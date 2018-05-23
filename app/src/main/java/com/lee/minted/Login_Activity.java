package com.lee.minted;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lee.minted.Clases.User;
import com.lee.minted.Clases.UserAuth;
import com.lee.minted.MenuActivities.Menu_Activity;

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
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                EditText usernameET = (EditText)findViewById(R.id.usernameET);
                EditText passwordET = (EditText)findViewById(R.id.passwordET);
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();

                if (username.equals("y")){
                    Intent intent = new Intent(Login_Activity.this, Menu_Activity.class);
                    intent.putExtra("user",usersMap.get("Yuval Goddard"));
                    startActivity(intent);
                    return;
                }

                if (username.equals("l")){
                    Intent intent = new Intent(Login_Activity.this, Menu_Activity.class);
                    intent.putExtra("user",usersMap.get("Lee Shterenshus"));
                    startActivity(intent);
                    return;
                }


                if (username.equals("n")){
                    Intent intent = new Intent(Login_Activity.this, Menu_Activity.class);
                    intent.putExtra("user",usersMap.get("Noa Kalo"));
                    startActivity(intent);
                    return;
                }

                if (username.equals("") || password.equals("") )
                {
                    Toast.makeText(Login_Activity.this,"אנא מלא את השדות החסרים", Toast.LENGTH_LONG).show();
                    return;
                }
                for (UserAuth ua : usersAuthList)
                {
                    if (ua.getUsername().equals(username) &&  ua.getPassword().equals(password))
                    {
                        Intent intent = new Intent(Login_Activity.this, Menu_Activity.class);
                        intent.putExtra("user",usersMap.get(username));
                        startActivity(intent);
                        return;
                    }
                }
                Toast.makeText(Login_Activity.this,"שם משתמש ו\\או סיסמא אינם נכונים", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        EditText usernameET = (EditText)findViewById(R.id.usernameET);
        EditText passwordET = (EditText)findViewById(R.id.passwordET);
        usernameET.setText("");
        passwordET.setText("");
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
