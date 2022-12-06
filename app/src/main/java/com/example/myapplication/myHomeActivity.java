package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.models.room;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class myHomeActivity extends AppCompatActivity {

    private static final String url = "jdbc:mysql://34.163.74.192/mydb?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String pass = "db-es-teamf";
    String current_email="";
    String current_house_id="";

    TextView tV;
    SeekBar seekBar;


    ArrayList<room> rooms = new ArrayList<room>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_home);

        current_email = getIntent().getStringExtra("message_email");
        current_house_id = getIntent().getStringExtra("message_house_id");

        myHomeActivity.retrieveRoomsFromDB retrieveRoomsFromDB = new myHomeActivity.retrieveRoomsFromDB();
        retrieveRoomsFromDB.execute();




        goCreateRoom();
        goToProfile();
        goToSmart();
    }

    private class updateRoomParameterOnDB extends AsyncTask<String, Void, String> {
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
                String room_id = params[0];
                String parameter = params[1];
                int value = Integer.parseInt(params[2]);

                String query =  "UPDATE action " +
                                "SET "+parameter+"= " + value + " " +
                                "WHERE action.room_id=\""+room_id+"\"";

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
    private class retrieveRoomsFromDB extends AsyncTask<String, Void, String> {
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
                                    "room.id AS id, " +
                                    "room.name AS name, " +
                                    "action.manual_control AS manual_control, " +
                                    "action.brightness AS brightness, " +
                                    "action.motion_sense AS motion_sense " +
                                "FROM room " +
                                    "JOIN action " +
                                    "ON room.id = action.room_id " +
                                "WHERE " +
                                    "room.house_id ="+current_house_id;

                rs = st.executeQuery(query);


                while(rs.next()){
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    int manual_control = rs.getInt("manual_control");
                    int brightness = rs.getInt("brightness");
                    int motion_sense = rs.getInt("motion_sense");
                    if( !name.equals("NOT_DEFINED") ){
                        room r = new room(id, name, manual_control, brightness, motion_sense);
                        rooms.add(r);
                    }

                }

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
            if( rooms.isEmpty() ){
                return;
            }
            else{
                for(int i=0; i<rooms.size(); i++){
                    if(i==0){
                        tV = (TextView) findViewById(R.id.room_1_name);
                        tV.setText( rooms.get(i).getName() );
                        rooms.get(i).setCL( (ConstraintLayout) findViewById(R.id.room_1) );
                        rooms.get(i).show();

                        rooms.get(0).setManual_switch((Switch) findViewById(R.id.manual1));
                        rooms.get(0).getManual_switch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                            @Override
                            public void onCheckedChanged( CompoundButton buttonView, boolean isChecked){
                                String id = rooms.get(0).getId();
                                if(isChecked){
                                    rooms.get(0).setManual_control(1);
                                    //dar enable aos outros elementos aqui
                                    myHomeActivity.updateRoomParameterOnDB updateRoomParameterOnDB = new myHomeActivity.updateRoomParameterOnDB();
                                    updateRoomParameterOnDB.execute(id, "manual_control", "1");
                                }
                                else{
                                    rooms.get(0).setManual_control(0);
                                    //dar disable aos outros elementos aqui
                                    myHomeActivity.updateRoomParameterOnDB updateRoomParameterOnDB = new myHomeActivity.updateRoomParameterOnDB();
                                    updateRoomParameterOnDB.execute(id, "manual_control", "0");
                                }
                            }
                        });

                        rooms.get(0).setMotion_switch((Switch) findViewById(R.id.motion1));
                        rooms.get(0).getMotion_switch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                            @Override
                            public void onCheckedChanged( CompoundButton buttonView, boolean isChecked){
                                String id = rooms.get(0).getId();
                                if(isChecked){
                                    rooms.get(0).setMotion_sense(1);
                                    myHomeActivity.updateRoomParameterOnDB updateRoomParameterOnDB = new myHomeActivity.updateRoomParameterOnDB();
                                    updateRoomParameterOnDB.execute(id, "motion_sense", "1");
                                }
                                else{
                                    rooms.get(0).setMotion_sense(1);
                                    myHomeActivity.updateRoomParameterOnDB updateRoomParameterOnDB = new myHomeActivity.updateRoomParameterOnDB();
                                    updateRoomParameterOnDB.execute(id, "motion_sense", "1");
                                }
                            }
                        });




                        rooms.get(0).setsB((SeekBar) findViewById(R.id.brightness1) );
                        rooms.get(0).getsB().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                                rooms.get(0).setBrightness( progress );
                            }
                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {}
                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar){
                                String id = rooms.get(0).getId();
                                String brightness = String.valueOf(rooms.get(0).getBrightness());
                                myHomeActivity.updateRoomParameterOnDB updateRoomParameterOnDB = new myHomeActivity.updateRoomParameterOnDB();
                                updateRoomParameterOnDB.execute(id, "brightness", brightness);
                            }
                        });
                    }
                    if(i==1){
                        tV = (TextView) findViewById(R.id.room_2_name);
                        tV.setText( rooms.get(i).getName() );
                        rooms.get(i).setCL( (ConstraintLayout) findViewById(R.id.room_2) );
                        rooms.get(i).show();
                        rooms.get(i).setsB((SeekBar) findViewById(R.id.brightness2) );
                        rooms.get(1).getsB().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                                rooms.get(1).setBrightness( progress );
                            }
                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {}
                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar){
                                String id = rooms.get(1).getId();
                                String brightness = String.valueOf(rooms.get(1).getBrightness());
                                myHomeActivity.updateRoomParameterOnDB updateRoomParameterOnDB = new myHomeActivity.updateRoomParameterOnDB();
                                updateRoomParameterOnDB.execute(id, "brightness", brightness);
                            }
                        });
                    }
                    if(i==2){
                        tV = (TextView) findViewById(R.id.room_3_name);
                        tV.setText( rooms.get(i).getName() );
                        rooms.get(i).setCL( (ConstraintLayout) findViewById(R.id.room_3) );
                        rooms.get(i).show();
                        rooms.get(i).setsB((SeekBar) findViewById(R.id.brightness3) );
                        rooms.get(2).getsB().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                                rooms.get(2).setBrightness( progress );
                            }
                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {}
                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar){
                                String id = rooms.get(2).getId();
                                String brightness = String.valueOf(rooms.get(2).getBrightness());
                                myHomeActivity.updateRoomParameterOnDB updateRoomParameterOnDB = new myHomeActivity.updateRoomParameterOnDB();
                                updateRoomParameterOnDB.execute(id, "brightness", brightness);
                            }
                        });
                    }
                    if(i==3){
                        tV = (TextView) findViewById(R.id.room_4_name);
                        tV.setText( rooms.get(i).getName() );
                        rooms.get(i).setCL( (ConstraintLayout) findViewById(R.id.room_4) );
                        rooms.get(i).show();
                        rooms.get(i).setsB((SeekBar) findViewById(R.id.brightness4) );
                        rooms.get(3).getsB().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                                rooms.get(3).setBrightness( progress );
                            }
                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {}
                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar){
                                String id = rooms.get(3).getId();
                                String brightness = String.valueOf(rooms.get(3).getBrightness());
                                myHomeActivity.updateRoomParameterOnDB updateRoomParameterOnDB = new myHomeActivity.updateRoomParameterOnDB();
                                updateRoomParameterOnDB.execute(id, "brightness", brightness);
                            }
                        });
                    }
                    if(i==4){
                        tV = (TextView) findViewById(R.id.room_5_name);
                        tV.setText( rooms.get(i).getName() );
                        rooms.get(i).setCL( (ConstraintLayout) findViewById(R.id.room_5) );
                        rooms.get(i).show();
                        rooms.get(i).setsB((SeekBar) findViewById(R.id.brightness5) );
                        rooms.get(4).getsB().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                                rooms.get(4).setBrightness( progress );
                            }
                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {}
                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar){
                                String id = rooms.get(4).getId();
                                String brightness = String.valueOf(rooms.get(4).getBrightness());
                                myHomeActivity.updateRoomParameterOnDB updateRoomParameterOnDB = new myHomeActivity.updateRoomParameterOnDB();
                                updateRoomParameterOnDB.execute(id, "brightness", brightness);
                            }
                        });
                    }
                }
            }
        }
    }



    public void goToProfile(){
        ImageButton button = (ImageButton) findViewById(R.id.myHome_Profile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(myHomeActivity.this,ProfileActivity.class);
                i.putExtra("message_email", current_email);
                i.putExtra("message_house_id", current_house_id);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    public void goToSmart(){
        ImageButton button = (ImageButton) findViewById(R.id.myHome_Smart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(myHomeActivity.this,SmartActivity.class);
                i.putExtra("message_email", current_email);
                i.putExtra("message_house_id", current_house_id);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    public void goCreateRoom(){
        ImageButton button = (ImageButton) findViewById(R.id.createRoom);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(myHomeActivity.this,createRoomActivity.class);
                i.putExtra("message_email", current_email);
                i.putExtra("message_house_id", current_house_id);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }



    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
