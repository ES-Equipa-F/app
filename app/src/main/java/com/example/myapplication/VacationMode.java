package com.example.myapplication;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.models.smart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

public class VacationMode extends AppCompatActivity {
    private static final String url = "jdbc:mysql://34.155.233.25/mydb?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String pass = "db-es-teamf";

    String current_email="";
    String current_house_id="";
    String selected_device_id="";
    String selected_device_name="";

    TextView tV;
    SeekBar seekBar;

    Button startTime, endTime;
    int hour1, minute1, hour2, minute2, second1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        current_email = getIntent().getStringExtra("message_email");
        current_house_id = getIntent().getStringExtra("message_house_id");

        setContentView(R.layout.pop_vacation);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.6));

        startTime = findViewById(R.id.vacation_start);
        endTime = findViewById(R.id.vacation_end);
        Button close_vacation = (Button) findViewById(R.id.vacation_done);
        close_vacation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                closeWindow();
            }
        });

        VacationMode.retrieveValuesFromDB2 retrieveValuesFromDB2 = new VacationMode.retrieveValuesFromDB2();
        retrieveValuesFromDB2.execute();

    }

    public void closeWindow(){
        this.finish();
    }


    public void popTimeStartPicker(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker1, int selectedHour1, int selectedMinute1) {
                hour1 = selectedHour1;
                minute1 = selectedMinute1;
                second1 = 0;
                startTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour1, minute1));
                String hour_1 = Integer.toString(hour1);
                String minute_1 = Integer.toString(minute1);
                int startingTime;
                if (minute1 < 10) {
                    startingTime = Integer.valueOf(String.valueOf(hour1) + String.valueOf(second1) + String.valueOf(minute1) + String.valueOf(second1) + String.valueOf(second1));
                }
                else {
                    startingTime = Integer.valueOf(String.valueOf(hour1) + String.valueOf(minute1) + String.valueOf(second1) + String.valueOf(second1));
                }
                String startingTime2 = Integer.toString(startingTime);
                System.out.println("sao" + startingTime2 + "horas");
                VacationMode.updateAction2ParameterOnDB updateAction2ParameterOnDB = new VacationMode.updateAction2ParameterOnDB();
                updateAction2ParameterOnDB.execute("2", "start_time", startingTime2);

            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour1, minute1, true);
        timePickerDialog.setTitle("Select Start Time");
        timePickerDialog.show();
    }

    public void popTimeEndPicker(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker2, int selectedHour2, int selectedMinute2) {
                hour2 = selectedHour2;
                minute2 = selectedMinute2;
                endTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour2, minute2));
                String hour_2 = Integer.toString(hour2);
                String minute_2 = Integer.toString(minute2);
                int startingTime;
                if (minute2 < 10) {
                    startingTime = Integer.valueOf(String.valueOf(hour2) + String.valueOf(second1) + String.valueOf(minute2) + String.valueOf(second1) + String.valueOf(second1));
                }
                else {
                    startingTime = Integer.valueOf(String.valueOf(hour2) + String.valueOf(minute2) + String.valueOf(second1) + String.valueOf(second1));
                }
                String startingTime2 = Integer.toString(startingTime);
                System.out.println("sao" + startingTime2 + "horas");
                VacationMode.updateAction2ParameterOnDB updateAction2ParameterOnDB = new VacationMode.updateAction2ParameterOnDB();
                updateAction2ParameterOnDB.execute("2", "end_time", startingTime2);

            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour2, minute2, true);
        timePickerDialog.setTitle("Select End Time");
        timePickerDialog.show();
    }

    private class updateAction2ParameterOnDB extends AsyncTask<String, Void, String> {
        // params[0] = room id (string)
        // params[1] = parameter manual/brightness/motion (string)
        // params[2] = value
        String res = "";
        Connection con = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                ResultSet rs = null;
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();
                String id = params[0];
                String parameter = params[1];
                int value = Integer.parseInt(params[2]);

                String query =  "UPDATE timed_action " +
                        "SET "+parameter+"= " + value + " " +
                        "WHERE timed_action.id=\""+id+"\"";

                st.executeUpdate(query);


            } catch (SQLException e) {
                e.printStackTrace();
                res = e.toString();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if(con != null){
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }


    private class retrieveValuesFromDB2 extends AsyncTask<String, Void, smart> {
        String res = "";
        Connection con = null;
        smart smart1;
        @Override
        protected smart doInBackground(String... params) {
            try {
                ResultSet rs = null;
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();

                String query = "SELECT " +
                        "timed_action.room_id AS division, " +
                        "timed_action.id AS id, " +
                        "timed_action.scheduled AS smart_on, " +
                        "timed_action.name AS name," +
                        "timed_action.brightness AS brightness, " +
                        "timed_action.motion_sense AS motion_sense, " +
                        "timed_action.start_time AS start_time, " +
                        "timed_action.end_time AS end_time " +
                        "FROM room " +
                        "JOIN timed_action " +
                        "ON room.id = timed_action.room_id " +
                        "WHERE " +
                        "room.house_id =" + current_house_id;

                rs = st.executeQuery(query);

                rs.next();
                rs.next();
                //while (!Objects.equals(rs.getString("id"), "1")) {
                //    rs.next();
                //}

                String name = rs.getString("name");
                String id = rs.getString("id");
                String division = rs.getString("division");
                int brightness = rs.getInt("brightness");
                int smart_on = rs.getInt("smart_on");
                int motion_sense = rs.getInt("motion_sense");
                int start_time = rs.getInt("start_time");
                int end_time = rs.getInt("end_time");
                smart1 = new smart(name, id, division, smart_on, brightness, motion_sense, start_time, end_time);



                System.out.println("CHEGUEI AQUI");

            } catch (SQLException e) {
                e.printStackTrace();
                res = e.toString();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(smart1);
            return smart1;
        }

        @Override
        protected void onPostExecute(smart result) {
            int curr_motion_sense, curr_brightness, curr_smart_on, curr_start_time;

            //Setup ao icone da brightness
            //Setup à SeekBar de controlo da Brightness
            result.smart_setsB((SeekBar) findViewById(R.id.light2) );
            curr_brightness = result.smart_getBrightness();
            result.smart_getsB().setProgress(curr_brightness);
            result.smart_getsB().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                    result.smart_setBrightness( progress );
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar){
                    String id = result.smart_getId();
                    System.out.println(id);
                    String brightness = String.valueOf(result.smart_getBrightness());
                    VacationMode.updateAction2ParameterOnDB updateAction2ParameterOnDB = new VacationMode.updateAction2ParameterOnDB();
                    updateAction2ParameterOnDB.execute(id, "brightness", brightness);
                }
            });


            Switch motion_smart1 = (Switch) findViewById(R.id.motion_smart2);
            result.smart_setMotion_switch(motion_smart1);
            curr_motion_sense = result.smart_getMotion_sense();
            result.smart_getMotion_switch().setChecked(curr_motion_sense == 1);
            result.smart_getMotion_switch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String id = result.smart_getId();
                    if (isChecked) {
                        result.smart_setMotion_sense(1);
                        VacationMode.updateAction2ParameterOnDB updateAction2ParameterOnDB = new VacationMode.updateAction2ParameterOnDB();
                        updateAction2ParameterOnDB.execute(id, "motion_sense", "1");
                    } else {
                        result.smart_setMotion_sense(1);
                        VacationMode.updateAction2ParameterOnDB updateAction2ParameterOnDB = new VacationMode.updateAction2ParameterOnDB();
                        updateAction2ParameterOnDB.execute(id, "motion_sense", "0");
                    }
                }
            });

            result.smart_setON_switch((Switch) findViewById(R.id.action_2));
            curr_smart_on = result.smart_getOn();
            result.smart_getON_Switch().setChecked(curr_smart_on==1);
            result.smart_getON_Switch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String id = result.smart_getId();
                    if(isChecked){
                        result.smart_setOn(1);
                        VacationMode.updateAction2ParameterOnDB updateAction2ParameterOnDB = new VacationMode.updateAction2ParameterOnDB();
                        updateAction2ParameterOnDB.execute(id, "scheduled", "1");
                    }
                    else{
                        result.smart_setOn(0);
                        VacationMode.updateAction2ParameterOnDB updateAction2ParameterOnDB = new VacationMode.updateAction2ParameterOnDB();
                        updateAction2ParameterOnDB.execute(id, "scheduled", "0");
                    }
                }
            });


        }
    }


}
