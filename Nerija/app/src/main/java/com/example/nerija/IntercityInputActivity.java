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

public class IntercityInputActivity extends AppCompatActivity implements View.OnClickListener
{
    String date;
    TransportAlarmSystem TAS = new TransportAlarmSystem();
    HashMap<String,String> dataSet = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_intercity);
        Button backButton = findViewById(R.id.intercityBackButton);
        Button OKButton = findViewById(R.id.intercityOKButton);
        Button startDo = findViewById(R.id.startDoPopUp);
        Button endDo = findViewById(R.id.endDoPopUp);
        Button calender = findViewById(R.id.calenderPopUp);
        backButton.setOnClickListener(this);
        OKButton.setOnClickListener(this);
        startDo.setOnClickListener(this);
        endDo.setOnClickListener(this);
        calender.setOnClickListener(this);

    }


    public void onClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            case R.id.intercityBackButton:
                intent = new Intent(getApplicationContext(),MainActivity.class);
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
            case R.id.startDoPopUp:
                Intent startPopIntent = new Intent(this,PopupActivity.class);
                startPopIntent.putExtra("type",1);
                startActivityForResult(startPopIntent,1);
                break;
            case R.id.endDoPopUp:
                Intent endPopIntent = new Intent(this,PopupActivity.class);
                endPopIntent.putExtra("type",2);
                startActivityForResult(endPopIntent,2);
                break;
            case R.id.intercityOKButton:
                intent = new Intent(getApplicationContext(),ListActivity.class);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("실행","잘됨");
        if (requestCode == 1 || requestCode==2) {
            if (resultCode == RESULT_OK) {
                //데이터 받기
                String result = data.getStringExtra("result");
                int fileName;
                if(!result.equals("back"))
                {
                   fileName = fileNameSearch(result);
                    try {
                        setDataList(dataSet,fileName,requestCode);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setDataList(HashMap<String,String> dataSet,int fileName,int type) throws IOException {
        ArrayList<String> dataList = new ArrayList<>();
        if(fileName==0)
        {
            dataList.add("초성의 데이터가 없습니다.");
            if(type==1)
            {
                Spinner startingSpinner = findViewById(R.id.startTerminalSelect);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,dataList);
                startingSpinner.setAdapter(arrayAdapter);
            }
            else
            {
                Spinner endSpinner = findViewById(R.id.endTerminalSelect);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,dataList);
                endSpinner.setAdapter(arrayAdapter);
            }
        }
        else {
            InputStream in = getResources().openRawResource(fileName);
            try {
                InputStreamReader data = new InputStreamReader(in, "utf-8");
                BufferedReader buffer = new BufferedReader(data);
                String line;
                while ((line = buffer.readLine()) != null) {
                    String[] a = line.split("\t");
                    dataSet.put(a[0], a[1]);
                    dataList.add(a[0]);
                }
                if (type == 1) {
                    Spinner startingSpinner = findViewById(R.id.startTerminalSelect);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, dataList);
                    startingSpinner.setAdapter(arrayAdapter);
                } else {
                    Spinner endSpinner = findViewById(R.id.endTerminalSelect);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, dataList);
                    endSpinner.setAdapter(arrayAdapter);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public int fileNameSearch(String result)
    {
        int fileName = 0;
        switch (result)
        {
            case "a":
                fileName = R.raw.a;
                break;
            case "b":
                fileName = R.raw.b;
                break;
            case "c":
                fileName = R.raw.c;
                break;
            case "d":
                fileName = 0;
                break;
            case "e":
                fileName = R.raw.e;
                break;
            case "f":
                fileName = R.raw.f;
                break;
            case "g":
                fileName = R.raw.g;
                break;
            case "h":
                fileName = R.raw.h;
                break;
            case "i":
                fileName = R.raw.i;
                break;
            case "j":
                fileName = R.raw.j;
                break;
            case "k":
                fileName = 0;
                break;
            case "l":
                fileName = R.raw.l;
                break;
            case "m":
                fileName = R.raw.m;
                break;
            case "n":
                fileName = R.raw.n;
        }
        return fileName;
    }

}
