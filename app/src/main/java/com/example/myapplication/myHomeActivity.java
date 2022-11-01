package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.models.room;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_home);

        // Retrieve da table Rooms da DB aqui
        room_1.defineParameters(0, "Sala");
        room_2.defineParameters(1, "Cozinha");
        room_3.defineParameters(2, "Quarto");
        //...

        displayRooms();
        goToProfile();
        goToSmart();
    }

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
                startActivity(i);
            }
        });
    }

    public void goToSmart(){
        ImageButton button = (ImageButton) findViewById(R.id.myHome_Smart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(myHomeActivity.this,SmartActivity.class);
                startActivity(i);
            }
        });
    }
}
