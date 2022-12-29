package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private static final String url = "jdbc:mysql://34.155.233.25/mydb?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String pass = "db-es-teamf";


    String email="";
    String house_id="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goToMyHome();
        goToSignUp();

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    public void goToMyHome(){
        EditText EditTextEmail,EditTextPassword;
        EditTextEmail = findViewById(R.id.editTextTextPersonName);
        EditTextPassword = findViewById(R.id.editTextTextPassword);

        Button button = (Button) findViewById(R.id.login_but);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email="";
                String password="";
                email = String.valueOf(EditTextEmail.getText());
                password = String.valueOf(EditTextPassword.getText());


                //task assincrona para verificar db
                MainActivity.validateCredentialsFromDB validate = new MainActivity.validateCredentialsFromDB();
                validate.execute(email, password);

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
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }



    private class validateCredentialsFromDB extends AsyncTask<String, Void, String> {
        String res = "";
        Connection con = null;


        @Override
        protected String doInBackground(String... params) {
            String status = "not valid";
            try {
                ResultSet rs = null;
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();

                String query = "SELECT * FROM user WHERE email=\""+params[0]+"\" AND password=\""+params[1]+"\"";
                rs = st.executeQuery(query);

                if(rs.next()){
                    status = "valid";
                    email = rs.getString("email");
                    house_id=rs.getString("house_id");

                }

            } catch (SQLException e) {
                e.printStackTrace();
                res = e.toString();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if(con != null){
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            if( result.equals("valid") ){
                //passa para outra pagina
                Intent i = new Intent(MainActivity.this,myHomeActivity.class);
                i.putExtra("message_email", email);
                i.putExtra("message_house_id", house_id);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
            else{
                //erro
                Toast.makeText(MainActivity.this,"Credentials are not valid", Toast.LENGTH_SHORT).show();
            }
        }
    }




}