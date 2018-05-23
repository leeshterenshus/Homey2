package com.lee.minted.DialogAlerts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.lee.minted.MenuActivities.contacts;
import com.lee.minted.R;

public class dayarProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayar_profile);

        final ImageButton back= (ImageButton)findViewById(R.id.back_bu);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(dayarProfile.this,contacts.class
                );
                startActivity(intent);
            }
        });
    }
}//comment
