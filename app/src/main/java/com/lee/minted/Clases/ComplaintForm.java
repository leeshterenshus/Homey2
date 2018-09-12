package com.lee.minted.Clases;

import java.util.ArrayList;
import java.util.Date;

public class ComplaintForm {
    public String date;
    public String appartment;
    public String issue;
    public String message;
    public String status;
//    public Long time;

    public ComplaintForm() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ComplaintForm(String date, String appartment, String issue, String message,String status) {
        this.date = date;
        this.appartment = appartment;
        this.issue = issue;
        this.message = message;
        this.status = status;
    }
}
