package com.lee.minted.Clases;

import java.util.Date;


public class IncomEAndExpences {
    public Date date;
    public Float amount;
    public String message;
    public Float balance;

    public IncomEAndExpences() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public IncomEAndExpences(Date date,Float amount, String message,Float balance ) {
        this.date = date;
        this.amount = amount;
        this.balance = balance;
        this.message = message;
    }
}
