package com.lee.minted.MenuActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lee.minted.Clases.ComplaintForm;
import com.lee.minted.Clases.FailureForm;
import com.lee.minted.Clases.User;
import com.lee.minted.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class failures extends AppCompatActivity {

    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;
    private User mUser;
    private String TAG = "Failures_Activity";
    HashMap<String,FailureForm> mFailuresFormHash = new HashMap<String,FailureForm>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takalot);

        setContentView(R.layout.activity_takalot);
        Intent intent = getIntent();
        mUser = (User)intent.getSerializableExtra("user");
        setOnClickListeners();
        initDatabases();

    }

    private void initDatabases() {
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Failures/");
//        mRef.child("Complaints").child(String.valueOf(mUser.appartment));

        ChildEventListener usersChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                FailureForm f = dataSnapshot.getValue(FailureForm.class);
                mFailuresFormHash.put(dataSnapshot.getKey(), f);
                renderView();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
                FailureForm f = dataSnapshot.getValue(FailureForm.class);
                mFailuresFormHash.put(dataSnapshot.getKey(), f);
                renderView();
                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                mFailuresFormHash.remove(dataSnapshot.getKey());
                renderView();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
                FailureForm f = dataSnapshot.getValue(FailureForm.class);
                mFailuresFormHash.put(dataSnapshot.getKey(), f);
                renderView();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(failures.this, "Failed to load failures activity.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mRef.addChildEventListener(usersChildEventListener);
    }

    private void setOnClickListeners() {
        LinearLayout addFailure = (LinearLayout)findViewById(R.id.llAddFailure);
        addFailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupFailureDialog();
            }
        });
    }

    private void popupFailureDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(failures.this);

        alertDialogBuilder.setTitle("תקלה חדשה");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView= inflater.inflate(R.layout.activity_add_takala, null);

        final EditText message = (EditText)dialogView.findViewById(R.id.ETmsg);
        final String[] failureStatus = new String[1];

        Spinner spinner = (Spinner) dialogView.findViewById(R.id.SPsubject);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                failureStatus[0] = adapterView.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                failureStatus[0] = adapterView.getItemAtPosition(0).toString();
            }
        });

        alertDialogBuilder
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("אשר",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        String failureMessage = message.getText().toString();
                        FailureForm cf = new FailureForm(getDate(), failureMessage, failureStatus[0]);
                        mRef.child(getTime()).setValue(cf);

                        // if this button is clicked, close
                        // current activity

                    }
                })
                .setNegativeButton("בטל",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }






    private String getDate()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yy ");
        return mdformat.format(calendar.getTime());
    }

    private String getTime()
    {
        Date date = new Date();
        return String.valueOf(date.getTime());
    }

    private void renderView() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.failuresScrollView);
        linearLayout.removeAllViews();
        List<FailureForm> failureList = new ArrayList<>();
        Map<String, FailureForm> map = new TreeMap<String, FailureForm>(mFailuresFormHash);
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry me = (Map.Entry)iterator.next();
            failureList.add((FailureForm) me.getValue());
        }
        Collections.reverse(failureList);
        for(FailureForm cf :failureList ){
            addFailureToView(cf);
        }
    }

    private void addFailureToView(FailureForm failureForm){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.failuresScrollView);

        Space space = new Space(failures.this);
        space.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                15));
        linearLayout.addView(space);

        for (int i=0; i<3; i++){
            TextView txt = new TextView(failures.this);
            switch(i){
                case 0:
                    txt.setTypeface(null, Typeface.BOLD);
                    txt.setText(failureForm.issue);
                    break;
                case 1:
                    txt.setText("סטטוס:"+failureForm.status);
                    break;
                case 2:
                    txt.setText("תאריך: "+failureForm.date);
                    break;
            }
            txt.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            txt.setBackgroundColor(Color.WHITE);
            linearLayout.addView(txt);


        }
    }

}
