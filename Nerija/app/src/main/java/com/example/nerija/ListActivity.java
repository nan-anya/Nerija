package com.example.nerija;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ListActivity extends AppCompatActivity
{

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
        InputManager IM = (InputManager) intent.getSerializableExtra("IM");
        depPlaceId = IM.getDepartPlace();
        arrPlaceId = IM.getArrivalPlace();
        depPlanDate = formatChange(IM.getDate());
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


}