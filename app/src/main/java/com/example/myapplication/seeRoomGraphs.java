package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class seeRoomGraphs extends AppCompatActivity {

    private static final String url = "jdbc:mysql://34.155.233.25/mydb?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String pass = "db-es-teamf";

    String current_email="";
    String current_house_id="";

    TextView tV;
    String selected_room_id="";
    String selected_room_name="";

    String selected_sensor_name="";
    String selected_time_frame="";

    LineGraphSeries<DataPoint> series;

    int rows_per_minute = 60;
    int num_retrieved_rows = 0;
    int max_retrieved_rows = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_room);

        current_email = getIntent().getStringExtra("message_email");
        current_house_id = getIntent().getStringExtra("message_house_id");
        selected_room_id = getIntent().getStringExtra("selected_room_id");
        selected_room_name = getIntent().getStringExtra("selected_room_name");

        setup_Room_name();
        setup_Spinner1();
        setup_Spinner2();
        setup_ButtonGraph();

        goBack();
    }


    void setup_Room_name(){
        tV = (TextView) findViewById(R.id.room_name);
        tV.setText( selected_room_name );
    }

    void setup_Spinner1(){
        //Opções a mostrar nos spinners
        ArrayList<String> sensor_Names_For_User = new ArrayList<>();
        sensor_Names_For_User.add("Light sensor");
        sensor_Names_For_User.add("People Presence");
        sensor_Names_For_User.add("Lamp Value");

        //Setar o spinner
        Spinner spinner = findViewById(R.id.spinner_graph);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(seeRoomGraphs.this,
                android.R.layout.simple_spinner_dropdown_item, sensor_Names_For_User);
        spinner.setAdapter(adapter);

        //Listener para detetar qual é o sensor selecionado e guardar em selected_sensor_name
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getItemAtPosition(i).toString() ;
                if(selected.equals("Light sensor") ){
                    selected_sensor_name = "ldr";
                }
                else if(selected.equals("People Presence") ){
                    selected_sensor_name = "movimento";
                }
                else if(selected.equals("Lamp Value") ){
                    selected_sensor_name = "lampada";
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void setup_Spinner2(){
        //Opções a mostrar nos spinners
        ArrayList<String> time_frames_For_User = new ArrayList<>();
        time_frames_For_User.add("Last Minute");
        time_frames_For_User.add("Last Hour");
        time_frames_For_User.add("Last Day");
        time_frames_For_User.add("Last Week");

        //Setar o spinner
        Spinner spinner = findViewById(R.id.spinner_graph2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(seeRoomGraphs.this,
                android.R.layout.simple_spinner_dropdown_item, time_frames_For_User);
        spinner.setAdapter(adapter);

        //Listener para detetar qual é o sensor selecionado e guardar em selected_sensor_name
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getItemAtPosition(i).toString() ;
                if(selected.equals("Last Minute") ){
                    selected_time_frame = "minute";
                    max_retrieved_rows = rows_per_minute;
                }
                else if(selected.equals("Last Hour") ){
                    selected_sensor_name = "hour";
                    max_retrieved_rows = rows_per_minute*60;
                }
                else if(selected.equals("Last Day") ){
                    selected_sensor_name = "day";
                    max_retrieved_rows = rows_per_minute*60*24;
                }
                else if(selected.equals("Last Week") ){
                    selected_sensor_name = "week";
                    max_retrieved_rows = rows_per_minute*60*24*7;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void goBack(){
        ImageButton button = (ImageButton) findViewById(R.id.graphRoom_goBack);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(seeRoomGraphs.this,myHomeActivity.class);
                i.putExtra("message_email", current_email);
                i.putExtra("message_house_id", current_house_id);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

    }

    void setup_ButtonGraph(){
        Button button = (Button) findViewById(R.id.create_button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String select_values_query = query_for_graph();
                seeRoomGraphs.retrieveValuesFromDB retrieveValuesFromDB = new seeRoomGraphs.retrieveValuesFromDB();
                retrieveValuesFromDB.execute(select_values_query);
            }
        });
    }

    String query_for_graph(){
        String query = "";

        if( selected_sensor_name.equals("lampada") ){
            query = "SELECT " +
                    "brightness_value.value AS value, " +
                    "brightness_value.timestamp AS timestamp " +
                    "FROM brightness_value " +
                    "JOIN light " +
                    "ON brightness_value.light_id = light.id " +
                    "WHERE " +
                    "light.room_id =\""+selected_room_id + "\" "+
                    "AND timestamp >= NOW() - INTERVAL 1 " + selected_time_frame + " "+
                    "ORDER BY timestamp ASC";
        }
        else {
            query = "SELECT " +
                    "sensor_value.value AS value, " +
                    "sensor_value.timestamp AS timestamp " +
                    "FROM sensor_value " +
                    "JOIN sensor " +
                    "ON sensor_value.sensor_id = sensor.id " +
                    "WHERE " +
                    "sensor.room_id =\""+selected_room_id + "\" "+
                    "AND sensor.type =\""+selected_sensor_name + "\" "+
                    "AND timestamp >= NOW() - INTERVAL 1 " + selected_time_frame + " "+
                    "ORDER BY timestamp ASC";
        }

        return query;

    }

    private class retrieveValuesFromDB extends AsyncTask<String, Void, String> {
        String res = "";
        Connection con = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                ResultSet rs = null;
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();

                String query = params[0];
                rs = st.executeQuery(query);

                series = new LineGraphSeries<DataPoint>();
                num_retrieved_rows=0;

                //Para sacar da db
                while( rs.next() && num_retrieved_rows<max_retrieved_rows ){
                    float value = rs.getFloat("value");

                    series.appendData(new DataPoint(num_retrieved_rows, value), true, max_retrieved_rows);
                    num_retrieved_rows++;
                }

                /*
                // Para testar com valores ficticios
                for(int i=0; i<max_retrieved_rows; i++){
                    float value=1;
                    if(i<100) value = 90;
                    else if(i<300) value = 0;
                    else if(i<500) value = 10;
                    else if(i<600) value = 60;

                    series.appendData(new DataPoint(num_retrieved_rows, value), true, max_retrieved_rows);
                    num_retrieved_rows++;
                }
                 */

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
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            GraphView graph = (GraphView) findViewById(R.id.graph);

            if( num_retrieved_rows >= max_retrieved_rows*0.9 ){      //tolerancia
                graph.addSeries(series);
                graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
                graph.setVisibility(View.VISIBLE);
            }
            else{
                Toast.makeText(seeRoomGraphs.this,"Not enough data for this time frame!", Toast.LENGTH_SHORT).show();
            }

        }
    }
}


