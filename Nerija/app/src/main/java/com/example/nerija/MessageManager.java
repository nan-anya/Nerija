package com.example.nerija;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MessageManager
{
    private String messageString;
    private NamePhoneNum receiver;

    private Context appContext;
    private Activity activity;



    public MessageManager(Activity ac)
    {
        activity = ac;

        this.appContext = ac.getApplicationContext();

        if ( Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(appContext, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.SEND_SMS}, 0);
        }

    }

    public void makeMessage(String arrival)
    {
        messageString = "[<내리자>에서 발신]\n" +receiver.getName() +"님! 지금 "  + arrival + "근처에 도착 했습니다." ;
    }

    public void transmitMessage(String phoneNum)
    {
        try
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNum, null, messageString, null, null);
            Toast.makeText(appContext, "전송 완료!", Toast.LENGTH_LONG).show();
        }

        catch (Exception e)
        {
            Toast.makeText(appContext, "SMS faild, please try again later!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public NamePhoneNum getReceiver() {
        return receiver;
    }
    public void setReceiver(NamePhoneNum receiver) {
        this.receiver = receiver;
    }

    public String getMessageString() {
        return messageString;
    }
}

class NamePhoneNum
{
    private String name;
    private String phoneNum;

    public NamePhoneNum(String name, String phoneNum)
    {
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public NamePhoneNum()
    {
    }

    public String getPhoneNum()
    {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public  String toString()
    {
        return name + " : " + phoneNum;
    }
}