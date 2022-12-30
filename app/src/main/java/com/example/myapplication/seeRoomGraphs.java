package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class seeRoomGraphs extends AppCompatActivity {

    private static final String url = "jdbc:mysql://34.155.233.25/mydb?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String pass = "db-es-teamf";

    String current_email="";
    String current_house_id="";

    TextView tV;
    String selected_room_id="";
    String selected_room_name="";
    String selected_sensor_name="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_room);

        current_email = getIntent().getStringExtra("message_email");
        current_house_id = getIntent().getStringExtra("message_house_id");
        selected_room_id = getIntent().getStringExtra("selected_room_id");
        selected_room_name = getIntent().getStringExtra("selected_room_name");

        setup_Room_name();
        setup_Spinner();
        goBack();

    }


    void setup_Room_name(){
        tV = (TextView) findViewById(R.id.room_name);
        tV.setText( selected_room_name );
    }

    void setup_Spinner(){
        //Opções a mostrar nos spinners
        ArrayList<String> sensor_Names_For_User = new ArrayList<>();
        sensor_Names_For_User.add("Light sensor");
        sensor_Names_For_User.add("People Presence");
        sensor_Names_For_User.add("Lamp Value");

        //Setar o spinner
        Spinner spinner = findViewById(R.id.spinner_graph);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(seeRoomGraphs.this,
                android.R.layout.simple_spinner_dropdown_item, sensor_Names_For_User);
        spinner.setAdapter(adapter);

        //Listener para detetar qual é o sensor selecionado e guardar em selected_sensor_name
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getItemAtPosition(i).toString() ;
                if(selected.equals("Light sensor") ){
                    selected_sensor_name = "ldr";
                    System.out.println(selected);
                }
                else if(selected.equals("People Presence") ){
                    selected_sensor_name = "movimento";
                }
                else if(selected.equals("Lamp Value") ){
                    selected_sensor_name = "lampada";
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void goBack(){
        ImageButton button = (ImageButton) findViewById(R.id.graphRoom_goBack);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(seeRoomGraphs.this,myHomeActivity.class);
                i.putExtra("message_email", current_email);
                i.putExtra("message_house_id", current_house_id);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

    }

}


