package com.lee.minted.Clases;

import java.util.Date;



public class IncomEAndExpencesForm {
    public String date;
    public Float amount;
    public String message;
    public int incomeOrExpense;

    public IncomEAndExpencesForm() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public IncomEAndExpencesForm(String date,Float amount, String message, int incomeOrExpense ) {
        this.date = date;
        this.amount = amount;
        this.message = message;
        this.incomeOrExpense = incomeOrExpense;
    }
}
