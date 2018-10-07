package com.lee.minted.Clases;
import java.io.Serializable;

@SuppressWarnings("serial")

public class User implements Serializable{

    public String username;
    public String usernameHeb;
    public String phone;
    public String appartment;
    public String floor;
    public String parking;
    public String storage;
    public boolean isManager;
    public boolean showPhone;
    public String lastFailureSeen = "-1";


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String hebName, String phone, String appartment, String floor, String parking, String storage, boolean isManager, boolean showPhone) {
        this.usernameHeb = hebName;
        this.username = username;
        this.phone = phone;
        this.appartment = appartment;
        this.floor = floor;
        this.isManager = isManager;
        this.parking = parking;
        this.storage = storage;
        this.showPhone = showPhone;
    }

    public void setLastFailure(String failureId){
     this.lastFailureSeen = failureId;
    }

}
