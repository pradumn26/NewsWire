package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.developinggeek.thebetterlawyernewsapp.R;

public class SplashScreenActivity extends AppCompatActivity {
    public static  int SPLASH_SCREEN_TIMEOUT=4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        },SPLASH_SCREEN_TIMEOUT);

    }
}
