package com.lee.minted.Clases;

public class UserAuth {

    private String username;
    private String password;

    public UserAuth() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

}
