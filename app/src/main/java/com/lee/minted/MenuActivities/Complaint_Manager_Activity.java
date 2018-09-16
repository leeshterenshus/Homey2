package com.lee.minted.MenuActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class Complaint_Manager_Activity extends AppCompatActivity {

    HashMap<Integer,String> dayOfWeek = new HashMap<Integer,String>(){{
        put(1,"א"); put(2,"ב"); put(3,"ג");put(4,"ד");put(5,"ה");put(6,"ו");put(7,"ש");
    }};

    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;
    private User mUser;
    private String mComplaintIssue;
    private String TAG = "Complaint_Activity";
    HashMap<String,ComplaintForm> mComplaintFormHash = new HashMap<String,ComplaintForm>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tlunot);
        Intent intent = getIntent();
        mUser = (User)intent.getSerializableExtra("user");
        setOnClickListeners();
        initDatabases();
        addDataFromServer();

    }

    private void initDatabases() {
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Complaints/");
//        mRef.child("Complaints").child(String.valueOf(mUser.appartment));

        ChildEventListener usersChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    ComplaintForm cf = ds.getValue(ComplaintForm.class);
                    mComplaintFormHash.put(ds.getKey(), cf);
                    renderView();
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    ComplaintForm cf = ds.getValue(ComplaintForm.class);
                    mComplaintFormHash.put(ds.getKey(), cf);
                    renderView();
                }
                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    ComplaintForm cf = ds.getValue(ComplaintForm.class);
                    mComplaintFormHash.put(ds.getKey(), cf);
                    renderView();
                }
                }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    ComplaintForm cf = ds.getValue(ComplaintForm.class);
                    mComplaintFormHash.put(ds.getKey(), cf);
                    renderView();
                }

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(Complaint_Manager_Activity.this, "Failed to load complaints.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mRef.addChildEventListener(usersChildEventListener);
    }

    private void addDataFromServer(){

    }


    private void setOnClickListeners(){


        FloatingActionButton FAB_AddComplaint = (FloatingActionButton)findViewById(R.id.FAB_AddComplaint);
        FAB_AddComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComplaint();
            }
        });
    }

    private void popupFailureDialog(FailureForm f) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Complaint_Manager_Activity.this);

        alertDialogBuilder.setTitle("העבר תלונה לתקלות");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView= inflater.inflate(R.layout.activity_add_takala, null);

        final EditText message = (EditText)dialogView.findViewById(R.id.ETmsg);
        final String[] failureStatus = new String[1];

        message.setText(f.issue);

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
                        DatabaseReference failureRef = mDatabase.getReference("Failures/");
                        failureRef.child(getTime()).setValue(cf);

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


    private void addComplaint(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Complaint_Manager_Activity.this);

        alertDialogBuilder.setTitle("תלונה חדשה");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView= inflater.inflate(R.layout.adding_complaint_layout, null);

        TextView TV_date = (TextView) dialogView.findViewById(R.id.TV_date);
        String dateTitle = "יום "+getDayInWeek()+"' "+ getDate();
        TV_date.setText(dateTitle);

        final EditText complaintTV = (EditText)dialogView.findViewById(R.id.ETmsg);

        Spinner spinner = (Spinner) dialogView.findViewById(R.id.SPsubject);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
//                Toast.makeText(adapterView.getContext(),
//                        "OnItemSelectedListener : " + adapterView.getItemAtPosition(pos).toString(),
//                        Toast.LENGTH_SHORT).show();
                updateComplaintIssue(adapterView.getItemAtPosition(pos).toString());
//                complaintIssue[0] = adapterView.getItemAtPosition(pos).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        alertDialogBuilder
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("אשר",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        String msg = complaintTV.getText().toString();
                        ComplaintForm cf = new ComplaintForm(getDate(), mUser.appartment, mComplaintIssue, msg,"טרם טופל");
                        writeNewComplaint(cf);

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

    private void updateComplaintIssue(String issue){
        mComplaintIssue= issue;
    }

    private void addComplaintToView(final ComplaintForm complain){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.compainsSV);

        Space space = new Space(Complaint_Manager_Activity.this);
        space.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                25));
        linearLayout.addView(space);

        for (int i=0; i<3; i++){
            TextView txt = new TextView(Complaint_Manager_Activity.this);
            switch(i){
                case 0:
                    txt.setTypeface(null, Typeface.BOLD);
                    txt.setText(complain.message);
                    txt.setTextSize(15);
                    break;
                case 1:
                    txt.setText("דירה: "+complain.appartment);
                    break;
                case 2:
                    txt.setText("תאריך: "+complain.date);
                    break;
            }
            txt.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            txt.setBackgroundColor(Color.WHITE);
//            txt.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    String toastMsg = "date: "+complain.date+", do you want to delete me?";
//                    Toast.makeText(Complaint_Manager_Activity.this,toastMsg,Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//            });
            linearLayout.addView(txt);
        }
        LinearLayout buttonsLL = new LinearLayout(Complaint_Manager_Activity.this);
        Button btn = new Button(Complaint_Manager_Activity.this);
        btn.setBackgroundColor(Color.RED);
        btn.setText("העבר לתקלות");
        btn.setTextColor(Color.WHITE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FailureForm f = new FailureForm(complain.date, complain.message, null);
                popupFailureDialog(f);
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                100
        );
        btn.setLayoutParams(params);
        btn.setPadding(3,3,0,3);

        linearLayout.addView(btn);
    }

    private void renderView(){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.compainsSV);
        linearLayout.removeAllViews();
        List<ComplaintForm> compList = new ArrayList<>();
        Map<String, ComplaintForm> map = new TreeMap<String, ComplaintForm>(mComplaintFormHash);
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry me = (Map.Entry)iterator.next();
            compList.add((ComplaintForm) me.getValue());
        }
        Collections.reverse(compList);
        for(ComplaintForm cf :compList ){
            addComplaintToView(cf);
        }
    }

    private String getDate()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yy ");
        return mdformat.format(calendar.getTime());
    }

    private String getDayInWeek()
    {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek.get(day);
    }

    private String getTime()
    {
        Date date = new Date();
        return String.valueOf(date.getTime());
    }

    private void writeNewComplaint(ComplaintForm cf) {
        mRef.child(String.valueOf(cf.appartment)).child(getTime()).setValue(cf);
    }



}
