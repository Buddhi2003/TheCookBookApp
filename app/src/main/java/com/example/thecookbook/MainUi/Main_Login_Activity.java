package com.example.thecookbook.MainUi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thecookbook.R;

public class Main_Login_Activity extends AppCompatActivity {
    private Button login,register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_login);
        login=(Button) findViewById(R.id.btnlogin);
        register=(Button)findViewById(R.id.btnregister);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentuser =new Intent(Main_Login_Activity.this,UserLoginActivity.class);
                startActivity(intentuser);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentreg =new Intent(Main_Login_Activity.this, MainRegisterActivity.class);
                startActivity(intentreg);
            }
        });
    }
}