package com.lee.minted;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class daf_kesher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daf_kesher);

        final ImageButton back= (ImageButton)findViewById(R.id.back_bu);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(daf_kesher.this,Menu_Dayar_Activity.class
                );
                startActivity(intent);
            }
        });

        final ImageButton go_profile= (ImageButton)findViewById(R.id.go_to_profile_bu);

        go_profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(daf_kesher.this,dayarProfile.class
                );
                startActivity(intent);
            }
        });
    }
}
