package com.example.nerija;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class endActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        TextView tv = findViewById(R.id.textView);
        Intent intent = getIntent();

        tv.setText(String.format("잠시 후 %s역에 도착합니다", intent.getStringExtra("goto")));

    }
    public void on1(View v){
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(1);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
