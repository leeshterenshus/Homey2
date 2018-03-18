package com.lee.minted;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Hadar_Dayarim extends AppCompatActivity {

public SimpleDateFormat dataForamtMonth=new SimpleDateFormat("MMM-YYYY", Locale.getDefault());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hadar__dayarim);

      // final ActionBar actionBar= getSupportActionBar();
      // actionBar.setDisplayHomeAsUpEnabled(false);
      //  actionBar.setTitle(null);

        //compactCalendar = (CompactCalendarView)findViewById(R.id.compactcalendar_view);
        //compactCalendar.setUseThreeLetterAbbreviation(true);


    }
}
