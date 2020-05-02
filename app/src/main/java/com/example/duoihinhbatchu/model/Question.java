package com.example.duoihinhbatchu.model;

public class Question {
    private int id;
    private String content;
    private String giaiNghia;
    private int ok;

    public Question(){}

    public Question(int id, String content, String giaiNghia, int ok) {
        this.id = id;
        this.content = content;
        this.giaiNghia = giaiNghia;
        this.ok = ok;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGiaiNghia() {
        return giaiNghia;
    }

    public void setGiaiNghia(String giaiNghia) {
        this.giaiNghia = giaiNghia;
    }

    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }
}
