package com.example.myapplication;

import android.app.Activity;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.models.room;
import com.example.myapplication.models.smart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

public class GoToBed extends AppCompatActivity {
    private static final String url = "jdbc:mysql://34.163.74.192/mydb?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String pass = "db-es-teamf";

    String current_email="";
    String current_house_id="";
    String selected_device_id="";
    String selected_device_name="";

    TextView tV;
    SeekBar seekBar;

    Button startTime1, endTime1;
    int hour1, minute1, hour2, minute2;

    smart smart1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_go_to_bed);

        current_email = getIntent().getStringExtra("message_email");
        current_house_id = getIntent().getStringExtra("message_house_id");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.6));
        startTime1 = findViewById(R.id.startTime1);
        endTime1 = findViewById(R.id.EndTime1);

        Button close_bed = (Button) findViewById(R.id.b_done);
        close_bed.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                closeWindow();
            }
        });

        GoToBed.retrieveValuesFromDB retrieveValuesFromDB = new GoToBed.retrieveValuesFromDB();
        retrieveValuesFromDB.execute();

    }

    public void closeWindow(){
        this.finish();
    }
    public void popTimeStartPicker1(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker1, int selectedHour1, int selectedMinute1) {
                hour1 = selectedHour1;
                minute1 = selectedMinute1;
                startTime1.setText(String.format(Locale.getDefault(), "%02d:%02d", hour1, minute1));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour1, minute1, true);
        timePickerDialog.setTitle("Select Start Time");
        timePickerDialog.show();
    }

    public void popTimeEndPicker1(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker2, int selectedHour2, int selectedMinute2) {
                hour2 = selectedHour2;
                minute2 = selectedMinute2;
                endTime1.setText(String.format(Locale.getDefault(), "%02d:%02d", hour2, minute2));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour2, minute2, true);
        timePickerDialog.setTitle("Select End Time");
        timePickerDialog.show();
    }

    private class updateAction1ParameterOnDB extends AsyncTask<String, Void, String> {
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


    private class retrieveValuesFromDB extends AsyncTask<String, Void, String> {
        String res = "";
        Connection con = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                ResultSet rs = null;
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();

                String query = "SELECT " +
                        "timed_action.id AS name, " +
                        "timed_action.room_id AS id, " +
                        "timed_action.scheduled AS smart_on, " +
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

                String id = rs.getString("id");

                String name = rs.getString("name");
                int brightness = rs.getInt("brightness");
                int smart_on = rs.getInt("smart_on");
                int motion_sense = rs.getInt("motion_sense");
                smart smart1 = new smart(id, name, smart_on, brightness, motion_sense);
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
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            int curr_motion_sense, curr_brightness;
            smart1.smart_setMotion_switch((Switch) findViewById(R.id.motion_smart1));
            curr_motion_sense = smart1.smart_getMotion_sense();
            smart1.smart_getMotion_switch().setChecked(curr_motion_sense == 1);
            smart1.smart_getMotion_switch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String id = smart1.smart_getId();
                    if (isChecked) {
                        smart1.smart_setMotion_sense(1);
                        GoToBed.updateAction1ParameterOnDB updateAction1ParameterOnDB = new GoToBed.updateAction1ParameterOnDB();
                        updateAction1ParameterOnDB.execute(id, "motion_sense", "1");
                    } else {
                        smart1.smart_setMotion_sense(1);
                        GoToBed.updateAction1ParameterOnDB updateAction1ParameterOnDB = new GoToBed.updateAction1ParameterOnDB();
                        updateAction1ParameterOnDB.execute(id, "motion_sense", "0");
                    }
                }
            });

            smart1.smart_getsB().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                    smart1.smart_setBrightness( progress );
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar){
                    String id = smart1.smart_getId();
                    String brightness = String.valueOf(smart1.smart_getBrightness());
                    GoToBed.updateAction1ParameterOnDB updateAction1ParameterOnDB = new GoToBed.updateAction1ParameterOnDB();
                    updateAction1ParameterOnDB.execute(id, "brightness", brightness);
                }
            });

            smart1.smart_getON_Switch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged( CompoundButton buttonView, boolean isChecked){
                    String id = smart1.smart_getId();
                    if(isChecked){
                        smart1.smart_setOn(1);
                        GoToBed.updateAction1ParameterOnDB updateAction1ParameterOnDB = new GoToBed.updateAction1ParameterOnDB();
                        updateAction1ParameterOnDB.execute(id, "smart_on", "1");
                    }
                    else{
                        smart1.smart_setOn(0);
                        GoToBed.updateAction1ParameterOnDB updateAction1ParameterOnDB = new GoToBed.updateAction1ParameterOnDB();
                        updateAction1ParameterOnDB.execute(id, "smart_on", "0");
                    }
                }
            });


        }
    }
}
