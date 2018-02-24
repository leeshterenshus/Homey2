package com.lee.minted;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button log_Dayar_Bu= (Button)findViewById(R.id.HomeBu_Dayar);
        Button log_Vaad_Bu= (Button)findViewById(R.id.HomeBu_Vaad);

        log_Dayar_Bu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login_Dayar_Activity.class);
                startActivity(intent);
            }
        });
        log_Vaad_Bu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login_Vaad_Activity.class);
                startActivity(intent);
            }
        });



    }


}
