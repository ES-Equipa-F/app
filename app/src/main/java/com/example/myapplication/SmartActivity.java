package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SmartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_actions);

        goToHome();
        goToProfile();
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

}
