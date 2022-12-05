package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
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

public class myHomeActivity extends AppCompatActivity {

    ConstraintLayout r1;
    ConstraintLayout r2;
    ConstraintLayout r3;
    ConstraintLayout r4;
    ConstraintLayout r5;
    room room_1 = new room();
    room room_2 = new room();
    room room_3 = new room();
    room room_4 = new room();
    room room_5 = new room();
    TextView tV;

    String current_email="";
    String current_house_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_home);

        current_email = getIntent().getStringExtra("message_email");
        current_house_id = getIntent().getStringExtra("message_house_id");



        //Sacar info da db


        // Retrieve da table Rooms da DB aqui
        room_1.defineParameters(0, "Sala");
        room_2.defineParameters(1, "Cozinha");
        room_3.defineParameters(2, "Quarto");
        //...


        displayRooms();
        goCreateRoom();
        goToProfile();
        goToSmart();
    }

/*
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

                String query = "SELECT" +
                                    "room.id AS id," +
                                    "room.name AS name," +
                                    "action.manual_control AS manual_control," +
                                    "action.brightness AS brightness," +
                                    "action.motion_sense AS motion_sense" +
                                "FROM room" +
                                    "JOIN action" +
                                    "ON room.id = action.room_id" +
                                "WHERE" +
                                    "room.house_id ="+current_house_id;

                rs = st.executeQuery(query);


                while(rs.next()){
                    //Se o device ainda não estiver atribuido a uma room..
                    if( rs.getString("activated").equals("0") ){
                        //Adiciona à lista de ids por utilizar
                        String curr_id = rs.getString("id");
                        free_device_ids.add( curr_id );
                    }
                    //Se já estiver atribuido a uma sala com nome
                    else{
                        //Adiciona à lista de nomes de rooms já existentes
                        String curr_name = rs.getString("name");
                        room_names_in_use.add( curr_name );

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

        }
    }

*/
    public void addRoom(View view){
        // Isto é só um teste...
        room_4.defineParameters(3, "Entrada");
        displayRooms();
    }

    public void displayRooms(){
        if( room_1.getId() != -1 ){
            tV = (TextView) findViewById(R.id.room_1_name);
            tV.setText( room_1.getName() );
            r1 = (ConstraintLayout) findViewById(R.id.room_1);
            show(r1);
        }
        if( room_2.getId() != -1 ){
            tV = (TextView) findViewById(R.id.room_2_name);
            tV.setText( room_2.getName() );
            r2 = (ConstraintLayout) findViewById(R.id.room_2);
            show(r2);
        }
        if( room_3.getId() != -1 ){
            tV = (TextView) findViewById(R.id.room_3_name);
            tV.setText( room_3.getName() );
            r3 = (ConstraintLayout) findViewById(R.id.room_3);
            show(r3);
        }
        if( room_4.getId() != -1 ){
            tV = (TextView) findViewById(R.id.room_4_name);
            tV.setText( room_4.getName() );
            r4 = (ConstraintLayout) findViewById(R.id.room_4);
            show(r4);
        }
        if( room_5.getId() != -1 ){
            tV = (TextView) findViewById(R.id.room_5_name);
            tV.setText( room_5.getName() );
            r5 = (ConstraintLayout) findViewById(R.id.room_5);
            show(r5);
        }
    }


    public void show(ConstraintLayout room){
        room.setVisibility(View.VISIBLE);
    }

    public void hide(ConstraintLayout room){
        room.setVisibility(View.INVISIBLE);
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
