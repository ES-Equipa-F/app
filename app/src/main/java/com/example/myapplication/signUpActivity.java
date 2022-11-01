package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

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

    public void SignUp(){
        EditText EditTextUserName, EditTextEmail, EditTextPassword, EditTextIDCode, EditTextPhoneNumber;

        Button button = (Button) findViewById(R.id.signup_but);
        EditTextUserName = findViewById(R.id.editTextTextPersonName2);
        EditTextPhoneNumber = findViewById(R.id.editTextTextPersonName3);
        EditTextEmail = findViewById(R.id.editTextTextPersonName4);
        EditTextIDCode = findViewById(R.id.editTextTextPersonName5);
        EditTextPassword = findViewById(R.id.editTextTextPassword2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username, email, password, idCode, phoneNumber;

                username = String.valueOf(EditTextUserName.getText());
                email = String.valueOf(EditTextEmail.getText());
                password = String.valueOf(EditTextPassword.getText());
                idCode = String.valueOf(EditTextIDCode.getText());
                phoneNumber = String.valueOf(EditTextPhoneNumber.getText());


                if(!username.equals("") && !email.equals("") && !password.equals("") && !idCode.equals("") && !phoneNumber.equals("")){
                    // Insert into database
                }

                else{
                    Toast.makeText(getApplicationContext(),"All fields are required", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
