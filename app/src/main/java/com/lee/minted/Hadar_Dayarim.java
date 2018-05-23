package com.lee.minted;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

//import com.applikeysolutions.cosmocalendar.dialog.CalendarDialog;
//import com.applikeysolutions.cosmocalendar.dialog.OnDaysSelectionListener;
//import com.applikeysolutions.cosmocalendar.model.Day;
//import com.applikeysolutions.cosmocalendar.selection.BaseSelectionManager;
//import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener;
//import com.applikeysolutions.cosmocalendar.selection.SingleSelectionManager;
//import com.applikeysolutions.cosmocalendar.settings.appearance.ConnectedDayIconPosition;
//import com.applikeysolutions.cosmocalendar.settings.lists.connected_days.ConnectedDays;
//import com.applikeysolutions.cosmocalendar.view.CalendarView;
//import com.applikeysolutions.cosmocalendar.selection.MultipleSelectionManager;
//import com.applikeysolutions.cosmocalendar.selection.criteria.BaseCriteria;
//import com.applikeysolutions.cosmocalendar.selection.criteria.WeekDayCriteria;
//import com.applikeysolutions.cosmocalendar.selection.criteria.month.CurrentMonthCriteria;
//import com.applikeysolutions.cosmocalendar.selection.criteria.month.NextMonthCriteria;
//import com.applikeysolutions.cosmocalendar.selection.criteria.month.PreviousMonthCriteria;
//import com.applikeysolutions.cosmocalendar.utils.SelectionType;





import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

public class Hadar_Dayarim extends AppCompatActivity {


//private CalendarView mCalendarView;
private Calendar calendar;
private Set<Long> days = new TreeSet<>();
public SimpleDateFormat dataForamtMonth= new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hadar_dayarim);

        initButtons();
//        setCalendar();

    }

//    private void setCalendar() {
//        mCalendarView = (CalendarView)findViewById(R.id.calendar_view);
//        mCalendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
//        mCalendarView.setWeekendDays(new HashSet(){{
//            add(Calendar.FRIDAY);
//            add(Calendar.SATURDAY);
//        }});
//        mCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
//        mCalendarView.setSelectionType(SelectionType.SINGLE);
//        mCalendarView.setShowDaysOfWeekTitle(true);
//        days.add(calendar.getTimeInMillis());
//        days.add(1526841476296L);
//        int textColor = Color.parseColor("#ff0000");
//        int selectedTextColor = Color.parseColor("#ff4000");
//        int disabledTextColor = Color.parseColor("#ff8000");
//        ConnectedDays connectedDays = new ConnectedDays(days, textColor, selectedTextColor, disabledTextColor);
//        mCalendarView.addConnectedDays(connectedDays);
//        mCalendarView.setSelectedDayBackgroundColor(Color.parseColor("#ffff4d"));
//        SingleSelectionManager a = mCalendarView.getSelectionManager();
//        mCalendarView.setSelectionManager(new BaseSelectionManager() {
//            @Override
//            public void toggleDay(@NonNull Day day) {
//                Toast.makeText(Hadar_Dayarim.this, day.toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public boolean isDaySelected(@NonNull Day day) {
//                return false;
//            }
//
//            @Override
//            public void clearSelections() {
//
//            }
//        });

//    }

    private void initButtons() {
        calendar = Calendar.getInstance();
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
//                Intent intent = new Intent(Hadar_Dayarim.this,add_activity_toCalendar_dayar.class
//                );
//                startActivity(intent);
            }
        });
    }
}
