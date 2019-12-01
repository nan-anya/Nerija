package com.example.nerija;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Date;

public class Alarm extends Thread implements Serializable {

    public Date alarm_date; // 알람을 울릴 시간 정보 저장;


    public void StartAlarm(final Context app, final Intent intent, final AlarmBaseData alarmBaseData){ // TextView는 화면에 띄워서 결과 확인하기 위한 선언; 나중에 제거하기
        final Toast toast = Toast.makeText(app.getApplicationContext(), "알람 쨘",Toast.LENGTH_SHORT);
        final Vibrator vib = (Vibrator) app.getSystemService(Context.VIBRATOR_SERVICE);

        final Handler handler = new Handler(){

            public void handleMessage(Message msg){
                getDiffer(app,toast,vib,intent);  // 시간 차이 구하는 메소드 실행
            }
        };

        Runnable task = new Runnable() {

            @Override
            public void run() {

                while(!isInterrupted()){
                    try{
                        Thread.sleep(1000); // 1초 동안 쓰레드 정지; 나중에 분으로 바꿔도 됨
                    }
                    catch(InterruptedException e){

                    } //

                    handler.sendEmptyMessage(1);    // 1초 후 handler 호출 즉, 시간 변경
                }
            }
        };

        Thread thread = new Thread(task);   // ShowTimerMethod가 실행 되면 바로 스레드 실행
        thread.start();

    }

    // 시간차이 구하는 메소드
    public void getDiffer(final Context app, Toast toast, Vibrator vib, Intent intent){

        long diff = new Date().getTime() - alarm_date.getTime();    // 현재시간 - 받아온 시간 => 실제로는 받아온 시간 - 현재시간 할 것
        long sec = diff/1000;   // 나누기 1000 : 초; 나누기 60000 : 분; 나누기 3600000 : 시 나중에 바꾸기;
        long[] pattern = {1000,1000};

        if(sec == 5){
            toast.show();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            app.startActivity(intent);
            vib.vibrate(pattern,0);
        }

    }

    public void getDate(final Date date) {  // 알람 시간 받아오는 메소드
        if(alarm_date == null) {
            alarm_date = date;
        }
    }
}

