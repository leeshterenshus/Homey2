package com.lee.minted;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.view.View;
import android.widget.ImageButton;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Hadar_Dayarim extends AppCompatActivity {

public SimpleDateFormat dataForamtMonth=new SimpleDateFormat("MMM-YYYY", Locale.getDefault());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hadar__dayarim);

        final ImageButton back= (ImageButton)findViewById(R.id.back_bu);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Hadar_Dayarim.this,Menu_Dayar_Activity.class
                );
                startActivity(intent);
            }
        });
        final ImageButton add= (ImageButton)findViewById(R.id.add_bu);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Hadar_Dayarim.this,add_activity_toCalendar_dayar.class
                );
                startActivity(intent);
            }
        });

      // final ActionBar actionBar= getSupportActionBar();
      // actionBar.setDisplayHomeAsUpEnabled(false);
      //  actionBar.setTitle(null);

        //compactCalendar = (CompactCalendarView)findViewById(R.id.compactcalendar_view);
        //compactCalendar.setUseThreeLetterAbbreviation(true);


    }
}
