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

    private static final String url = "jdbc:mysql://34.163.74.192/mydb?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String pass = "db-es-teamf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ConnectMySql connectMySql = new ConnectMySql();
        connectMySql.execute("");

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




    private class ConnectMySql extends AsyncTask<String, Void, String> {
        String res = "";
        Connection con = null;


        @Override
        protected String doInBackground(String... params) {
            try {
                ResultSet rs = null;
                String s = null;
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();
                rs = st.executeQuery("Select * from house where id = 1");

                try{
                    if(rs.next()){
                        s = rs.getString("address");

                    }
                }
                catch (Exception e){
                    System.out.println(e);
                }
                System.out.println(s);


                String result = "Database Connection Successful\n";
                res = result;
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
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(this.getClass().getSimpleName(), result);
            //escrever aqui o que fazer com os resultados
        }
    }




}