package com.example.duoihinhbatchu.model;

public class History {
    private String id;
    private String time;
    private byte[] image;
    public History(){}

    public String getTime() {
        return time;
    }

    public History(String id, String time, byte[] image) {
        this.id = id;
        this.time = time;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }
}
