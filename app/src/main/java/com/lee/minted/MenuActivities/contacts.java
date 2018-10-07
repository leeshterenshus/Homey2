package com.lee.minted.MenuActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lee.minted.Clases.ForumForm;
import com.lee.minted.Clases.User;
import com.lee.minted.R;

import java.util.HashMap;

public class contacts extends AppCompatActivity {

    private HashMap<String, User> usersMap = new HashMap<>();
    final String TAG = "contacts";
    private DatabaseReference mUsersRef;
    private FirebaseDatabase mDatabase;
    private User mUser;
    private String mSearchString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daf_kesher);
        Intent intent = getIntent();
        mUser = (User)intent.getSerializableExtra("user");
        initDatabases();
        initButtons();
        renderUsersPhoneTable();
    }

    private void initButtons() {
        final Button btn_profile = (Button)findViewById(R.id.btn_profile);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile(mUser);
            }
        });

        final SearchView searchView = (SearchView)findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchString = query;
                renderUsersPhoneTable();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSearchString = newText;
                renderUsersPhoneTable();
                return false;
            }
        });
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
                Toast.makeText(contacts.this, "Failed to load users.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mUsersRef.addChildEventListener(usersChildEventListener);
    }

    private void renderUsersPhoneTable(){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.scrollViewLiniarLayout);
        linearLayout.removeAllViews();
        for (String username : usersMap.keySet()){
            if (username.equals(mUser.username)) {
                mUser = usersMap.get(username);
            continue;
            }

            TextView txt = new TextView(contacts.this);
            final User user = usersMap.get(username);

            String userDetails ="<b>"+ "דירה "+ user.appartment + "</b>" +", קומה " +user.floor+"\n"+"<br/>"+
                    "<b>"+"איש קשר: "+"</b>"+user.usernameHeb;
            if (mUser.isManager){
                userDetails+="<br/>"+"<b>"+ "\nטלפון: "+"</b>"+user.phone+"<br/>"+"<b>"+"\nחניה: "+"</b>"+user.parking+"<br/>"+"<b>"+"\nמחסן: "+"</b>"+user.storage;
            } else{
                if (user.showPhone){
                    userDetails+="<br/>"+"<b>"+ "\nטלפון: "+"</b>"+user.phone;
                }
            }
            txt.setText(Html.fromHtml(userDetails));
            txt.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            txt.setTextSize(18);
            txt.setBackgroundColor(Color.WHITE);
            ImageButton editBtn = new ImageButton(contacts.this);
            editBtn.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            editBtn.setImageResource(R.drawable.pencil);
            editBtn.setBackgroundColor(Color.WHITE);
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editProfile(user);
                }
            });
            Space space = new Space(contacts.this);
            space.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    15));
            if (userDetails.contains(mSearchString)){
                linearLayout.addView(space);
                linearLayout.addView(txt);
                if (mUser.isManager)
                    linearLayout.addView(editBtn);
            }

        }
    }

    private void editProfile(final User user){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(contacts.this);

        alertDialogBuilder.setTitle("עריכת פרופיל");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView= inflater.inflate(R.layout.activity_dayar_profile, null);

        final EditText contactName = (EditText)dialogView.findViewById(R.id.editContact);
        final EditText contactPhone = (EditText)dialogView.findViewById(R.id.editPhone);
        final EditText contactParking = (EditText)dialogView.findViewById(R.id.editParking);
        final EditText contactGarage = (EditText)dialogView.findViewById(R.id.editGarage);
        final CheckBox showPhone = (CheckBox)dialogView.findViewById(R.id.showPhoneCheck2);
        final Button btnOK = (Button)dialogView.findViewById(R.id.btn_ok);

        if (!mUser.isManager){
            contactName.setKeyListener(null);
            contactParking.setKeyListener(null);
            contactGarage.setKeyListener(null);
        }
        contactName.setText(user.usernameHeb);
        contactPhone.setText(user.phone);
        contactParking.setText(user.parking);
        contactGarage.setText(user.storage);
        showPhone.setChecked(user.showPhone);

        alertDialogBuilder
                .setView(dialogView)
                .setCancelable(true);


        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User updatedUser = new User(user.username,
                        contactName.getText().toString(),
                        contactPhone.getText().toString(),
                        user.appartment,
                        user.floor,
                        contactParking.getText().toString(),
                        contactGarage.getText().toString(),
                        user.isManager,
                        showPhone.isChecked() );
                mUsersRef.child(user.appartment).setValue(updatedUser);
                alertDialog.dismiss();
            }
        });


    }




}
