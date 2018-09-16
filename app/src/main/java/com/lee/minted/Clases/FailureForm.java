package com.lee.minted.Clases;

public class FailureForm {
    public String date;
    public String issue;
    public String status;

    public FailureForm() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public FailureForm(String date, String issue, String status) {
        this.date = date;
        this.issue = issue;
        this.status = status;
    }
}
