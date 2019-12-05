package com.example.nerija;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class geontakActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tak_temp);
        Alarm alarm = new Alarm();
        AlarmBaseData alarmBaseData = new AlarmBaseData();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Intent intent = new Intent(getApplicationContext(),endActivity.class);
        Date date = null;
        try {
            date = sdf.parse("2019-12-05 20:54");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        alarmBaseData.setDate(date);
        alarmBaseData.setPhoneNum("01062221447");
        alarmBaseData.setReciverName("남도훈");
        alarmBaseData.setArrivalPlaceName("창원중앙역");
        alarm.StartAlarm(getApplicationContext(),intent,alarmBaseData);
    }

}