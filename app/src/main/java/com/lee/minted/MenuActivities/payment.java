package com.lee.minted.MenuActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lee.minted.Clases.PaymentForm;
import com.lee.minted.Clases.ServiceForm;
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


public class payment extends AppCompatActivity {

    private DatabaseReference mRef;
    private DatabaseReference mUsersRef;
    private FirebaseDatabase mDatabase;

    private String TAG = "Payment_Activity";
    HashMap<String,PaymentForm> mPaymentFormHash = new HashMap<String,PaymentForm>(); //list of payment for every month
    ArrayList<Integer> mUsersList = new ArrayList<Integer>(); // global list of users in system
    ArrayList<Integer> mUsersPaymentList = new ArrayList<Integer>();   // users who paid this month

    private String currentMonth;


    enum MONTH_CHANGED {
        INIT,
        FORWARD,
        BACKWARD
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent intent = getIntent();
        initDatabases();
        initButtons();
        calculateCurrentMonth(MONTH_CHANGED.INIT);
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        Button nextMonthBtn = (Button)findViewById(R.id.btnNextMonth);
        Button lastMonthBtn = (Button)findViewById(R.id.btnLastMonth);

        nextMonthBtn.setText(">>");
        lastMonthBtn.setText("<<");

        nextMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCurrentMonth(MONTH_CHANGED.FORWARD);
                mUsersPaymentList.clear();
                renderView();
            }
        });

        lastMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCurrentMonth(MONTH_CHANGED.BACKWARD);
                mUsersPaymentList.clear();
                renderView();
            }
        });

        nextMonthBtn.performClick();
        lastMonthBtn.performClick();
    }

//    private void updatePaymentList() {
//        TextView tvCurrMonth = (TextView)findViewById(R.id.tvCurrentMonth);
//        PaymentForm pf =  mPaymentFormHash.get(tvCurrMonth.getText().toString());
//        for (int i: pf.monthlyPaymentList)
//        {
//
//        }
//    }

    private void calculateCurrentMonth(MONTH_CHANGED direction) {
        TextView tvCurrMonth = (TextView)findViewById(R.id.tvCurrentMonth);
        String tvCurrMonthTxt = tvCurrMonth.getText().toString();
        String month = tvCurrMonthTxt.split("\\\\")[0];
        String year =  tvCurrMonthTxt.split("\\\\")[1];

        int newMonth = 1;
        int newYear = 0;

        switch (direction){
            case INIT:
                newMonth = Integer.valueOf(getDate().split("/")[1]);
                newYear = Integer.valueOf(getDate().split("/")[2].split(" ")[0]);
                break;
            case FORWARD:
                if (Integer.valueOf(month) < 12){
                    newMonth = Integer.valueOf(month)+1;
                    newYear = Integer.valueOf(year);
                } else {
                    newMonth = 1;
                    newYear = Integer.valueOf(year)+1;
                }
                break;
            case BACKWARD:
                if (Integer.valueOf(month) > 1){
                    newMonth = Integer.valueOf(month)-1;
                    newYear = Integer.valueOf(year);
                } else {
                    newMonth = 12;
                    newYear = Integer.valueOf(year)-1;
                }
                break;
        }

        currentMonth = String.valueOf(newMonth)+"\\"+String.valueOf(newYear);
        tvCurrMonth.setText(currentMonth);

    }

    private void initButtons() {

    }


    private void initDatabases() {
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Payment");
        mUsersRef = mDatabase.getReference("users");

        ChildEventListener usersChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                PaymentForm form = dataSnapshot.getValue(PaymentForm.class);
                mPaymentFormHash.put(dataSnapshot.getKey(), form);
                renderView();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                PaymentForm form = dataSnapshot.getValue(PaymentForm.class);
                mPaymentFormHash.put(dataSnapshot.getKey(), form);
                renderView();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                mPaymentFormHash.remove(dataSnapshot.getKey());
                renderView();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                PaymentForm form = dataSnapshot.getValue(PaymentForm.class);
                mPaymentFormHash.put(dataSnapshot.getKey(), form);
                renderView();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(payment.this, "Failed to load Service activity.",
                        Toast.LENGTH_SHORT).show();
                                renderView();

            }
        };
        mRef.addChildEventListener(usersChildEventListener);

        mUsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    mUsersList.add(Integer.valueOf(ds.getKey()));
                }
                renderView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    void renderView()
    {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.paymentList);
        linearLayout.removeAllViews();
        Collections.sort(mUsersList);
        for (final int i: mUsersList)
        {
            LayoutInflater inflater = this.getLayoutInflater();
            View paymentRow = inflater.inflate(R.layout.payment_row, null);

            CheckBox cb = (CheckBox)paymentRow.findViewById(R.id.checkBox);
            cb.setText("דירה "+String.valueOf(i));
            if (mPaymentFormHash.containsKey(currentMonth))
              cb.setChecked(mPaymentFormHash.get(currentMonth).monthlyPaymentList.contains(i));
            cb.setTextColor(Color.BLACK);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        if (!mUsersPaymentList.contains(i)){
                            mUsersPaymentList.add(i);
                            uploadPaymentForm();
                        }
                    } else {
                        if (mUsersPaymentList.contains(i)){
                            mUsersPaymentList.remove(mUsersPaymentList.indexOf(i));
                            uploadPaymentForm();
                        }
                    }
                }
            });

            linearLayout.addView(paymentRow);
        }
    }

    void uploadPaymentForm()
    {
        PaymentForm pf = new PaymentForm(currentMonth,mUsersPaymentList);
        mRef.child(currentMonth).setValue(pf);
    }
}
