package com.lee.minted.MenuActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lee.minted.Clases.FailureForm;
import com.lee.minted.Clases.IncomEAndExpencesForm;
import com.lee.minted.Clases.User;
import com.lee.minted.R;

import java.util.HashMap;


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
    private ImageButton btn_info;

    HashMap<String,FailureForm> mFailuresFormHash = new HashMap<String,FailureForm>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        mUser = (User)intent.getSerializableExtra("user");

        initFailureDatabase();
        initButtons();
        setOnClickListeners();
        displayManagerIcons();

        renderFailureView();


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
        btn_info             =(ImageButton)findViewById(R.id.btn_info);
    }

    private void setOnClickListeners() {
        btn_sharedRoom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Toast.makeText(Menu_Activity.this, "feature is not ready yet", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Menu_Activity.this,shared_room.class);
                intent.putExtra("user",mUser);
                startActivity(intent);
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

        btn_service.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this,service.class);
                startActivity(intent);
            }
        });

        btn_payment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this,payment.class);
                startActivity(intent);
            }
        });

        btn_info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showInfoAlertDialog();
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

    private void showInfoAlertDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Menu_Activity.this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView= inflater.inflate(R.layout.info_dialog, null);

        alertDialogBuilder
                .setView(dialogView)
                .setCancelable(true);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void initFailureDatabase() {
        FirebaseDatabase Database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = Database.getReference("Failures/");

        ChildEventListener usersChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                FailureForm f = dataSnapshot.getValue(FailureForm.class);
                mFailuresFormHash.put(dataSnapshot.getKey(), f);
                renderFailureView();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                FailureForm f = dataSnapshot.getValue(FailureForm.class);
                mFailuresFormHash.put(dataSnapshot.getKey(), f);
                renderFailureView();
                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                mFailuresFormHash.remove(dataSnapshot.getKey());
                renderFailureView();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                FailureForm f = dataSnapshot.getValue(FailureForm.class);
                mFailuresFormHash.put(dataSnapshot.getKey(), f);
                renderFailureView();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        Ref.addChildEventListener(usersChildEventListener);


        DatabaseReference userRef;
        userRef = Database.getReference("users/"+mUser.appartment);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUser =  dataSnapshot.getValue(User.class);
                renderFailureView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void renderFailureView() {
        Button failures_number_btn = (Button)findViewById(R.id.failures_number_btn);
        failures_number_btn.setClickable(false);
        int i = 0;
        for (String s: mFailuresFormHash.keySet()){
            if (Long.valueOf(s)>Long.valueOf(mUser.lastFailureSeen)){
                i++;
            }
        }
        if (i==0){
            failures_number_btn.setVisibility(View.INVISIBLE);
        } else {
            failures_number_btn.setVisibility(View.VISIBLE);
            failures_number_btn.setText(String.valueOf(i));
        }
    }


}
