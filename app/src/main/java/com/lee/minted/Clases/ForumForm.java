package com.lee.minted.Clases;

import java.util.Date;


    public class ForumForm {
        public Date date;
        public int appartment;
        public String header;
        public String message;

        public ForumForm() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public ForumForm(Date date, int appartment, String header, String message) {
            this.date = date;
            this.appartment = appartment;
            this.header = header;
            this.message = message;
        }
    }

