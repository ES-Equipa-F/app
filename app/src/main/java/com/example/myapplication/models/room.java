package com.example.myapplication.models;

import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class room {
    String id;
    String name;
    int manual_control;
    int brightness;
    int motion_sense;
    ConstraintLayout CL;
    SeekBar sB;
    Switch manual_switch;
    Switch motion_switch;
    TextView motion_text;
    ImageView brightness_icon;

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

    public SeekBar getsB() {
        return sB;
    }

    public void setsB(SeekBar sB) {
        this.sB = sB;
    }

    public Switch getManual_switch() {
        return manual_switch;
    }

    public void setManual_switch(Switch manual_switch) {
        this.manual_switch = manual_switch;
    }

    public Switch getMotion_switch() {
        return motion_switch;
    }

    public void setMotion_switch(Switch motion_switch) {
        this.motion_switch = motion_switch;
    }

    public TextView getMotion_text() {
        return motion_text;
    }

    public void setMotion_text(TextView motion_text) {
        this.motion_text = motion_text;
    }

    public ImageView getBrightness_icon() {
        return brightness_icon;
    }

    public void setBrightness_icon(ImageView brightness_icon) {
        this.brightness_icon = brightness_icon;
    }

    public void updateControlsDisplay(){
        int state;
        if( this.manual_control == 1) state = View.VISIBLE;
        else state = View.INVISIBLE;

        this.motion_text.setVisibility(state);
        this.motion_switch.setVisibility(state);
        this.brightness_icon.setVisibility(state);
        this.sB.setVisibility(state);
    }
}
