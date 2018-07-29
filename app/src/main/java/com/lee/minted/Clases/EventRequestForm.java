package com.lee.minted.Clases;

public class EventRequestForm {
    public String date;
    public String time;
    public int appartment;
    public String status;

    public EventRequestForm() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public EventRequestForm(String date, String time, int appartment, String status) {
        this.date = date;
        this.time = time;
        this.appartment = appartment;
        this.status = status;
    }
}
