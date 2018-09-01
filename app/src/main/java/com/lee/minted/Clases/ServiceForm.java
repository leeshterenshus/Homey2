package com.lee.minted.Clases;

public class ServiceForm {
    public String date;
    public String issue;

    public ServiceForm() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ServiceForm(String date, String issue) {
        this.date = date;
        this.issue = issue;
    }
}
