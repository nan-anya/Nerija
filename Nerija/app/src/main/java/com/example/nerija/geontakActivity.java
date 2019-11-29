package com.example.nerija;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class geontakActivity extends AppCompatActivity implements View.OnClickListener
{
    private MessageManager MM;

    private LocationChecker LC;

    Button button;

    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tak_temp);
        button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(this);
        txtResult = (TextView)findViewById(R.id.txtResult);

        MM = new MessageManager(this);

        LC = new LocationChecker(getApplicationContext(), this);
    }

    public void onClick(View v)
    {
        Intent intent;
        if (v.getId() == R.id.button1)
        {
            MM.setReceiver(new namePhoneNum("남도훈", "01062221447"));

            MM.makeMessage("-장소 이름-");

            MM.transmitMessage("01062221447");
        }
    }
}
