package com.lee.minted.MenuActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lee.minted.Clases.User;
import com.lee.minted.R;
import com.lee.minted.forum;
import com.lee.minted.maazan;


public class Menu_Activity extends AppCompatActivity {

    private User mUser;

    private ImageButton btn_contacts;
    private ImageButton btn_failures;
    private ImageButton btn_complaints;
    private ImageButton btn_forum;
    private ImageButton btn_sharedRoom;
    private ImageButton btn_incomeAndResults;
    private ImageButton btn_service;
    private ImageButton btn_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        mUser = (User)intent.getSerializableExtra("user");

        initButtons();
        setOnClickListeners();
        displayManagerIcons();

    }

    private void initButtons() {
        btn_contacts         =(ImageButton)findViewById(R.id.btn_contacts);
        btn_failures         =(ImageButton)findViewById(R.id.btn_failures);
        btn_complaints       =(ImageButton)findViewById(R.id.btn_complaints);
        btn_forum            =(ImageButton)findViewById(R.id.btn_forum);
        btn_sharedRoom       =(ImageButton)findViewById(R.id.btn_sharedRoom);
        btn_incomeAndResults =(ImageButton)findViewById(R.id.btn_incomeAndResults);
        btn_service          =(ImageButton)findViewById(R.id.btn_service);
        btn_payment          =(ImageButton)findViewById(R.id.btn_payment);
    }

    private void setOnClickListeners() {
        btn_sharedRoom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(Menu_Activity.this, "feature is not ready yet", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Menu_Dayar_Activity.this,shared_room.class);
//                intent.putExtra("user",mUser);
//                startActivity(intent);
            }
        });


        btn_contacts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this,contacts.class);
                intent.putExtra("user",mUser);
                startActivity(intent);
            }
        });

        btn_complaints.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent;
                if (mUser.isManager){
                    intent = new Intent(Menu_Activity.this,Complaint_Manager_Activity.class);
                } else{
                    intent = new Intent(Menu_Activity.this,Complaint_Activity.class);
                }
                intent.putExtra("user",mUser);
                startActivity(intent);
            }
        });

        btn_failures.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this,failures.class);
                intent.putExtra("user",mUser);
                startActivity(intent);
            }
        });
        btn_forum.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this,forum.class);
                intent.putExtra("user",mUser);
                startActivity(intent);
            }
        });

        btn_incomeAndResults.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this,maazan.class);
                intent.putExtra("user",mUser);
                startActivity(intent);
            }
        });
    }

    private void displayManagerIcons() {
        LinearLayout managerIcons = (LinearLayout)findViewById(R.id.linearLayout3);
        if (mUser.isManager == true){
            managerIcons.setVisibility(View.VISIBLE);
        } else{
            managerIcons.setVisibility(View.INVISIBLE);
        }
    }


}
