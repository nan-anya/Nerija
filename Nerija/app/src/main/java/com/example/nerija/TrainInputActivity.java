package com.example.nerija;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class TrainInputActivity extends AppCompatActivity implements View.OnClickListener
{
    private String userDepartPlaceID;
    private String userArrivalPlaceID;
    private String userArrivalPlaceName;
    private Date userDate;

    private HashMap<String,String> dataSet = new HashMap<>();

    private UserInputData userInputData = new UserInputData();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_train);

        Button backButton = findViewById(R.id.trainBackButton);
        backButton.setOnClickListener(this);

        Button calenderPopUpButton = findViewById(R.id.calenderPopUp);
        calenderPopUpButton.setOnClickListener(this);

        Button OKButton  = findViewById(R.id.trainOKButton);
        OKButton.setOnClickListener(this);

        Spinner startSpinner = findViewById(R.id.departSpinner);
        Spinner endSpinner = findViewById(R.id.arrivalSpinner);

        try
        {
            setDataList(dataSet);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        startSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {//출발지 선택
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Spinner startingSpinner = findViewById(R.id.departSpinner);
                String departPlace = String.valueOf(startingSpinner.getItemAtPosition(position));
                userDepartPlaceID = dataSet.get(departPlace);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
        endSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {//도착지 선택
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Spinner endSpinner = findViewById(R.id.arrivalSpinner);
                String arrivalPlace = String.valueOf(endSpinner.getItemAtPosition(position));

                userArrivalPlaceID = dataSet.get(arrivalPlace);
                userArrivalPlaceName = arrivalPlace;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.trainBackButton:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case R.id.calenderPopUp:
                calenderSelect();
                break;

            case R.id.trainOKButton:
                trainOK();
                break;
        }
    }

    private void calenderSelect()
    {
        Calendar cal = new GregorianCalendar();
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
            {
                String temp = (i + "-" + (i1+1) + "-" + i2);

                try
                {
                    userDate = new SimpleDateFormat("yyyy-MM-dd").parse(temp);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
        };
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = String.valueOf(cal.get(Calendar.MONTH));
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, listener,Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
        datePickerDialog.show();
    }

    private void trainOK()
    {
        Date selectedDate = userDate;//선택한 시간
        Date currentDate =  new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        if(this.userDate == null)
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
                    userInputData.setArrivalPlaceID(userArrivalPlaceID);
                    userInputData.setArrivalPlaceName(userArrivalPlaceName);
                    userInputData.setDepartPlaceID(userDepartPlaceID);
                    userInputData.setDate(userDate);

                    Intent listIntent = new Intent(getApplicationContext(),ListActivity.class);
                    listIntent.putExtra("userInputData",userInputData);
                    //listIntent.putExtra("searchMode",1);
                    startActivity(listIntent);
                }
                else
                {
                    Toast.makeText(this,"네트워크 연결 상태를 확인해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void setDataList(HashMap<String,String> dataSet) throws IOException
    {
        ArrayList<String> dataList = new ArrayList<>();
        InputStream in = getResources().openRawResource(R.raw.train_name);
        try
        {
            InputStreamReader data = new InputStreamReader(in, "utf-8");
            BufferedReader buffer = new BufferedReader(data);
            String line;
            while ((line = buffer.readLine()) != null)
            {
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
