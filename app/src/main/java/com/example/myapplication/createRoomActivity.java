package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class createRoomActivity extends AppCompatActivity {

    private static final String url = "jdbc:mysql://34.155.233.25/mydb?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String pass = "db-es-teamf";



    ArrayList<String> free_device_ids = new ArrayList<>();
    ArrayList<String> room_names_in_use = new ArrayList<>();


    String current_email="";
    String current_house_id="";
    String selected_device_id="";
    String selected_device_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        current_email = getIntent().getStringExtra("message_email");
        current_house_id = getIntent().getStringExtra("message_house_id");

        //sacar ids da db
        createRoomActivity.retrieveFreeDeviceIds retrieveFreeDeviceIds = new createRoomActivity.retrieveFreeDeviceIds();
        retrieveFreeDeviceIds.execute("");

        goBack();
        createRoom();


    }

    public void createRoom(){
        EditText EditTextRoomName;
        EditTextRoomName = findViewById(R.id.roomName);
        Button button = (Button) findViewById(R.id.create_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_device_name = String.valueOf(EditTextRoomName.getText());
                if( nameIsInvalid() ){
                    Toast.makeText(createRoomActivity.this,"Name Invalid", Toast.LENGTH_SHORT).show();
                }
                else if( free_device_ids.isEmpty() ){
                    Toast.makeText(createRoomActivity.this,"No devices are available", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Se o nome for válido, dar update na DB
                    String define_room_query =
                            "UPDATE room SET name = \""+selected_device_name+"\", activated = '1' WHERE (id = \""+selected_device_id+"\")";
                    createRoomActivity.updateRoomOnDB updateRoomOnDB = new createRoomActivity.updateRoomOnDB();
                    updateRoomOnDB.execute(define_room_query);
                }


            }
        });
    }


    public void goBack(){
        ImageButton button = (ImageButton) findViewById(R.id.createRoom_goBack);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(createRoomActivity.this,myHomeActivity.class);
                i.putExtra("message_email", current_email);
                i.putExtra("message_house_id", current_house_id);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

    }
    public boolean nameIsInvalid(){
        if( selected_device_name.equals("") ) return true;
        for( String curr : room_names_in_use ){
            if(curr.equals(selected_device_name) ) return true;
        }
        return false;
    }

    private class updateRoomOnDB extends AsyncTask<String, Void, String> {
        String res = "valid";
        Connection con = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                ResultSet rs = null;
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();

                String query = params[0];
                st.executeUpdate(query);


            } catch (SQLException e) {
                e.printStackTrace();
                res = "invalid";
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
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            if( res.equals("valid") ){
                Toast.makeText(createRoomActivity.this,"Room Added!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(createRoomActivity.this , myHomeActivity.class);
                i.putExtra("message_email", current_email);
                i.putExtra("message_house_id", current_house_id);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
            else{
                Toast.makeText(createRoomActivity.this,"Error occurred!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class retrieveFreeDeviceIds extends AsyncTask<String, Void, String> {
        String res = "";
        Connection con = null;


        @Override
        protected String doInBackground(String... params) {
            try {
                ResultSet rs = null;
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();

                String query = "SELECT * FROM room WHERE house_id="+current_house_id;
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
            if( !free_device_ids.isEmpty() ){
                //Mostrar o que retirou da DB no spinner
                Spinner spinner = findViewById(R.id.spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(createRoomActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, free_device_ids);
                spinner.setAdapter(adapter);

                //Listener para detetar qual é o device selecionado e guardar em selected_device_id
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selected_device_id = adapterView.getItemAtPosition(i).toString() ;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }
            else{
                Toast.makeText(createRoomActivity.this,"No devices are available", Toast.LENGTH_SHORT).show();
            }
        }
    }


}


