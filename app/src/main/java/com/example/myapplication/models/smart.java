package com.example.myapplication.models;

import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class smart {
    String id;
    String name;
    int smart_on;
    int brightness;
    int motion_sense;
    int start_time;
    int end_time;
    SeekBar sB;
    Switch motion_switch;
    Switch on;
    ImageView brightness_icon;

    public smart(String id, String name, int smart_on, int brightness, int motion_sense, int start_time, int end_time) {
        this.id = id;
        this.name = name;
        this.smart_on = smart_on;
        this.brightness = brightness;
        this.motion_sense = motion_sense;
        this.start_time = start_time;
        this.end_time = end_time;
    }


    public String smart_getId() {
        return id;
    }

    public void smart_setId(String id) {
        this.id = id;
    }

    public String smart_getName() {
        return name;
    }

    public void smart_setName(String name) {
        this.name = name;
    }

    public int smart_getOn() {
        return smart_on;
    }

    public void smart_setOn(int smart_on) {
        this.smart_on = smart_on;
    }

    public int smart_getBrightness() {
        return brightness;
    }

    public void smart_setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int smart_getMotion_sense() {
        return motion_sense;
    }

    public void smart_setMotion_sense(int motion_sense) {
        this.motion_sense = motion_sense;
    }

    public int smart_getStart_time() {
        return start_time;
    }

    public void smart_setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int smart_getEnd_time() {
        return end_time;
    }

    public void smart_setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public SeekBar smart_getsB() {
        return sB;
    }

    public void smart_setsB(SeekBar sB) {
        this.sB = sB;
    }

    public Switch smart_getON_Switch() {
        return on;
    }

    public void smart_setON_switch(Switch on) {
        this.on = on;
    }

    public Switch smart_getMotion_switch() {
        return motion_switch;
    }

    public void smart_setMotion_switch(Switch motion_switch) {
        this.motion_switch = motion_switch;
    }

    public ImageView smart_getBrightness_icon() {
        return brightness_icon;
    }

    public void smart_setBrightness_icon(ImageView brightness_icon) {
        this.brightness_icon = brightness_icon;
    }

}

