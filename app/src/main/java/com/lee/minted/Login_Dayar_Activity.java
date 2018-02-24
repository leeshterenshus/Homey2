package com.lee.minted;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login_Dayar_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__dayar_);



        Button login_Da_Bu= (Button)findViewById(R.id.Dayer_Login_Bu);

        login_Da_Bu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Login_Dayar_Activity.this, Menu_Dayar_Activity.class);
                startActivity(intent);
            }
        });
    }
}
