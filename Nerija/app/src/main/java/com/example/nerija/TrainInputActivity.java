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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class TrainInputActivity extends AppCompatActivity implements View.OnClickListener
{
    String date;
    HashMap<String,String> dataSet = new HashMap<>();
    TransportAlarmSystem TAS = new TransportAlarmSystem();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_train);

        Button backButton = findViewById(R.id.trainBackButton);
        Button calenderPopUpButton = findViewById(R.id.calenderPopUp);
        Button OKButton  = findViewById(R.id.trainOKButton);
        Spinner startSpinner = findViewById(R.id.departSpinner);
        Spinner endSpinner = findViewById(R.id.arrivalSpinner);
        backButton.setOnClickListener(this);
        calenderPopUpButton.setOnClickListener(this);
        OKButton.setOnClickListener(this);
        try {
            setDataList(dataSet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        startSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//출발지 선택
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputManager inputManager = TAS.getInputManager();
                Spinner startingSpinner = findViewById(R.id.departSpinner);
                String departPlace = String.valueOf(startingSpinner.getItemAtPosition(position));
                inputManager.setDepartPlace(dataSet.get(departPlace));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        endSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//도착지 선택
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputManager inputManager = TAS.getInputManager();
                Spinner endSpinner = findViewById(R.id.arrivalSpinner);
                String arrivalPlace = String.valueOf(endSpinner.getItemAtPosition(position));
                inputManager.setArrivalPlace(dataSet.get(arrivalPlace));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.trainBackButton:
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
            case R.id.trainOKButton:
                InputManager temp = TAS.getInputManager();
                Intent listIntent = new Intent(getApplicationContext(),ListActivity.class);
                listIntent.putExtra("IM",temp);
                listIntent.putExtra("searchMode",1);
                Log.d("으악",temp.getArrivalPlace()+" "+temp.getDepartPlace() + temp.getDate());
                startActivity(listIntent);
                break;
        }
    }


    public void setDataList(HashMap<String,String> dataSet) throws IOException {
        ArrayList<String> dataList = new ArrayList<>();
        InputStream in = getResources().openRawResource(R.raw.train_name);
        try
        {
            InputStreamReader data = new InputStreamReader(in, "utf-8");
            BufferedReader buffer = new BufferedReader(data);
            String line;
            while ((line = buffer.readLine()) != null) {
                String[] a = line.split("\t");
                dataSet.put(a[0],a[1]);
                dataList.add(a[0]);
            }
            Spinner startingSpinner = findViewById(R.id.departSpinner);
            Spinner endSpinner = findViewById(R.id.arrivalSpinner);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,dataList);
            startingSpinner.setAdapter(arrayAdapter);
            endSpinner.setAdapter(arrayAdapter);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

    }
}
