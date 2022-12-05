package com.example.myapplication.models;

public class room {
    int id;
    String name;
    int manual_control;
    int brightness;
    int motion_sense;


    public room() {
        this.id = -1;
        this.name = "ND";
        //this.manual_control=0;
        //this.brightness=0;
        //this.motion_sense=0;
    }

    public void defineParameters(int id, String name){
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
