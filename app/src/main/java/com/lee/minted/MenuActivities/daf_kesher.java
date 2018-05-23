package com.lee.minted.MenuActivities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lee.minted.Clases.User;
import com.lee.minted.R;

import java.util.HashMap;

public class daf_kesher extends AppCompatActivity {

    private HashMap<String, User> usersMap = new HashMap<>();
    final String TAG = "daf_kesher";
    private DatabaseReference mUsersRef;
    private FirebaseDatabase mDatabase;
    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daf_kesher);


        Intent intent = getIntent();
        mUser = (User)intent.getSerializableExtra("user");

        initDatabases();

        final Button go_profile= (Button)findViewById(R.id.go_to_profile_bu);

//        go_profile.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(daf_kesher.this, com.lee.minted.dayarProfile.class
//                );
//                startActivity(intent);
//            }
//        });

        renderUsersPhoneTable();
    }

    private void initDatabases() {
        mDatabase = FirebaseDatabase.getInstance();
        mUsersRef = mDatabase.getReference("users");

        ChildEventListener usersChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                User user = dataSnapshot.getValue(User.class);
                usersMap.put(user.username, user);
                String commentKey = dataSnapshot.getKey();
                renderUsersPhoneTable();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                User newUser = dataSnapshot.getValue(User.class);
                usersMap.put(newUser.username, newUser);
                String commentKey = dataSnapshot.getKey();
                renderUsersPhoneTable();
                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String commentKey = dataSnapshot.getKey();
                User user = dataSnapshot.getValue(User.class);
                usersMap.remove(user.username);
                renderUsersPhoneTable();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                User movedUser = dataSnapshot.getValue(User.class);
                usersMap.put(movedUser.username, movedUser);
                String commentKey = dataSnapshot.getKey();
                renderUsersPhoneTable();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(daf_kesher.this, "Failed to load users.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mUsersRef.addChildEventListener(usersChildEventListener);
    }

    private void renderUsersPhoneTable(){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.scrollViewLiniarLayout);
        linearLayout.removeAllViews();
        for (String username : usersMap.keySet()){
            if (username.equals(mUser.username)) continue;

            TextView txt = new TextView(daf_kesher.this);
            User user = usersMap.get(username);
            String userDetails ="<b>"+ "דירה "+ user.appartment + "</b>" +", קומה " +user.floor+"\n"+"<br/>"+
                    "<b>"+"איש קשר: "+"</b>"+user.usernameHeb;
            if (mUser.isManager){
                userDetails+="<br/>"+"<b>"+ "\nטלפון: "+"</b>"+user.phone+"<br/>"+"<b>"+"\nחניה: "+"</b>"+user.parking+"<br/>"+"<b>"+"\nמחסן: "+"</b>"+user.storage;
            }
            txt.setText(Html.fromHtml(userDetails));
            txt.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            txt.setTextSize(18);
            txt.setBackgroundColor(Color.WHITE);
            Space space = new Space(daf_kesher.this);
            space.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    15));
            linearLayout.addView(space);
            linearLayout.addView(txt);
        }
    }
}
