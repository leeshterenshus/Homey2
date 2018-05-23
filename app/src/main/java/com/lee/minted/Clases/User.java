package com.lee.minted.Clases;
import java.io.Serializable;

@SuppressWarnings("serial")

public class User implements Serializable{

    public String username;
    public String usernameHeb;
    public String phone;
    public int appartment;
    public int floor;
    public int parking;
    public int storage;
    public boolean isManager;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String hebName, String phone, int appartment, int floor, int parking, int storage, boolean isManager) {
        this.usernameHeb = hebName;
        this.username = username;
        this.phone = phone;
        this.appartment = appartment;
        this.floor = floor;
        this.isManager = isManager;
        this.parking = parking;
        this.storage = storage;
    }

}
