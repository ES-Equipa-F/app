package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.Encryption.passwordEncryption;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signUpActivity extends AppCompatActivity {


    private static final String url = "jdbc:mysql://34.155.233.25/mydb?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String pass = "db-es-teamf";
    private ArrayList<Integer> global_keys = new ArrayList<Integer>();
    private ArrayList<String> global_emails = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getKeys();
        getEmails();
        goBack();
        SignUp(); //Substitute goToMyHome when database is ready

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    public void getKeys(){
        String query;
        query = "SELECT id from activation_key WHERE activated = false";
        signUpActivity.retrieveKeys getKeys = new signUpActivity.retrieveKeys();
        getKeys.execute(query);
    }

    public void getEmails(){
        String query;
        query = "SELECT email from user";
        signUpActivity.retrieveEmails getEmails = new signUpActivity.retrieveEmails();
        getEmails.execute(query);
    }

    public boolean checkKey(int key){
        for(int  curr_key : global_keys){
            if(curr_key == key){
                return true;
            }
        }
        return false;
    }

    public boolean checkEmail(String email){
        for(String  curr_email : global_emails){
            if(curr_email.equals(email)){
                return true;
            }
        }
        return false;
    }

    public void addUser(String username, String password, String email,String phoneNumber, int key,String address){
        String insert_house, insert_user, update_key;
        insert_house = "INSERT INTO house (id, address) values ("+key+",\""+address+"\");";
        insert_user= "INSERT INTO user (email, name, password, phone_number, house_id, activation_key_id) values ("+"\""+email+"\""+"," +
                                                                                                                  "\""+username+"\""+
                                                                                                                  ",\""+password+"\""+
                                                                                                                  ",\""+phoneNumber+"\""+
                                                                                                                  ","+key+
                                                                                                                  ","+key+");";
        update_key = "update activation_key SET activated=true WHERE id="+key;

        //-----------------------------------------QUERIES PARA A ESP1-----------------------------------------//
        String insert_room1 = "INSERT INTO room (id, name, activated, house_id) values("+
                                "\"ESP_1\"," +
                                "\"NOT_DEFINED\","+
                                "0,"+
                                key+")";
        String insert_action1 = "INSERT INTO action (id, manual_control, brightness, motion_sense, room_id) values("+
                                "\"ESP_1\"," +
                                "0,"+
                                "0,"+
                                "0,"+
                                "\"ESP_1\")";
        String insert_sensor1_ldr = "INSERT INTO sensor (type, room_id) values("+
                                    "\"ldr\"," +
                                    "\"ESP_1\")";
        String insert_sensor1_movimento =   "INSERT INTO sensor (type, room_id) values("+
                                            "\"movimento\"," +
                                            "\"ESP_1\")";
        String insert_light1 = "INSERT INTO light (id, room_id) values("+
                                "1,"+
                                "\"ESP_1\")";
        //queries para insert das smart actions
        String insert_smart1_1 =  "INSERT INTO timed_action (id, room_id) values(1, \"ESP_1\")";
        String insert_smart1_2 =  "INSERT INTO timed_action (id, room_id) values(2, \"ESP_1\")";
        String insert_smart1_3=  "INSERT INTO timed_action (id, room_id) values(3, \"ESP_1\")";

        //-----------------------------------------QUERIES PARA A ESP2-----------------------------------------//
        String insert_room2 = "INSERT INTO room (id, name, activated, house_id) values("+
                                "\"ESP_2\"," +
                                "\"NOT_DEFINED\","+
                                "0,"+
                                key+")";
        String insert_action2 = "INSERT INTO action (id, manual_control, brightness, motion_sense, room_id) values("+
                                "\"ESP_2\"," +
                                "0,"+
                                "0,"+
                                "0,"+
                                "\"ESP_2\")";
        String insert_sensor2_ldr = "INSERT INTO sensor (type, room_id) values("+
                                    "\"ldr\"," +
                                    "\"ESP_2\")";
        String insert_sensor2_movimento =   "INSERT INTO sensor (type, room_id) values("+
                                            "\"movimento\"," +
                                            "\"ESP_2\")";
        String insert_light2 = "INSERT INTO light (id, room_id) values("+
                                "2,"+
                                "\"ESP_2\")";
        //-----------------------------------------QUERIES PARA A ESP3-----------------------------------------//
        String insert_room3 = "INSERT INTO room (id, name, activated, house_id) values("+
                "\"ESP_3\"," +
                "\"NOT_DEFINED\","+
                "0,"+
                key+")";
        String insert_action3 = "INSERT INTO action (id, manual_control, brightness, motion_sense, room_id) values("+
                "\"ESP_3\"," +
                "0,"+
                "0,"+
                "0,"+
                "\"ESP_3\")";
        String insert_sensor3_ldr = "INSERT INTO sensor (type, room_id) values("+
                "\"ldr\"," +
                "\"ESP_3\")";
        String insert_sensor3_movimento =   "INSERT INTO sensor (type, room_id) values("+
                "\"movimento\"," +
                "\"ESP_3\")";
        String insert_light3 = "INSERT INTO light (id, room_id) values("+
                "3,"+
                "\"ESP_3\")";
        //-----------------------------------------QUERIES PARA A ESP4-----------------------------------------//
        String insert_room4 = "INSERT INTO room (id, name, activated, house_id) values("+
                "\"ESP_4\"," +
                "\"NOT_DEFINED\","+
                "0,"+
                key+")";
        String insert_action4 = "INSERT INTO action (id, manual_control, brightness, motion_sense, room_id) values("+
                "\"ESP_4\"," +
                "0,"+
                "0,"+
                "0,"+
                "\"ESP_4\")";
        String insert_sensor4_ldr = "INSERT INTO sensor (type, room_id) values("+
                "\"ldr\"," +
                "\"ESP_4\")";
        String insert_sensor4_movimento =   "INSERT INTO sensor (type, room_id) values("+
                "\"movimento\"," +
                "\"ESP_4\")";
        String insert_light4 = "INSERT INTO light (id, room_id) values("+
                "4,"+
                "\"ESP_4\")";
        //-----------------------------------------QUERIES PARA A ESP5-----------------------------------------//
        String insert_room5 = "INSERT INTO room (id, name, activated, house_id) values("+
                "\"ESP_5\"," +
                "\"NOT_DEFINED\","+
                "0,"+
                key+")";
        String insert_action5 = "INSERT INTO action (id, manual_control, brightness, motion_sense, room_id) values("+
                "\"ESP_5\"," +
                "0,"+
                "0,"+
                "0,"+
                "\"ESP_5\")";
        String insert_sensor5_ldr = "INSERT INTO sensor (type, room_id) values("+
                "\"ldr\"," +
                "\"ESP_5\")";
        String insert_sensor5_movimento =   "INSERT INTO sensor (type, room_id) values("+
                "\"movimento\"," +
                "\"ESP_5\")";
        String insert_light5 = "INSERT INTO light (id, room_id) values("+
                "5,"+
                "\"ESP_5\")";


        signUpActivity.addUserToDB setUser = new signUpActivity.addUserToDB();


        setUser.execute(insert_house,
                        insert_user,
                        update_key,
                            insert_room1,
                            insert_action1,
                            insert_sensor1_ldr,
                            insert_sensor1_movimento,
                            insert_light1,
                            insert_smart1_1,
                            insert_smart1_2,
                            insert_smart1_3,
                                insert_room2,
                                insert_action2,
                                insert_sensor2_ldr,
                                insert_sensor2_movimento,
                                insert_light2,
                                    insert_room3,
                                    insert_action3,
                                    insert_sensor3_ldr,
                                    insert_sensor3_movimento,
                                    insert_light3,
                                        insert_room4,
                                        insert_action4,
                                        insert_sensor4_ldr,
                                        insert_sensor4_movimento,
                                        insert_light4,
                                            insert_room5,
                                            insert_action5,
                                            insert_sensor5_ldr,
                                            insert_sensor5_movimento,
                                            insert_light5);

    }

    public void goToMyHome(){
        Button button = (Button) findViewById(R.id.signup_but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(signUpActivity.this,myHomeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
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
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    public boolean validateEmail(String email){
        String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern PATTERN = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

        Matcher matcher = PATTERN.matcher(email);
        return matcher.matches();
    }

    public boolean validateUsername(String username){ // Username can have numbers, _, -, and letters, its length must be from 3-16.
        final String USERNAME_REGEX = "^[a-zA-Z0-9._-]{3,16}$";
        Pattern PATTERN = Pattern.compile(USERNAME_REGEX);

        Matcher matcher = PATTERN.matcher(username);
        return matcher.matches();
    }

    public boolean validatePassword(String password){
        int MIN_LENGTH = 8;
        int MAX_LENGTH = 32;

        // Check password length
        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            return false;
        }

        // Check for at least one lowercase letter
        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        // Check for at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Check for at least one digit
        if (!password.matches(".*\\d.*")) {
            return false;
        }

        // Check for at least one special character
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            return false;
        }

        return true;
    }

    public void SignUp(){
        EditText EditTextUserName, EditTextEmail, EditTextPassword, EditTextIDCode, EditTextPhoneNumber, EditTextAddress;

        Button button = (Button) findViewById(R.id.signup_but);
        EditTextUserName = findViewById(R.id.editTextTextPersonName2);
        EditTextPhoneNumber = findViewById(R.id.editTextTextPersonName3);
        EditTextEmail = findViewById(R.id.editTextTextPersonName4);
        EditTextIDCode = findViewById(R.id.editTextTextPersonName5);
        EditTextPassword = findViewById(R.id.editTextTextPassword4);
        EditTextAddress = findViewById(R.id.address);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username, email, password, idCode, phoneNumber, address;

                username = String.valueOf(EditTextUserName.getText());
                email = String.valueOf(EditTextEmail.getText());
                password = String.valueOf(EditTextPassword.getText());
                idCode = String.valueOf(EditTextIDCode.getText());
                phoneNumber = String.valueOf(EditTextPhoneNumber.getText());
                address = String.valueOf(EditTextAddress.getText());

                int key=0;
                if(!idCode.equals("")){
                    key = Integer.parseInt(idCode);
                }


                if(!username.equals("") && !email.equals("") && !password.equals("") && !idCode.equals("") && !phoneNumber.equals("")&& !address.equals("")){
                    if(validateUsername(username)){
                        if(validateEmail(email)){
                            if(validatePassword(password)){
                                if(checkKey(key)){//If key exists
                                    if(!checkEmail(email)){// If the email is already registered
                                        try {
                                            password = passwordEncryption.encrypt(password);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        addUser(username,password,email,phoneNumber,key, address);
                                        Intent i = new Intent(signUpActivity.this,MainActivity.class);
                                        Toast.makeText(getApplicationContext(),"Account created successfully!", Toast.LENGTH_SHORT).show();
                                        startActivity(i);
                                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Email is already registered!", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(),"Invalid Key!", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(),"Invalid Password!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"Invalid Email!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Invalid Username!", Toast.LENGTH_SHORT).show();
                    }



                }else{
                    Toast.makeText(getApplicationContext(),"All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private class retrieveKeys extends AsyncTask<String, Void,String> {
        String res = "";
        Connection con = null;

        @Override
        protected String doInBackground(String... params) {
            int key = 0;
            try {
                ResultSet rs = null;
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();
                rs = st.executeQuery(params[0]);

                while(rs.next()){
                    key = rs.getInt("id");
                    global_keys.add(key);
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

            return res;
        }
    }

    private class retrieveEmails extends AsyncTask<String, Void,String> {
        String res = "";
        Connection con = null;

        @Override
        protected String doInBackground(String... params) {
            String email="";
            try {
                ResultSet rs = null;
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();
                rs = st.executeQuery(params[0]);

                while(rs.next()){
                    email = rs.getString("email");
                    global_emails.add(email);
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

            return res;
        }
    }

    private class addUserToDB extends AsyncTask<String, Void,String> {
        String res = "";
        Connection con = null;

        @Override
        protected String doInBackground(String... params) {
            int key = 0;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();

                for(int i=0; i<31; i++){
                    st.executeUpdate(params[i]);
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

            return res;
        }

    }
}
