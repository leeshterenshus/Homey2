package com.lee.minted.MenuActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
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
import com.lee.minted.Clases.EventRequestForm;
import com.lee.minted.decorators.EventDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import com.lee.minted.R;
import com.lee.minted.Clases.User;

import com.prolificinteractive.materialcalendarview.OnDateLongClickListener;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class shared_room extends AppCompatActivity {

    private String TAG = "Shared Room Ativity";
    private View mView;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;
    private User mUser;
    private MaterialCalendarView  mCalendarView;
    private Set<Long> days = new TreeSet<>();
    public SimpleDateFormat dataForamtMonth= new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    private CalendarDay mCurrCalanderDay;
    HashMap<String,EventRequestForm> mEventFormHash = new HashMap<String,EventRequestForm>();
    HashMap<String,ArrayList<EventRequestForm>> mMonthEvents = new HashMap<String,ArrayList<EventRequestForm>>();

    public boolean dailyEventDialogIsOn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hadar_dayarim);
        Intent intent = getIntent();
        mUser = (User)intent.getSerializableExtra("user");
        initButtons();
        setCalendar();
        initMonthEventDatabase(mCalendarView.getCurrentDate().getYear(),mCalendarView.getCurrentDate().getMonth());


    }

    private void setDailyDatabase(final CalendarDay calendarDay) {
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference(String.format("Events/%s/%s/%s", calendarDay.getYear(),calendarDay.getMonth(),calendarDay.getDay()));
        mEventFormHash.clear();


        ChildEventListener usersChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                EventRequestForm erf = dataSnapshot.getValue(EventRequestForm.class);
                mEventFormHash.put(erf.time, erf);
                showEventHandler(calendarDay);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                EventRequestForm erf = dataSnapshot.getValue(EventRequestForm.class);
                mEventFormHash.put(erf.time, erf);
                showEventHandler(calendarDay);

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                EventRequestForm erf = dataSnapshot.getValue(EventRequestForm.class);
                mEventFormHash.remove(erf.time);
                showEventHandler(calendarDay);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                EventRequestForm erf = dataSnapshot.getValue(EventRequestForm.class);
                mEventFormHash.put(erf.time, erf);
                showEventHandler(calendarDay);


                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(shared_room.this, "Failed to load events.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mRef.addChildEventListener(usersChildEventListener);
    }


    private void initMonthEventDatabase(final int year, final int month) {
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference(String.format("Events/%s/%s", year, month));
        mMonthEvents.clear();

        ChildEventListener usersChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                List<CalendarDay> calendarDays= new ArrayList<>();
                CalendarDay cd = CalendarDay.from(year,month,Integer.parseInt(dataSnapshot.getKey()));
                calendarDays.add(cd);
                mCalendarView.addDecorator(new EventDecorator(Color.GREEN, calendarDays));

//                renderView();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                ArrayList<EventRequestForm> erf_list = (ArrayList<EventRequestForm>)dataSnapshot.getValue();
                mMonthEvents.put(dataSnapshot.getKey(), erf_list);
//                renderView();
                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                ArrayList<EventRequestForm> erf_list = (ArrayList<EventRequestForm>)dataSnapshot.getValue();
                mMonthEvents.remove(dataSnapshot.getKey());
//                renderView();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                ArrayList<EventRequestForm> erf_list = (ArrayList<EventRequestForm>)dataSnapshot.getValue();
                mMonthEvents.put(dataSnapshot.getKey(), erf_list);
//                renderView();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(shared_room.this, "Failed to load events.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mRef.addChildEventListener(usersChildEventListener);
    }


    private void setCalendar() {
        mCalendarView = (MaterialCalendarView)findViewById(R.id.calendarView);
        mCalendarView.setOnDateLongClickListener(new OnDateLongClickListener() {
            @Override
            public void onDateLongClick(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay) {
                setDailyDatabase(calendarDay);
                showEventHandler(calendarDay);
            }
        });
        mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
                mCurrCalanderDay = calendarDay;
            }
        });
        mCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {
                initMonthEventDatabase(mCalendarView.getCurrentDate().getYear(),mCalendarView.getCurrentDate().getMonth());
            }
        });
    }

    private void initButtons() {
//        calendar = Calendar.getInstance();

        final ImageButton add= (ImageButton)findViewById(R.id.add_bu);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setDailyDatabase(mCurrCalanderDay);
                showEventHandler(mCurrCalanderDay);

            }
        });
    }

    private void showEventHandler(final CalendarDay calendarDay){
        dailyEventDialogIsOn = true;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(shared_room.this);
        final String myDate = calendarDay.getDay()+"/"+ calendarDay.getMonth()+"/"+ calendarDay.getYear();
        alertDialogBuilder.setTitle("הזמנה חדשה: " + myDate);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView= inflater.inflate(R.layout.adding_event_layout, null);

        LinearLayout linearLayout = (LinearLayout) dialogView.findViewById(R.id.llEvents);
        linearLayout.setBackgroundColor(Color.LTGRAY);

        int OPENING_ROOM_HOUR = 8;
        int NUM_OF_HOURS_ROOM_IS_OPEN = 15;
        for (int i=0; i<NUM_OF_HOURS_ROOM_IS_OPEN; i++) {
            final String currentHour = String.valueOf(i+OPENING_ROOM_HOUR)+":00:00";

            final LinearLayout LL = new LinearLayout(shared_room.this);
            LL.setBackgroundColor(Color.WHITE);
            LL.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            LL.setWeightSum(6f);
            LL.setLayoutParams(LLParams);



            final TextView timeTxt = new TextView(shared_room.this);
            final ImageButton btnRequest = new ImageButton(this);


            timeTxt.setText(currentHour);
            timeTxt.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            timeTxt.setBackgroundColor(Color.TRANSPARENT);
//            timeTxt.setBackgroundColor((i%4==0)?Color.rgb(0,255,0):Color.rgb(255,99,71));
            timeTxt.setTextColor(Color.BLACK);

            if (mEventFormHash.containsKey(currentHour))
            {
                EventRequestForm erf = mEventFormHash.get(currentHour);
                LL.setBackgroundColor(Color.rgb(0,255,0));
                timeTxt.setText(currentHour+"\n"+"Requested by Appartment: " + erf.appartment);
                timeTxt.setTextColor(Color.WHITE);
                btnRequest.setVisibility(View.INVISIBLE);
            }


            btnRequest.setImageResource(R.drawable.event_request);

            btnRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    String requestText = "request for room in date: "+myDate+ " at " +currentHour+ " has been sent to manager";
//                    Toast.makeText(shared_room.this, requestText, Toast.LENGTH_LONG).show();
                    List<CalendarDay> calendarDays= new ArrayList<>();
                    calendarDays.add(calendarDay);
//                    materialCalendarView.addDecorator(new EventDecorator(Color.GREEN, calendarDays));
                    EventRequestForm erf = new EventRequestForm(myDate, currentHour, mUser.appartment, "ON_REQUEST");
                    LL.setBackgroundColor(Color.rgb(255,215,0));
                    timeTxt.setText(currentHour+"\n"+"Requested by Appartment: " + mUser.appartment);
                    timeTxt.setTextColor(Color.WHITE);
                    btnRequest.setVisibility(View.INVISIBLE);

                    createNewEventRequest(erf);
                }
            });
            btnRequest.setBackgroundColor(Color.TRANSPARENT);


            LL.addView(timeTxt);
            LL.addView(btnRequest);
            LL.setId(i+8);

            Space space = new Space(shared_room.this);
            space.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    15));
            linearLayout.addView(space);
            linearLayout.addView(LL);


        }

        View mView = dialogView;

        alertDialogBuilder
                .setView(mView)
                .setCancelable(true)
                .setPositiveButton("סיום",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private String getTime()
    {
        Date date = new Date();
        return String.valueOf(date.getTime());
    }

    private void createNewEventRequest(EventRequestForm erf) {
        mRef.child(getTime()).setValue(erf);
    }


} // shared room class
