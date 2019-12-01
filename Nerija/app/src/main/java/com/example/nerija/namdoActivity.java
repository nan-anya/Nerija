package com.example.nerija;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.util.Date;

public class namdoActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namdo);
        TextView tv = findViewById(R.id.textView);
        Intent intent = getIntent();

        tv.setText("잠시 후 "+intent.getStringExtra("goto")+"역에 도착합니다");

    }
    public void on1(View v){
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(1);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
