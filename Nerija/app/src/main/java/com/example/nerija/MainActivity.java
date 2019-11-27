package com.example.nerija;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button trainButton = findViewById(R.id.trainButton);
        Button interBusButton = findViewById(R.id.intercityBusButton);
        Button cityBusButton = findViewById(R.id.cityBusButton);
        trainButton.setOnClickListener(this);
        interBusButton.setOnClickListener(this);
        cityBusButton.setOnClickListener(this);
        cityBusButton.setVisibility(View.GONE);
        int SSibal =  18;
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
}

