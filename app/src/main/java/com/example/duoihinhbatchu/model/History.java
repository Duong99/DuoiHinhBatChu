package com.example.duoihinhbatchu.model;

public class History {
    private String id;
    private byte[] image;
    public History(){}


    public History(String id,  byte[] image) {
        this.id = id;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }
}
