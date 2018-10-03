package com.lee.minted.Clases;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class ForumForm {
        public String date;
        public String appartment;
        public String header;
        public String message;
        public Boolean showMsg = false;
        public ArrayList<ForumMessageForm> followingMessagedList = new ArrayList<ForumMessageForm>();

        public ForumForm() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public ForumForm(String date, String appartment, String header, String message, ArrayList<ForumMessageForm> followingMessagedList) {
            this.date = date;
            this.appartment = appartment;
            this.header = header;
            this.message = message;
            this.followingMessagedList = followingMessagedList;
        }
    }

