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
    private NamePhoneNum receiver;

    private Context appContext;
    private Activity activity;

    private ArrayList<NamePhoneNum> namePhoneNums;

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

        makePhoneNumList();
    }

    public void makeMessage(String arrival)
    {
        messageString = "[<내리자>에서 발신]\n" +receiver.getName() +"님! 지금 "  + arrival + "근처에 도착 했습니다." ;
    }

    private void makePhoneNumList()
    {
        namePhoneNums = new ArrayList<NamePhoneNum>();
        Cursor c = appContext.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " asc");

        while (c.moveToNext())
        {
            NamePhoneNum temp = new NamePhoneNum();

            String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));

            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
            temp.setName(name);

            Cursor phoneCursor = appContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);

            if (phoneCursor.moveToFirst())
            {
                String number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                temp.setPhoneNum(number);
            }

            phoneCursor.close();
            namePhoneNums.add(temp);
        }
        c.close();
    }

    public  ArrayList<String> getAddressesString()
    {
        ArrayList<String> output = new ArrayList<String>();

        for (NamePhoneNum i : namePhoneNums)
        {
            output.add(i.toString());
        }

        return output;
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

    public ArrayList<NamePhoneNum> getNamePhoneNums()
    {
        return namePhoneNums;
    }
    public NamePhoneNum getReceiver() {
        return receiver;
    }
    public void setReceiver(NamePhoneNum receiver) {
        this.receiver = receiver;
    }
    public void setReceiver(String namePhoneNum)
    {
        for(NamePhoneNum i : namePhoneNums)
        {
            if(i.toString().equals(namePhoneNum))
            {
                receiver = i;
                break;
            }
        }
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