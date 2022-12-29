package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;


public class ProfileActivity extends AppCompatActivity {

    private static final String url = "jdbc:mysql://34.155.233.25/mydb?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String pass = "db-es-teamf";

    String current_email="";
    String current_house_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        current_email = getIntent().getStringExtra("message_email");
        current_house_id = getIntent().getStringExtra("message_house_id");

        ProfileActivity.displayNameEmail display = new ProfileActivity.displayNameEmail();
        display.execute();

        goToHome();
        goToSmart();
        ChangePassword();
        Logout();
    }

    public void Logout(){
        Button buttonLogOut = findViewById(R.id.logout);

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this,MainActivity.class);
                Toast.makeText(getApplicationContext(),"Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
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

    public void ChangePassword() {
        Button buttonChange = findViewById(R.id.change_but);

        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileActivity.getUser getPass = new ProfileActivity.getUser();
                getPass.execute(current_email);
            }
        });


    }

    private class getUser extends AsyncTask<String, Void, String> {
        String res = "";
        Connection con = null;

        @Override
        protected String doInBackground(String... params) {
            String def_password = "";
            try {
                ResultSet rs = null;
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();

                String query = "SELECT password FROM user WHERE email=\""+params[0]+"\"";
                rs = st.executeQuery(query);

                if(rs.next()){
                    def_password = rs.getString("password");
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
            return def_password;
        }

        @Override
        protected void onPostExecute(String result) {
            EditText oldPass, newPass;
            newPass = (EditText) findViewById(R.id.editNewPassword);
            oldPass = (EditText) findViewById(R.id.editOldPassword);

            String Old, New;
            New = String.valueOf(newPass.getText());
            Old = String.valueOf(oldPass.getText());

            if(Old.equals(result)){
                ProfileActivity.setNewPassword setPassWord = new ProfileActivity.setNewPassword();
                setPassWord.execute(New);
            }else{
                Toast.makeText(getApplicationContext(),"Incorrect Old Password!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class setNewPassword extends AsyncTask<String, Void, Integer> {
        String res = "";
        Connection con = null;

        @Override
        protected Integer doInBackground(String... params) {
            int worked = 0;
            try {
                ResultSet rs = null;
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();

                String query = "UPDATE user SET password=\""+params[0]+"\" WHERE email = \""+current_email+"\"";
                worked = st.executeUpdate(query);

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
            return worked;
        }

        @Override
        protected void onPostExecute(Integer result) {
            EditText oldPass, newPass;

            if(result != 0){
                newPass = (EditText) findViewById(R.id.editNewPassword);
                oldPass = (EditText) findViewById(R.id.editOldPassword);
                newPass.setText("");
                oldPass.setText("");

                Toast.makeText(getApplicationContext(),"Password Changed Successfully!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"Error Changing Password!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class displayNameEmail extends AsyncTask<String, Void, String> {
        String res = "";
        Connection con = null;

        @Override
        protected String doInBackground(String... params) {
            String name = "";
            try {
                ResultSet rs = null;
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();

                String query = "SELECT name FROM user WHERE email=\""+current_email+"\"";
                rs = st.executeQuery(query);

                if(rs.next()){
                    name = rs.getString("name");
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
            return name;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView nameTv = (TextView) findViewById(R.id.name_mypage);
            TextView emailTv = (TextView) findViewById(R.id.email_mypage);

            nameTv.setText(result);
            emailTv.setText(current_email);
        }
    }
}


