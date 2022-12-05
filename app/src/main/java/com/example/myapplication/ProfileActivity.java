package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;


public class ProfileActivity extends AppCompatActivity {

    private EditText oldPass, newPass;
    private Button buttonChange;
    //private String oldPassstr, newPassstr; //tem a pass que o user vai inserir

    String current_email="";
    String current_house_id="";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        current_email = getIntent().getStringExtra("message_email");
        current_house_id = getIntent().getStringExtra("message_house_id");

        newPass = (EditText) findViewById(R.id.editNewPassword);
        oldPass = (EditText) findViewById(R.id.editOldPassword);
        buttonChange = findViewById(R.id.change_but);

        goToHome();
        goToSmart();
        ChangePassword(oldPass);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    public void goToHome(){
        ImageButton button = (ImageButton) findViewById(R.id.Profile_Home_but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this,myHomeActivity.class);
                i.putExtra("message_email", current_email);
                i.putExtra("message_house_id", current_house_id);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    public void goToSmart(){
        ImageButton button = (ImageButton) findViewById(R.id.Profile_Smart_but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this,SmartActivity.class);
                i.putExtra("message_email", current_email);
                i.putExtra("message_house_id", current_house_id);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    public void ChangePassword(EditText oldPass) {
/*
        String oldPass_str = oldPass.getText().toString();
        String oldDB="aaaa";
        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oldDB.equals(oldPass_str)) {
                    Context context1 = getApplicationContext();
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Password Updated!", Toast.LENGTH_SHORT);
                    toast1.show();
                }
                else{
                    Context context2 = getApplicationContext();
                    Toast toast2 = Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT);
                    toast2.show();
                }
            }
        });*/
        buttonChange.setOnClickListener(event -> {
            if (oldPass.getText().equals("xxxx")) {
                Toast.makeText(getApplicationContext(), "Password updated!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


