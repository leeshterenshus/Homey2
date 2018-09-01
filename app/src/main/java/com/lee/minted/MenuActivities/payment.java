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
import com.lee.minted.Clases.PaymentForm;
import com.lee.minted.Clases.ServiceForm;
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
    private FirebaseDatabase mDatabase;

    private String TAG = "Payment_Activity";
    HashMap<String,PaymentForm> mPaymentFormHash = new HashMap<String,PaymentForm>();
    HashMap<String,Boolean> mPaymentListHash = new HashMap<String,Boolean>();

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
        setOnClickListeners();
        calculateCurrentMonth(MONTH_CHANGED.INIT);
    }

    private void setOnClickListeners() {
        Button nextMonthBtn = (Button)findViewById(R.id.btnNextMonth);
        Button lastMonthBtn = (Button)findViewById(R.id.btnLastMonth);

        nextMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCurrentMonth(MONTH_CHANGED.FORWARD);
                updatePaymentList();
            }
        });

        lastMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCurrentMonth(MONTH_CHANGED.BACKWARD);
                updatePaymentList();
            }
        });
    }

    private void updatePaymentList() {
        TextView tvCurrMonth = (TextView)findViewById(R.id.tvCurrentMonth);
        PaymentForm pf =  mPaymentFormHash.get(tvCurrMonth.getText().toString());
        for (int i: pf.monthlyPaymentList)
        {

        }
    }

    private void calculateCurrentMonth(MONTH_CHANGED direction) {
        TextView tvCurrMonth = (TextView)findViewById(R.id.tvCurrentMonth);
        String month = tvCurrMonth.getText().toString().split("/")[0];
        String year = tvCurrMonth.getText().toString().split("/")[1];

        int newMonth = 0;
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
                    newMonth = 0;
                    newYear = Integer.valueOf(year)+1;
                }
                break;
            case BACKWARD:
                if (Integer.valueOf(month) > 0){
                    newMonth = Integer.valueOf(month)-1;
                    newYear = Integer.valueOf(year);
                } else {
                    newMonth = 12;
                    newYear = Integer.valueOf(year)-1;
                }
                break;
        }

        tvCurrMonth.setText(String.valueOf(newMonth)+"/"+String.valueOf(newYear));
    }

    private void initButtons() {
        final ImageButton back= (ImageButton)findViewById(R.id.back_bu);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(payment.this,Menu_Activity.class
                );
                startActivity(intent);
            }
        });

    }


    private void initDatabases() {
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Payment");

        ChildEventListener usersChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                PaymentForm form = dataSnapshot.getValue(PaymentForm.class);
                mPaymentFormHash.put(dataSnapshot.getKey(), form);
//                renderView();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                PaymentForm form = dataSnapshot.getValue(PaymentForm.class);
                mPaymentFormHash.put(dataSnapshot.getKey(), form);
//                renderView();
                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                mPaymentFormHash.remove(dataSnapshot.getKey());
//                renderView();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                PaymentForm form = dataSnapshot.getValue(PaymentForm.class);
                mPaymentFormHash.put(dataSnapshot.getKey(), form);
//                renderView();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(payment.this, "Failed to load Service activity.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mRef.addChildEventListener(usersChildEventListener);
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
//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.paymentList);
//        linearLayout.removeAllViews();
//        List<ServiceForm> list = new ArrayList<>();
//        Map<String, ServiceForm> map = new TreeMap<String, ServiceForm>(mServiceFormHash);
//        Set set = map.entrySet();
//        Iterator iterator = set.iterator();
//        while(iterator.hasNext()) {
//            Map.Entry me = (Map.Entry)iterator.next();
//            list.add((ServiceForm) me.getValue());
//        }
//        Collections.reverse(list);
//
//        for(int i = 0; i<list.size();i++)
//        {
//            addFormToView(list.get(i));
//        }
    }

    private void addFormToView(ServiceForm f) {
//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.seviceList);
//
//        Space space = new Space(payment.this);
//        space.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.FILL_PARENT,
//                15));
//        linearLayout.addView(space);
//
//        LayoutInflater inflater = this.getLayoutInflater();
//        View msgView= inflater.inflate(R.layout.service_message, null);
//        msgView.setBackgroundColor(Color.WHITE);
//
//        TextView title = (TextView)msgView.findViewById(R.id.tvTitle);
//        TextView date = (TextView)msgView.findViewById(R.id.tvDate);
//
//
//        title.setText(f.issue);
//        date.setText("תאריך: "+f.date);
//
//        linearLayout.addView(msgView);

    }
}
