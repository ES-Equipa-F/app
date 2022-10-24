package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class signUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        goToMyHome();
        goBack();
    }

    public void goToMyHome(){
        Button button = (Button) findViewById(R.id.signup_but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(signUpActivity.this,myHomeActivity.class);
                startActivity(i);
            }
        });
    }

    public void goBack(){
        ImageButton button = (ImageButton) findViewById(R.id.signup_goBack);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(signUpActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
