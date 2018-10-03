package com.lee.minted.MenuActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
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
import com.lee.minted.Clases.ForumForm;
import com.lee.minted.Clases.ForumMessageForm;
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

public class forum extends AppCompatActivity {

    HashMap<Integer,String> dayOfWeek = new HashMap<Integer,String>(){{
        put(1,"א"); put(2,"ב"); put(3,"ג");put(4,"ד");put(5,"ה");put(6,"ו");put(7,"ש");
    }};

    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;
    private User mUser;
    private String mForumMsg;
    private String TAG = "Forum_Activity";
    HashMap<String,ForumForm> mForumFormHash = new HashMap<String,ForumForm>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        Intent intent = getIntent();
        mUser = (User)intent.getSerializableExtra("user");
        setOnClickListeners();
        initDatabases();
        addDataFromServer();

    }

    private void initDatabases() {
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Forum/");

        ChildEventListener usersChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                ForumForm f = dataSnapshot.getValue(ForumForm.class);
                mForumFormHash.put(dataSnapshot.getKey(), f);
                renderView();


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
                ForumForm f = dataSnapshot.getValue(ForumForm.class);
                mForumFormHash.put(dataSnapshot.getKey(), f);
                renderView();
                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                ForumForm f = dataSnapshot.getValue(ForumForm.class);
                mForumFormHash.put(dataSnapshot.getKey(), f);
                renderView();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
                ForumForm f = dataSnapshot.getValue(ForumForm.class);
                mForumFormHash.put(dataSnapshot.getKey(), f);
                renderView();
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(forum.this, "Failed to load forum messages.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mRef.addChildEventListener(usersChildEventListener);
    }

    private void addDataFromServer(){

    }


    private void setOnClickListeners(){
        FloatingActionButton FAB_AddForumMsg = (FloatingActionButton)findViewById(R.id.FAB_AddForumMsg);
        FAB_AddForumMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addForumMessage();
            }
        });
    }


    private void addReplyForumMessage(final ForumForm forumForm){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(forum.this);

        alertDialogBuilder.setTitle("הוסף תגובה");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView= inflater.inflate(R.layout.adding_forum_msg, null);

        TextView TV_date = (TextView) dialogView.findViewById(R.id.TV_date);
        String dateTitle = "יום "+getDayInWeek()+"' "+ getDate();
        TV_date.setText(dateTitle);

        final EditText title = (EditText)dialogView.findViewById(R.id.etTitle);
        final EditText message = (EditText)dialogView.findViewById(R.id.etMessage);

        alertDialogBuilder
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("אשר",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        String filledInMsg = message.getText().toString();
                        String filledInTitle = title.getText().toString();
                        ForumMessageForm f = new ForumMessageForm(getDate(), mUser.appartment, filledInTitle,filledInMsg);
                        forumForm.followingMessagedList.add(f);

                        updateMessage(forumForm);

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


    private void addForumMessage(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(forum.this);

        alertDialogBuilder.setTitle("הודעה חדשה");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView= inflater.inflate(R.layout.adding_forum_msg, null);

        TextView TV_date = (TextView) dialogView.findViewById(R.id.TV_date);
        String dateTitle = "יום "+getDayInWeek()+"' "+ getDate();
        TV_date.setText(dateTitle);

        final EditText title = (EditText)dialogView.findViewById(R.id.etTitle);
        final EditText message = (EditText)dialogView.findViewById(R.id.etMessage);

        alertDialogBuilder
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("אשר",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        String filledInMsg = message.getText().toString();
                        String filledInTitle = title.getText().toString();
                        ForumForm f = new ForumForm(getDate(), mUser.appartment, filledInTitle,filledInMsg, null);
                        writeNewMessage(f);

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
        mForumMsg = issue;
    }

    private void addMessageToView(final ForumForm forumForm){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.SVforum);

        Space space = new Space(forum.this);
        space.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                15));
        linearLayout.addView(space);

        final LinearLayout llText = new LinearLayout(forum.this);
        llText.setOrientation(LinearLayout.VERTICAL);


        TextView txt1 = new TextView(forum.this);
        txt1.setText(forumForm.header);
        txt1.setTypeface(null, Typeface.BOLD);
        txt1.setTextSize(15);
        txt1.setTextColor(Color.WHITE);
        txt1.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
//        txt1.setBackgroundColor(Color.WHITE);

        TextView txt2 = new TextView(forum.this);
        txt2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
//        txt2.setBackgroundColor(Color.WHITE);
        txt2.setText("דירה: "+forumForm.appartment);
        txt2.setTextColor(Color.WHITE);


        TextView txt3 = new TextView(forum.this);
        txt3.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
//        txt3.setBackgroundColor(Color.WHITE);
        txt3.setText("תאריך: "+forumForm.date);
        txt3.setTextColor(Color.WHITE);


        ImageButton editBtn = new ImageButton(forum.this);
        editBtn.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editBtn.setImageResource(R.drawable.reply);
        editBtn.setBackgroundColor(Color.WHITE);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReplyForumMessage(forumForm);
            }
        });
