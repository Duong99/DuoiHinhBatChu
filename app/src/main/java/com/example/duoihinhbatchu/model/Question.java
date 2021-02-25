package com.example.duoihinhbatchu.model;

public class Question {
    private int id;
    private String content;
    private String giaiNghia;
    private int ok;

    public Question() {
    }

    public Question(int id, String content, String giaiNghia, int ok) {
        this.id = id;
        this.content = content;
        this.giaiNghia = giaiNghia;
        this.ok = ok;
    }

    public int getId() {
        return id;
    }

    public int getOk() {
        return ok;
    }

    public String getContent() {
        return content;
    }

    public String getGiaiNghia() {
        return giaiNghia;
    }

}
