package com.example.nerija;

import android.telephony.SmsManager;

public class MessageManager
{
    private String messageString;
    private String receiver;

    public MessageManager()
    {


    }

    public void makeMessage(String arrival)
    {
        messageString = "[<내리자>에서 발신]\n" +receiver +"님! 지금 "  + arrival + "근처에 도착 했습니다." ;
    }

    public void transmitMessage(String phoneNum)
    {
        try
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNum, null, messageString, null, null);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver)
    {
        this.receiver = new String(receiver);
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