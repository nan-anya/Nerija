package com.example.nerija;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class IntercityInputActivity extends AppCompatActivity implements View.OnClickListener
{
    String date;
    TransportAlarmSystem TAS = new TransportAlarmSystem();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_intercity);
        Button backButton = findViewById(R.id.intercityBackButton);
        Button OKButton = findViewById(R.id.intercityOKButton);
        Button calender = findViewById(R.id.calenderPopUp);
        backButton.setOnClickListener(this);
        OKButton.setOnClickListener(this);
        calender.setOnClickListener(this);

    }


    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.intercityBackButton:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.calenderPopUp:
                Calendar cal = new GregorianCalendar();
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date = (i + "-" + (i1+1) + "-" + i2);
                        InputManager inputManager = TAS.getInputManager();
                        inputManager.setDate(date);//데이터 삽입
                    }
                };
                String year = String.valueOf(cal.get(Calendar.YEAR));
                String month = String.valueOf(cal.get(Calendar.MONTH));
                String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, listener,Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
                datePickerDialog.show();
                break;
            case R.id.intercityOKButton:
                break;
        }
    }

}
