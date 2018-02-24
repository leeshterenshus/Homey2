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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__dayar_);



        ImageButton information_Button= (ImageButton)findViewById(R.id.inforamtion_bu);

        information_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Dayar_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }



}
