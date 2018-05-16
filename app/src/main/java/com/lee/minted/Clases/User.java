package com.lee.minted.Clases;
import java.io.Serializable;

@SuppressWarnings("serial")

public class User implements Serializable{

    public String username;
    public String phone;
    public int appartment;
    public int floor;
    public boolean isManager;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String phone, int appartment, int floor, boolean isManager) {
        this.username = username;
        this.phone = phone;
        this.appartment = appartment;
        this.floor = floor;
        this.isManager = isManager;
    }

}
