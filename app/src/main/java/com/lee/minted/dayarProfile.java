package com.lee.minted;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class dayarProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayar_profile);

        final ImageButton back= (ImageButton)findViewById(R.id.back_bu);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(dayarProfile.this,daf_kesher.class
                );
                startActivity(intent);
            }
        });
    }
}//comment
