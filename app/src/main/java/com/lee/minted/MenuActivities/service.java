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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lee.minted.Clases.IncomEAndExpencesForm;
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


public class service extends AppCompatActivity {

    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;

    private String TAG = "Service_Activity";
    HashMap<String,ServiceForm> mServiceFormHash = new HashMap<String,ServiceForm>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Intent intent = getIntent();
        initDatabases();
        initButtons();
    }

    private void initButtons() {
        final ImageButton back= (ImageButton)findViewById(R.id.back_bu);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(service.this,Menu_Activity.class
                );
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fabAddServiceMsg);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }


    private void initDatabases() {
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Service/Messages");

        ChildEventListener usersChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                ServiceForm form = dataSnapshot.getValue(ServiceForm.class);
                mServiceFormHash.put(dataSnapshot.getKey(), form);
                renderView();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                ServiceForm form = dataSnapshot.getValue(ServiceForm.class);
                mServiceFormHash.put(dataSnapshot.getKey(), form);
                renderView();
                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                mServiceFormHash.remove(dataSnapshot.getKey());
                renderView();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                ServiceForm form = dataSnapshot.getValue(ServiceForm.class);
                mServiceFormHash.put(dataSnapshot.getKey(), form);
                renderView();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(service.this, "Failed to load Service activity.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mRef.addChildEventListener(usersChildEventListener);
    }

    private void showAlertDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(service.this);

        alertDialogBuilder.setTitle("רשומה חדשה");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.adding_service, null);

        final TextView title = (TextView)dialogView.findViewById(R.id.TV_Service_date);
        title.setText(getDate()+"תאריך: ");
        final EditText issue = (EditText)dialogView.findViewById(R.id.ETmsg);

        alertDialogBuilder
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("אשר",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        String issueMsg =  issue.getText().toString();
                        ServiceForm form = new ServiceForm(getDate(),issueMsg);
                        mRef.child(getTime()).setValue(form);
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
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.seviceList);
        linearLayout.removeAllViews();
        List<ServiceForm> list = new ArrayList<>();
        Map<String, ServiceForm> map = new TreeMap<String, ServiceForm>(mServiceFormHash);
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry me = (Map.Entry)iterator.next();
            list.add((ServiceForm) me.getValue());
        }
        Collections.reverse(list);

        for(int i = 0; i<list.size();i++)
        {
            addFormToView(list.get(i));
        }
    }

    private void addFormToView(ServiceForm f) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.seviceList);

        Space space = new Space(service.this);
        space.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                15));
        linearLayout.addView(space);

        LayoutInflater inflater = this.getLayoutInflater();
        View msgView= inflater.inflate(R.layout.service_message, null);
        msgView.setBackgroundColor(Color.WHITE);

        TextView title = (TextView)msgView.findViewById(R.id.tvTitle);
        TextView date = (TextView)msgView.findViewById(R.id.tvDate);


        title.setText(f.issue);
        date.setText("תאריך: "+f.date);

        linearLayout.addView(msgView);

    }
}
