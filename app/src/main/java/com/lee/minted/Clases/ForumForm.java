package com.lee.minted.Clases;

import java.util.Date;
import java.util.HashMap;


public class ForumForm {
        public String date;
        public String appartment;
        public String header;
        public String message;
        public Boolean showMsg = false;
        public HashMap<String,ForumMessageForm> followingMessagedHash = new HashMap<String,ForumMessageForm>();

        public ForumForm() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public ForumForm(String date, String appartment, String header, String message,HashMap<String,ForumMessageForm> followingMessagedHash) {
            this.date = date;
            this.appartment = appartment;
            this.header = header;
            this.message = message;
            this.followingMessagedHash = followingMessagedHash;
        }
    }

