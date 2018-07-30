package com.lee.minted.MenuActivities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lee.minted.Clases.EventRequestForm;
import com.lee.minted.Clases.User;
import com.lee.minted.MenuActivities.Menu_Activity;
import com.lee.minted.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;

public class forum extends AppCompatActivity {

    private String TAG = "Forum Ativity";
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        initButtons();

    }

    private void initButtons()
    {
        final ImageButton back= (ImageButton)findViewById(R.id.back_bu);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(forum.this,Menu_Activity.class
                );
                startActivity(intent);
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddMaazanMsg);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                showDialog();
//            }
//        });
    }

//    private void setForumDatabase() {
//        mDatabase = FirebaseDatabase.getInstance();
//        mRef = mDatabase.getReference("Forum");
//        mEventFormHash.clear();
//
//
//        ChildEventListener usersChildEventListener = new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
//
//                EventRequestForm erf = dataSnapshot.getValue(EventRequestForm.class);
//                mEventFormHash.put(erf.time, erf);
//                showEventHandler(calendarDay);
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
//
//                EventRequestForm erf = dataSnapshot.getValue(EventRequestForm.class);
//                mEventFormHash.put(erf.time, erf);
//                showEventHandler(calendarDay);
//
//                // ...
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
//                EventRequestForm erf = dataSnapshot.getValue(EventRequestForm.class);
//                mEventFormHash.remove(erf.time);
//                showEventHandler(calendarDay);
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
//
//                EventRequestForm erf = dataSnapshot.getValue(EventRequestForm.class);
//                mEventFormHash.put(erf.time, erf);
//                showEventHandler(calendarDay);
//
//
//                // ...
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
//                Toast.makeText(shared_room.this, "Failed to load events.",
//                        Toast.LENGTH_SHORT).show();
//            }
//        };
//        mRef.addChildEventListener(usersChildEventListener);
//    }

    //comment

}
