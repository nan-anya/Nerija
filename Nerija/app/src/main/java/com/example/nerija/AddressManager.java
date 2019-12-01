package com.example.nerija;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class AddressManager
{
    private Context appContext;
    private Activity activity;

    private ArrayList<NamePhoneNum> namePhoneNums;

    public AddressManager(Activity ac)
    {
        activity = ac;

        this.appContext = ac.getApplicationContext();

        if ( Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(appContext, android.Manifest.permission.READ_CONTACTS ) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_CONTACTS}, 0);
        }

        makePhoneNumList();
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

    public ArrayList<NamePhoneNum> getNamePhoneNums()
    {
        return namePhoneNums;
    }
}
