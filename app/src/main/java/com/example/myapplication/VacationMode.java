package com.example.myapplication;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class VacationMode extends AppCompatActivity {
    Button startTime, endTime;
    int hour1, minute1, hour2, minute2;

    String current_email="";
    String current_house_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        current_email = getIntent().getStringExtra("message_email");
        current_house_id = getIntent().getStringExtra("message_house_id");

        setContentView(R.layout.pop_vacation);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.6));

        startTime = findViewById(R.id.vacation_start);
        endTime = findViewById(R.id.vacation_end);
        Button close_vacation = (Button) findViewById(R.id.vacation_done);
        close_vacation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                closeWindow();
            }
        });

    }

    public void closeWindow(){
        this.finish();
    }


    public void popTimeStartPicker(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker1, int selectedHour1, int selectedMinute1) {
                hour1 = selectedHour1;
                minute1 = selectedMinute1;
                startTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour1, minute1));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour1, minute1, true);
        timePickerDialog.setTitle("Select Start Time");
        timePickerDialog.show();
    }

    public void popTimeEndPicker(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker2, int selectedHour2, int selectedMinute2) {
                hour2 = selectedHour2;
                minute2 = selectedMinute2;
                endTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour2, minute2));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour2, minute2, true);
        timePickerDialog.setTitle("Select End Time");
        timePickerDialog.show();
    }

}
