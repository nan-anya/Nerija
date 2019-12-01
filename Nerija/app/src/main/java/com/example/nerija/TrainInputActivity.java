package com.example.nerija;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class TrainInputActivity extends AppCompatActivity implements View.OnClickListener
{
    String date;
    HashMap<String,String> dataSet = new HashMap<>();

    UserInputData userInputData = new UserInputData();

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

        //임시 버튼임
        Button namdoB = findViewById(R.id.namdoButton);
        namdoB.setOnClickListener(this);
        Button buttonT = findViewById(R.id.geontakButton);
        buttonT.setOnClickListener(this);
        //

        try {
            setDataList(dataSet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        startSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//출발지 선택
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner startingSpinner = findViewById(R.id.departSpinner);
                String departPlace = String.valueOf(startingSpinner.getItemAtPosition(position));
                userInputData.setDepartPlaceID(dataSet.get(departPlace));
                userInputData.setDepartPlaceName(departPlace);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        endSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//도착지 선택
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner endSpinner = findViewById(R.id.arrivalSpinner);
                String arrivalPlace = String.valueOf(endSpinner.getItemAtPosition(position));
                userInputData.setArrivalPlaceID(dataSet.get(arrivalPlace));
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
                        userInputData.setDate(date);//데이터 삽입
                    }
                };
                String year = String.valueOf(cal.get(Calendar.YEAR));
                String month = String.valueOf(cal.get(Calendar.MONTH));
                String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, listener,Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
                datePickerDialog.show();
                break;
            case R.id.trainOKButton:
                Date selectedDate = userInputData.getDate();//선택한 시간
                Date currentDate =  new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                if(date == null)
                {
                    Toast.makeText(this,"날짜를 선택해주세요.",Toast.LENGTH_LONG).show();
                }
                else
                {
                    String selectedDateString = format.format(selectedDate);
                    String currentDateString = format.format(currentDate);
                    int result = selectedDateString.compareTo(currentDateString);
                    if(result<0)
                    {
                        Toast.makeText(this,"선택한 날짜가 과거입니다.",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                        if(networkInfo !=null)
                        {

                            Intent listIntent = new Intent(getApplicationContext(),ListActivity.class);
                            listIntent.putExtra("userInputData",userInputData);
                            listIntent.putExtra("searchMode",1);
                            startActivity(listIntent);
                        }
                        else
                        {
                            Toast.makeText(this,"네트워크 연결 상태를 확인해주세요",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case R.id.geontakButton:
                Intent temp1 = new Intent(getApplicationContext(),geontakActivity.class);
                startActivity(temp1);
                break;
            case R.id.namdoButton:
                Intent temp2 = new Intent(getApplicationContext(),namdoActivity.class);
                Alarm alarm = new Alarm();
                alarm.getDate(new Date());

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentTitle("알림 제목");
                builder.setContentText("알람 세부 텍스트");

                builder.setColor(Color.RED);
                // 사용자가 탭을 클릭하면 자동 제거
                builder.setAutoCancel(true);

                // 알림 표시
                NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
                }

                // id값은
                // 정의해야하는 각 알림의 고유한 int값
                notificationManager.notify(1, builder.build());
                alarm.StartAlarm(getApplicationContext(),temp2);
                Toast.makeText(getApplicationContext(),"press",Toast.LENGTH_SHORT).show();



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
