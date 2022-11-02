package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    public void LogIn(){

        EditText UserName,Password;

        Button button = (Button) findViewById(R.id.login_but);
        UserName = findViewById(R.id.editTextTextPersonName);
        Password = findViewById(R.id.editTextTextPassword);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username, password;
                username = String.valueOf(UserName.getText());
                password = String.valueOf(Password.getText());

                /* if(Make query to check if the username and password correspond to something in the db){

                }
                else{
                    Make a toast saying "Incorrect username or password, try again! "
                }
                * */

            }
        });
    }




}