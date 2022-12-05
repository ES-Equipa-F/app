package com.example.myapplication.models;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class room {
    String id;
    String name;
    int manual_control;
    int brightness;
    int motion_sense;
    ConstraintLayout CL;



    public room(String id, String name, int manual_control, int brightness, int motion_sense) {
        this.id = id;
        this.name = name;
        this.manual_control = manual_control;
        this.brightness = brightness;
        this.motion_sense = motion_sense;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getManual_control() {
        return manual_control;
    }

    public void setManual_control(int manual_control) {
        this.manual_control = manual_control;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getMotion_sense() {
        return motion_sense;
    }

    public void setMotion_sense(int motion_sense) {
        this.motion_sense = motion_sense;
    }

    public ConstraintLayout getCL() {
        return CL;
    }

    public void setCL(ConstraintLayout CL) {
        this.CL = CL;
    }

    public void show(){
        this.CL.setVisibility(View.VISIBLE);
    }
}
