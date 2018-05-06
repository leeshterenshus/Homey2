package com.lee.minted;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lee.minted.Users.User;
import com.lee.minted.Users.UserAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Login_Activity extends AppCompatActivity {

    private DatabaseReference mUsersRef;
    private DatabaseReference mUsersAuthRef;
    private FirebaseDatabase mDatabase;
    final List<UserAuth> usersAuthList = new ArrayList<>();
    final String TAG = "Login_Activity";
    private ArrayList<User> usersList = new ArrayList<>();
    private HashMap<String, User> usersMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        initDatabases();

        Button loginBtn= (Button)findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText usernameET = (EditText)findViewById(R.id.usernameET);
                EditText passwordET = (EditText)findViewById(R.id.passwordET);
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();

                if (username.equals("") ||
                        password.equals("") ){
                    Toast.makeText(Login_Activity.this,"אנא מלא את השדות החסרים", Toast.LENGTH_LONG).show();
                    return;
                }
                for (UserAuth ua : usersAuthList){
                    if (ua.getUsername().equals(username) &&
                            ua.getPassword().equals(password)){
                        if (usersMap.get(username).isManager){
                            Intent intent = new Intent(Login_Activity.this, Menu_Dayar_Activity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(Login_Activity.this, Menu_Vaad_Activity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });

    }

    private void initDatabases() {
        mDatabase = FirebaseDatabase.getInstance();
        mUsersRef = mDatabase.getReference("users");
        mUsersAuthRef = mDatabase.getReference("usersAuth");

        ChildEventListener usersChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                User user = dataSnapshot.getValue(User.class);
                usersList.add(user);
                usersMap.put(user.username, user);
                String commentKey = dataSnapshot.getKey();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                User newUser = dataSnapshot.getValue(User.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                User movedUser = dataSnapshot.getValue(User.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(Login_Activity.this, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mUsersRef.addChildEventListener(usersChildEventListener);

        ChildEventListener usersAuthChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                UserAuth userAuth = dataSnapshot.getValue(UserAuth.class);
                usersAuthList.add(userAuth);
                String commentKey = dataSnapshot.getKey();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                UserAuth userAuth = dataSnapshot.getValue(UserAuth.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                User movedUser = dataSnapshot.getValue(User.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(Login_Activity.this, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mUsersAuthRef.addChildEventListener(usersAuthChildEventListener);
    }
}
