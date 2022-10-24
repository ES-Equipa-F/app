package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        goToHome();
        goToSmart();
    }

    public void goToHome(){
        ImageButton button = (ImageButton) findViewById(R.id.Profile_Home_but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this,myHomeActivity.class);
                startActivity(i);
            }
        });
    }

    public void goToSmart(){
        ImageButton button = (ImageButton) findViewById(R.id.Profile_Smart_but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this,SmartActivity.class);
                startActivity(i);
            }
        });
    }


}
