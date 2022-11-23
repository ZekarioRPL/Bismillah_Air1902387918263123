package com.example.bismillah_air.API;

public class History {

    private String date, time, debu_after, debu_before;

    public History(String date, String time, String debu_after, String debu_before) {
        this.date = date;
        this.time = time;
        this.debu_after = debu_after;
        this.debu_before = debu_before;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDebu_after() {
        return debu_after;
    }

    public void setDebu_after(String debu_after) {
        this.debu_after = debu_after;
    }

    public String getDebu_before() {
        return debu_before;
    }

    public void setDebu_before(String debu_before) {
        this.debu_before = debu_before;
    }
}
