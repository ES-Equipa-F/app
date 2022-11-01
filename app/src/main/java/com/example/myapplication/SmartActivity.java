package com.example.myapplication;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SmartActivity extends AppCompatActivity {
    Button startTime;
    int hour, minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_actions);

        goToHome();
        goToProfile();
        smart_go_to_bed();
        smart_vacation();
        smart_add();
    }

    public void goToHome(){
        ImageButton button = (ImageButton) findViewById(R.id.Smart_Home_but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SmartActivity.this,myHomeActivity.class);
                startActivity(i);
            }
        });
    }

    public void goToProfile(){
        ImageButton button = (ImageButton) findViewById(R.id.Smart_Profile_but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SmartActivity.this,ProfileActivity.class);
                startActivity(i);
            }
        });
    }

    public void smart_go_to_bed(){
        ImageButton b = (ImageButton) findViewById(R.id.gotobed);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SmartActivity.this,GoToBed.class);
                startActivity(i);
            }
        });
    }

    public void smart_vacation(){
        ImageButton b = (ImageButton) findViewById(R.id.vacation);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SmartActivity.this,VacationMode.class);
                startActivity(i);
            }
        });
    }

    public void smart_add() {
        ImageButton b = (ImageButton) findViewById(R.id.add_action);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SmartActivity.this,AddAction.class);
                startActivity(i);
            }
        });
    }

}
