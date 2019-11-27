package com.example.nerija;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity
{

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();
        String depPlaceId, arrPlaceId, depPlanDate;
        ListView list = findViewById(R.id.list);
        int searchMode = 0;
        ArrayList<String> time = new ArrayList<>();
        InputManager IM = (InputManager) intent.getSerializableExtra("IM");
        depPlaceId = IM.getDepartPlace();
        arrPlaceId = IM.getArrivalPlace();
        SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
        String tempData =tempDate.format(IM.getDate());
        depPlanDate = tempData.substring(0,4)+tempData.substring(5,7)+tempData.substring(8,10);
        searchMode = intent.getIntExtra("searchMode",0);//1이면 기차, 2이면 시외버스
        SearchManager SM = new SearchManager(depPlaceId,arrPlaceId,depPlanDate,time,searchMode);
        SM.start();//쓰레드 실행
        while(!SM.isReady());//리스트 받아오는거 기다리는 중
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,time);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String time;//리스트뷰의 데이터 가져오기
                ListView lv = findViewById(R.id.list);
                time = String.valueOf(lv.getItemAtPosition(position));
                Log.d("시간",time);
            }
        });
    }

}
