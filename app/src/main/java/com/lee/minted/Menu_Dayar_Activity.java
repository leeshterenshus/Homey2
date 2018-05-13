package com.lee.minted;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Menu_Dayar_Activity extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__dayar_);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");


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
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });


        Hadar_dayarim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Dayar_Activity.this,Hadar_Dayarim.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });


        dayarim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Dayar_Activity.this,daf_kesher.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        tlunot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Dayar_Activity.this,tlunot.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        takalot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Dayar_Activity.this,takalot.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        forum.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Dayar_Activity.this,forum.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        maazan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Dayar_Activity.this,maazan.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

    }



}
