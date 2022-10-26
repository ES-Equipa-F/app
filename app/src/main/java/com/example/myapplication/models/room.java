package com.example.myapplication.models;

public class room {
    int id;
    String name;

    public room() {
        this.id = -1;
        this.name = "ND";
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
