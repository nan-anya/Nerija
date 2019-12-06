package com.example.nerija;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Date;

public class phoneNumActivity extends AppCompatActivity
{

    AlarmBaseData alarmBaseData;

    Button listBackButton;

    ListView list;

    AddressManager AM;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_num);

        Intent intent = getIntent();
        alarmBaseData = (AlarmBaseData) intent.getSerializableExtra("alarmBaseData");

        listBackButton = findViewById(R.id.listBack);

        list = findViewById(R.id.list);

        AM = new AddressManager(this);

        userSelect();
    }

    private void userSelect()
    {
        ArrayList<NamePhoneNum> nps = AM.getNamePhoneNums();

        ArrayList<String> npss = AM.getAddressesString();

        npss.remove(0);//자기 자신 번호 삭제

        if (nps.size() == 1)
        {
            npss.add("주소록에 주소가 없습니다.");
        }

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, npss);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String nameNum = String.valueOf(list.getItemAtPosition(position));

                NamePhoneNum temp = new NamePhoneNum();

                if (!nameNum.equals("주소록에 주소가 없습니다."))
                {
                    for(NamePhoneNum i : AM.getNamePhoneNums())
                    {
                        if(i.toString().equals(nameNum))
                        {
                            temp = i;
                            break;
                        }
                    }

                    alarmBaseData.setReciverName(temp.getName());
                    alarmBaseData.setPhoneNum(temp.getPhoneNum());
                }

                makeNotification();
                makeAlarm(alarmBaseData);
            }
        });


    }

    private void makeAlarm(AlarmBaseData alarmBaseData)
    {
        Intent intent = new Intent(getApplicationContext(),endActivity.class);
        intent.putExtra("goto",alarmBaseData.getArrivalPlaceName());

        Alarm alarm = new Alarm();
        alarm.StartAlarm(getApplicationContext(), intent, alarmBaseData);
        Toast.makeText(getApplicationContext(),"알람 등록",Toast.LENGTH_SHORT).show();
        ActivityCompat.finishAffinity(this);
    }

    private void makeNotification()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "default");
        builder.setOngoing(true);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("내리자 - "+ alarmBaseData.getArrivalPlaceName());
        builder.setContentText(sdf.format(alarmBaseData.date) + " 도착 5분전 알람");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }
        notificationManager.notify(1, builder.build());

    }
}
