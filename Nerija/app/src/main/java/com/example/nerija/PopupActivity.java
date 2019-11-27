package com.example.nerija;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class PopupActivity extends Activity implements View.OnClickListener
{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);
        Button a = findViewById(R.id.a);//ㄱ
        Button b = findViewById(R.id.b);//ㄴ
        Button c = findViewById(R.id.c);//ㄷ
        Button d = findViewById(R.id.d);//ㄹ
        Button e = findViewById(R.id.e);//ㅁ
        Button f = findViewById(R.id.f);//ㅂ
        Button g = findViewById(R.id.g);//ㅅ
        Button h = findViewById(R.id.h);//ㅇ
        Button i = findViewById(R.id.i);//ㅈ
        Button j = findViewById(R.id.j);//ㅊ
        Button k = findViewById(R.id.k);//ㅋ
        Button l = findViewById(R.id.l);//ㅌ
        Button m = findViewById(R.id.m);//ㅍ
        Button n = findViewById(R.id.n);//ㅎ
        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        d.setOnClickListener(this);
        e.setOnClickListener(this);
        f.setOnClickListener(this);
        g.setOnClickListener(this);
        h.setOnClickListener(this);
        i.setOnClickListener(this);
        j.setOnClickListener(this);
        k.setOnClickListener(this);
        l.setOnClickListener(this);
        m.setOnClickListener(this);
        n.setOnClickListener(this);
    }


    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.a:
                intent.putExtra("result","a");
                break;
            case R.id.b:
                intent.putExtra("result","b");
                break;
            case R.id.c:
                intent.putExtra("result","c");
                break;
            case R.id.d:
                intent.putExtra("result","d");
                break;
            case R.id.e:
                intent.putExtra("result","e");
                break;
            case R.id.f:
                intent.putExtra("result","f");
                break;
            case R.id.g:
                intent.putExtra("result","g");
                break;
            case R.id.h:
                intent.putExtra("result","h");
                break;
            case R.id.i:
                intent.putExtra("result","i");
                break;
            case R.id.j:
                intent.putExtra("result","j");
                break;
            case R.id.k:
                intent.putExtra("result","k");
                break;
            case R.id.l:
                intent.putExtra("result","l");
                break;
            case R.id.m:
                intent.putExtra("result","m");
                break;
            case R.id.n:
                intent.putExtra("result","n");
                break;
        }
        setResult(RESULT_OK, intent);
        finish();
    }

}
