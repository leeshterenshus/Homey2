package com.lee.minted.Clases;


import java.util.List;

public class PaymentForm {
    public String date;
    public List<Integer> monthlyPaymentList;

    public PaymentForm() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public PaymentForm(String date,List<Integer> monthlyPaymentList) {
        this.date = date;
        this.monthlyPaymentList = monthlyPaymentList;
    }
}

