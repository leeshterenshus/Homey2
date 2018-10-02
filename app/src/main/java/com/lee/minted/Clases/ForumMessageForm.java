package com.lee.minted.Clases;


public class ForumMessageForm {
    public String date;
    public String appartment;
    public String header;
    public String message;
    public Boolean showMsg = false;

    public ForumMessageForm() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ForumMessageForm(String date, String appartment, String header, String message) {
        this.date = date;
        this.appartment = appartment;
        this.header = header;
        this.message = message;
    }
}

