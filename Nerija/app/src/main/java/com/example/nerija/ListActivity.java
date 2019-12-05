package com.example.nerija;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListActivity extends AppCompatActivity
{

    AlarmBaseData alarmBaseData;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Button tv = findViewById(R.id.listBack);
        Intent intent = getIntent();
        final String depPlaceId, arrPlaceId, depPlanDate;
        ListView list = findViewById(R.id.list);
        int searchMode = 0;
        ArrayList<String> time = new ArrayList<>();
        final UserInputData userInputData = (UserInputData) intent.getSerializableExtra("userInputData");
        depPlaceId = userInputData.getDepartPlaceID();
        arrPlaceId = userInputData.getArrivalPlaceID();
        depPlanDate = formatChange(userInputData.getDate());
        searchMode = intent.getIntExtra("searchMode",0);//1이면 기차, 2이면 시외버스
        SearchManager SM = new SearchManager(depPlaceId,arrPlaceId,depPlanDate,time,searchMode);



        SM.start();//쓰레드 실행
        while(!SM.isReady());//리스트 받아오는거 기다리는 중


        if(time.size()==0)
        {
            time.add("선택할 수 있는 시간표가 존재하지 않습니다.");
            tv.setVisibility(View.VISIBLE);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),TrainInputActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,time);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String time;//리스트뷰의 데이터 가져오기
                ListView lv = findViewById(R.id.list);
                time = String.valueOf(lv.getItemAtPosition(position));
                if(!time.equals("선택할 수 있는 시간표가 존재하지 않습니다."));
                {
                    Date selectedDate = dateParsing(depPlanDate,time);
                    alarmBaseData = new AlarmBaseData(selectedDate, userInputData.getDepartPlaceName());
                    askUseMessageService(alarmBaseData);
                }
            }
        });
    }

    public String formatChange(Date date)//데이터 20190101형태로 변환하는 메소드
    {
        SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
        String tempData =tempDate.format(date);
        String[] splitData = tempData.split("-");//Date 타입의 - 떼기
        StringBuilder returnData = new StringBuilder();
        for(int i=0;i<splitData.length;i++)
        {
            returnData.append(splitData[i]);//데이터 이어 붙이기
        }
        return returnData.toString();
    }

    public Date dateParsing(String depPlanDate,String time)
    {
        SimpleDateFormat parsing = new SimpleDateFormat("yyyyMMddHHmm");
        String temp = depPlanDate+time.substring(10,12)+time.substring(15,17);
        Log.d("확인",temp);
        Date date =null;
        try {
            date = parsing.parse(temp);
            Log.d("확인2",date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private void askUseMessageService(final AlarmBaseData alarmBaseData)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("메시지 발신").setMessage("알람이 울릴때 메시지 발신하도록 예약 하시겠습니까?");

        builder.setPositiveButton("네", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Intent intent = new Intent(getApplicationContext(),phoneNumActivity.class);
                intent.putExtra("alarmBaseData",alarmBaseData);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");
                Intent temp2 = new Intent(getApplicationContext(),endActivity.class);
                temp2.putExtra("goto",alarmBaseData.getArrivalPlaceName());
                Alarm alarm = new Alarm();

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "default");

                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentTitle("내리자 - "+ alarmBaseData.getArrivalPlaceName());
                builder.setContentText(sdf.format(alarmBaseData.date) + " 도착 5분전 알람");

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
                }
                notificationManager.notify(1, builder.build());
               // alarmBaseData.date = new Date();
                alarm.StartAlarm(getApplicationContext(),temp2,alarmBaseData);

                Toast.makeText(getApplicationContext(),"알람 등록",Toast.LENGTH_SHORT).show();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
