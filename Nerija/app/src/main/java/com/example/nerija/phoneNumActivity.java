package com.example.nerija;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class phoneNumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_num);

        Intent intent = getIntent();
        final AlarmBaseData alarmBaseData = (AlarmBaseData) intent.getSerializableExtra("alarmBaseData");

        Button listBackButton = findViewById(R.id.listBack);

        final ListView list = findViewById(R.id.list);

        final MessageManager MM = new MessageManager(this);

        ArrayList<NamePhoneNum> nps = MM.getNamePhoneNums();

        ArrayList<String> npss = MM.getAddressesString();

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

                if (!nameNum.equals("주소록에 주소가 없습니다."))
                {
                    MM.setReceiver(nameNum);

                    alarmBaseData.setReciverName(MM.getReceiver().getName());
                    alarmBaseData.setPhoneNum(MM.getReceiver().getPhoneNum());
                }
            }
        });
    }


}
