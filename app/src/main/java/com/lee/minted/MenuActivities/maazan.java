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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lee.minted.Clases.ComplaintForm;
import com.lee.minted.Clases.IncomEAndExpencesForm;
import com.lee.minted.Clases.User;
import com.lee.minted.MenuActivities.Menu_Activity;
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


public class maazan extends AppCompatActivity {

    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mAmountRef;

    private User mUser;
    private String mComplaintIssue;
    private String TAG = "income_and_expenses_Activity";
    HashMap<String,IncomEAndExpencesForm> mMaazanFormHash = new HashMap<String,IncomEAndExpencesForm>();
    private float mCurrentAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maazan);
        Intent intent = getIntent();
        mUser = (User)intent.getSerializableExtra("user");
        initDatabases();
        initButtons();


    }

    private void initButtons() {


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fabAddMaazanMsg);
        if (!mUser.isManager)
            fab.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }


//    income_and_expenses_layout


    private void initDatabases() {
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("IncomeAndExpenses/Messages");
        mAmountRef = mDatabase.getReference("IncomeAndExpenses/Amount");

        ChildEventListener usersChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                IncomEAndExpencesForm form = dataSnapshot.getValue(IncomEAndExpencesForm.class);
                mMaazanFormHash.put(dataSnapshot.getKey(), form);
                renderView();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                IncomEAndExpencesForm form = dataSnapshot.getValue(IncomEAndExpencesForm.class);
                mMaazanFormHash.put(dataSnapshot.getKey(), form);
                renderView();
                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                mMaazanFormHash.remove(dataSnapshot.getKey());
                renderView();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                IncomEAndExpencesForm form = dataSnapshot.getValue(IncomEAndExpencesForm.class);
                mMaazanFormHash.put(dataSnapshot.getKey(), form);
                renderView();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(maazan.this, "Failed to load maazan activity.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mRef.addChildEventListener(usersChildEventListener);

        mAmountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mCurrentAmount =  dataSnapshot.getValue(Float.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showAlertDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(maazan.this);

        alertDialogBuilder.setTitle("רשומה חדשה");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView= inflater.inflate(R.layout.income_and_expenses_layout, null);

        final EditText title = (EditText)dialogView.findViewById(R.id.etTitle);
        final EditText payment = (EditText)dialogView.findViewById(R.id.etAmount);
        final RadioButton rbIncome = (RadioButton) dialogView.findViewById(R.id.rbIncome);
        final RadioButton rbExspence = (RadioButton) dialogView.findViewById(R.id.rbExspence);
        final int[] incomeOrExpense = {-1};

        rbIncome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    incomeOrExpense[0] = 1;
                    rbExspence.setChecked(false);
                }
            }
        });

        rbExspence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    incomeOrExpense[0] = -1;
                    rbIncome.setChecked(false);
                }
            }
        });

        alertDialogBuilder
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("אשר",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        if (title.getText().toString().equals("") ||  payment.getText().toString().equals(""))
                        {
                            Toast.makeText(maazan.this, "please enter valid title and amount", Toast.LENGTH_SHORT).show();
                            return;
                        } else
                        {
                            float fPayment =  Float.parseFloat(payment.getText().toString());
                            IncomEAndExpencesForm form = new IncomEAndExpencesForm(getDate(),fPayment,title.getText().toString(), incomeOrExpense[0]);
                            mRef.child(getTime()).setValue(form);
                            mAmountRef.setValue(mCurrentAmount+ incomeOrExpense[0]*fPayment);
                        }
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

    void renderView()
    {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llIncomeAndExpensesList);
        linearLayout.removeAllViews();
        List<IncomEAndExpencesForm> list = new ArrayList<>();
        Map<String, IncomEAndExpencesForm> map = new TreeMap<String, IncomEAndExpencesForm>(mMaazanFormHash);
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry me = (Map.Entry)iterator.next();
            list.add((IncomEAndExpencesForm) me.getValue());
        }
        float balance = 0;
        List<Float> balanceList = new ArrayList<>();

        for(IncomEAndExpencesForm f :list ){
            balance+=f.incomeOrExpense*f.amount;
            balanceList.add(balance);
        }
        Collections.reverse(list);
        Collections.reverse(balanceList);

        for(int i = 0; i<list.size();i++)
        {
            addFormToView(list.get(i), balanceList.get(i));
        }
    }

    private void addFormToView(IncomEAndExpencesForm f, float gloabalBalance) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llIncomeAndExpensesList);

        Space space = new Space(maazan.this);
        space.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                15));
        linearLayout.addView(space);

        LayoutInflater inflater = this.getLayoutInflater();
        View msgView= inflater.inflate(R.layout.income_and_expenses_message, null);
        msgView.setBackgroundColor(Color.WHITE);

        TextView title = (TextView)msgView.findViewById(R.id.tvTitle);
        TextView amount = (TextView)msgView.findViewById(R.id.tvAmount);
        TextView balance = (TextView)msgView.findViewById(R.id.tvBalance);
        TextView date = (TextView)msgView.findViewById(R.id.tvDate);
        ImageView icon = (ImageView)msgView.findViewById(R.id.ivBalance);


        title.setText(f.message);
        amount.setText("סך: "+f.amount);
        date.setText("תאריך: "+f.date);
        balance.setText("יתרה: "+gloabalBalance);
        icon.setImageResource((f.incomeOrExpense>0)?R.drawable.income:R.drawable.expense);

        linearLayout.addView(msgView);

    }
}
