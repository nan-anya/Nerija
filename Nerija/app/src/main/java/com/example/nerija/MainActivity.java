package com.example.nerija;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private String[] permissions =
            {
            Manifest.permission.ACCESS_NETWORK_STATE, //인터넷 상태 확인 권한
            Manifest.permission.ACCESS_FINE_LOCATION, // 위치 정보 접근 권한
            Manifest.permission.READ_CONTACTS, // 주소록 접근 권한
            Manifest.permission.VIBRATE, // 진동 알림 권한
            Manifest.permission.SEND_SMS//문자 발신
    };

    private static final int MULTIPLE_PERMISSIONS = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button trainButton = findViewById(R.id.trainButton);
        trainButton.setOnClickListener(this);

        //현제 사용안함
        Button interBusButton = findViewById(R.id.intercityBusButton);
        interBusButton.setOnClickListener(this);
        interBusButton.setVisibility(View.GONE);

        Button cityBusButton = findViewById(R.id.cityBusButton);
        cityBusButton.setOnClickListener(this);
        cityBusButton.setVisibility(View.GONE);
        //



        if (Build.VERSION.SDK_INT >= 23) // 안드로이드 6.0 이상일 경우 퍼미션 체크
        {
            getPermissions();
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())
        {
            case R.id.trainButton ://기차버튼
                intent = new Intent(getApplicationContext(),TrainInputActivity.class);
                startActivity(intent);
                break;
            case R.id.intercityBusButton://시외버스 버튼
                intent = new Intent(getApplicationContext(),IntercityInputActivity.class);
                startActivity(intent);
                break;
            case R.id.cityBusButton://시내버스 버튼
                break;
        }
    }

    private void getPermissions()
    {
        ArrayList<String> permissionList = new ArrayList<>();

        boolean wait = true;

        for (String pm : permissions)
        {
            if (ContextCompat.checkSelfPermission(this, pm) != PackageManager.PERMISSION_GRANTED)
            {
                permissionList.add(pm);
            }
        }
        for (String i : permissionList)
        {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
        }

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        for(int g : grantResults)
        {
            if(g != PackageManager.PERMISSION_GRANTED)
            {
                showToast_PermissionDeny();
            }
        }
    }

    private void showToast_PermissionDeny()
    {
        Toast.makeText(this, "권한 요청에 동의 해주셔야 이용 가능합니다.", Toast.LENGTH_SHORT).show();
        finish();
    }
}

