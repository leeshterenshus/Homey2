package com.lee.minted.Clases;

import java.util.Date;


    public class ForumForm {
        public String date;
        public String appartment;
        public String header;
        public String message;
        public Boolean showMsg = false;

        public ForumForm() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public ForumForm(String date, String appartment, String header, String message) {
            this.date = date;
            this.appartment = appartment;
            this.header = header;
            this.message = message;
        }
    }

