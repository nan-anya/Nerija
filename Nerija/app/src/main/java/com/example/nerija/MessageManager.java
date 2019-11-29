package com.example.nerija;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MessageManager
{
    private boolean sendMessage;
    private String messageString;
    private namePhoneNum receiver;

    private Context appContext;
    private Activity activity;


    public MessageManager(Activity ac)
    {
        activity = ac;

        this.appContext = ac.getApplicationContext();

        if ( Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(appContext, android.Manifest.permission.READ_CONTACTS ) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_CONTACTS}, 0);
        }
        if ( Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(appContext, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.SEND_SMS}, 0);
        }
    }

    void makeMessage(String arrival)
    {
        messageString = "[<내리자>에서 발신]\n" +receiver.getName() +"님 지금 "  + arrival + "근처에 도착 했습니다." ;
    }

    public ArrayList<namePhoneNum> getAddresses()
    {
        ArrayList<namePhoneNum> dataList = new ArrayList<namePhoneNum>();
        Cursor c = appContext.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " asc");

        while (c.moveToNext())
        {
            namePhoneNum temp = new namePhoneNum();

            String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));

            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
            temp.setName(name);

            Cursor phoneCursor = appContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);

            if (phoneCursor.moveToFirst())
            {
                String number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                temp.setName(number);
            }

            phoneCursor.close();
            dataList.add(temp);
        }
        c.close();

        return dataList;
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

    public namePhoneNum getReceiver() {
        return receiver;
    }
    public void setReceiver(namePhoneNum receiver) {
        this.receiver = receiver;
    }
    public String getMessageString() {
        return messageString;
    }
}

class namePhoneNum
{
    private String name;
    private String phoneNum;

    public namePhoneNum(String name, String phoneNum)
    {
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public namePhoneNum()
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
}