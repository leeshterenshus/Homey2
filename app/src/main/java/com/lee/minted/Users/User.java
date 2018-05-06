package com.lee.minted.Users;

public class User {

    public String username;
    public String phone;
    public int appartment;
    public int floor;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String phone, int appartment, int floor) {
        this.username = username;
        this.phone = phone;
        this.appartment = appartment;
        this.floor = floor;
    }

}
