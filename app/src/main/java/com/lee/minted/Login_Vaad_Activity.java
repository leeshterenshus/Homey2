package com.lee.minted;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login_Vaad_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__vaad_);

        Button login_Va_Bu= (Button)findViewById(R.id.Vaad_Login_Bu);

        login_Va_Bu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Login_Vaad_Activity.this, Menu_Vaad_Activity.class);
                startActivity(intent);
            }
        });


    }
}
