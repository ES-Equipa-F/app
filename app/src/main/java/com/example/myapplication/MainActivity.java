package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToMyHome();
        goToSignUp();

    }

    public void goToMyHome(){
        Button button = (Button) findViewById(R.id.login_but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,myHomeActivity.class);
                startActivity(i);
            }
        });
    }


    public void goToSignUp(){
        Button button = (Button) findViewById(R.id.login_signup_but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,signUpActivity.class);
                startActivity(i);
            }
        });
    }




}