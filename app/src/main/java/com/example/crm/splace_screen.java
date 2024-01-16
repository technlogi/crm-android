package com.example.crm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class splace_screen extends AppCompatActivity {
    LinearLayout  splace2;
    RelativeLayout splace3,splace1;
    ImageView next_img;
    TextView splace_login;
    CountDownTimer timer1,timer2,timer3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_splace_screen);


        splace1 = findViewById(R.id.splace1);
        splace2 = findViewById(R.id.splace2);
//        splace3 = findViewById(R.id.splace3);
        next_img = findViewById(R.id.next_img);
//        splace_login = findViewById(R.id.splace_login);


        timer1 = new CountDownTimer(300, 100) {
            public void onTick(long millisUntilFinished) {


            }

            public void onFinish() {

                splace2.setVisibility(View.VISIBLE);
                splace1.setVisibility(View.GONE);
//                splace3.setVisibility(View.GONE);

//                timer2 = new CountDownTimer(3000, 1000) {
//                    public void onTick(long millisUntilFinished) {
//
//
//                    }
//
//                    public void onFinish() {
//                        splace3.setVisibility(View.VISIBLE);
//                        splace1.setVisibility(View.GONE);
//                        splace2.setVisibility(View.GONE);
//                        timer3 = new CountDownTimer(3000, 1000) {
//                            public void onTick(long millisUntilFinished) {
//
//
//                            }
//
//                            public void onFinish() {
//                                Intent i = new Intent(splace_screen.this, LoginScreenActivity.class);
//                                startActivity(i);
//
//                            }
//                        }.start();
//
//
//                    }
//                }.start();

            }
        }.start();


        next_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer2 != null){

                    timer2.cancel();

                }
                if (timer3 != null){

                    timer3.cancel();

                }
                if (getSharedPreferences("MySharedPref", Context.MODE_PRIVATE).getString("user_id","").equals("") || getSharedPreferences("MySharedPref", Context.MODE_PRIVATE).getString("user_id", "") == null) {
                    Intent i = new Intent(splace_screen.this, LoginScreenActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent  i = new Intent(splace_screen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}