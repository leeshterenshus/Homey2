package com.lee.minted;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.lee.minted.Clases.User;

public class Menu_Dayar_Activity extends AppCompatActivity {

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__dayar_);

        Intent intent = getIntent();
        mUser = (User)intent.getSerializableExtra("user");


        final ImageButton back= (ImageButton)findViewById(R.id.back_bu);
        final ImageButton Hadar_dayarim= (ImageButton)findViewById(R.id.heder_dayarim_bu);
        final ImageButton information= (ImageButton)findViewById(R.id.inforamtion_bu);
        final ImageButton dayarim= (ImageButton)findViewById(R.id.dayarim_bu);
        final ImageButton tlunot= (ImageButton)findViewById(R.id.tlonot_bu);
        final ImageButton takalot= (ImageButton)findViewById(R.id.takalot_bu);
        final ImageButton forum= (ImageButton)findViewById(R.id.forum_bu);
        final ImageButton maazan= (ImageButton)findViewById(R.id.maazan_bu);


//cc

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Dayar_Activity.this,MainActivity.class);
                intent.putExtra("user",mUser);
                startActivity(intent);
            }
        });


        Hadar_dayarim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Dayar_Activity.this,Hadar_Dayarim.class);
                intent.putExtra("user",mUser);
                startActivity(intent);
            }
        });


        dayarim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Dayar_Activity.this,daf_kesher.class);
                intent.putExtra("user",mUser);
                startActivity(intent);
            }
        });

        tlunot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Dayar_Activity.this,Complaint_Activity.class);
                intent.putExtra("user",mUser);
                startActivity(intent);
            }
        });

        takalot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Dayar_Activity.this,takalot.class);
                intent.putExtra("user",mUser);
                startActivity(intent);
            }
        });
        forum.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Dayar_Activity.this,forum.class);
                intent.putExtra("user",mUser);
                startActivity(intent);
            }
        });

        maazan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Dayar_Activity.this,maazan.class);
                intent.putExtra("user",mUser);
                startActivity(intent);
            }
        });

    }



}