//        editBtn.setBackgroundColor((mUser.appartment.equals(message.appartment))?Color.BLUE:Color.WHITE);
        editBtn.setBackgroundColor(Color.rgb(0,191,255));

        llText.addView(txt1);
        llText.addView(txt2);
        llText.addView(txt3);
        llText.addView(editBtn);


//        llText.setBackgroundColor((mUser.appartment.equals(message.appartment))?Color.BLUE:Color.WHITE);
        llText.setBackgroundColor(Color.rgb(0,191,255));

        llText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llText.getChildCount() == 4) {
                    llText.removeViewAt(1);
                } else {
                    final TextView txt = new TextView(forum.this);
                    txt.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
//                    txt.setBackgroundColor(Color.WHITE);
                    txt.setText(forumForm.message);
                    llText.addView(txt,1);
                }
            }
        });

        llText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(llText);
        addFollowingMessagesToView(forumForm);


//        }
    }

    private void addFollowingMessagesToView(final ForumForm forumForm){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.SVforum);
        for (final ForumMessageForm fmf: forumForm.followingMessagedList){

            Space space = new Space(forum.this);
            space.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    15));
            linearLayout.addView(space);

            final LinearLayout llText = new LinearLayout(forum.this);
            llText.setOrientation(LinearLayout.VERTICAL);


            TextView txt1 = new TextView(forum.this);
            txt1.setText(fmf.header);
            txt1.setTypeface(null, Typeface.BOLD);
            txt1.setTextSize(15);
            txt1.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            TextView txt2 = new TextView(forum.this);
            txt2.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            txt2.setText("דירה: "+fmf.appartment);


            TextView txt3 = new TextView(forum.this);
            txt3.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            txt3.setText("תאריך: "+fmf.date);

            llText.addView(txt1);
            llText.addView(txt2);
            llText.addView(txt3);

            llText.setBackgroundColor(Color.WHITE);

            llText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (llText.getChildCount() == 4) {
                        llText.removeViewAt(1);
                    } else {
                        final TextView txt = new TextView(forum.this);
                        txt.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.FILL_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        txt.setText(fmf.message);
                        llText.addView(txt,1);
                    }
                }
            });
            linearLayout.addView(llText);
        }
    }

    private void renderView(){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.SVforum);
        linearLayout.removeAllViews();
        List<ForumForm> forumList = new ArrayList<>();
        Map<String, ForumForm> map = new TreeMap<String, ForumForm>(mForumFormHash);
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry me = (Map.Entry)iterator.next();
            forumList.add((ForumForm) me.getValue());
        }
        Collections.reverse(forumList);
        for(ForumForm f :forumList ){
            addMessageToView(f);
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

    private void writeNewMessage(ForumForm f) {
        mRef.child(getTime()).setValue(f);
    }

    private void updateMessage(ForumForm f) {
        //run on hash to get key
        String relevantKey = "";
        for (String key: mForumFormHash.keySet()){
            if (mForumFormHash.get(key)==f){
                relevantKey = key;
                break;
            }
        }
        mRef.child(relevantKey).setValue(f);
    }



}
