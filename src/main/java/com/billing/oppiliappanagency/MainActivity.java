package com.billing.oppiliappanagency;

/*This project is for calculating estimation for Construction bars for the use of Construction bar agencies..
* Permission given in the manifest is WRITE_EXTERNAL_STORAGE only for devices below android 9.Devices above android 9 doesn't take any permissions due to
* android storage update above android 10(Scoped storage).*/


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
Intent myintent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this line is to disable the night mode for app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //this is to call next activity
        myintent=new Intent(this,HomeActivity.class);
        Splashscreen(1000);

    }

    private void Splashscreen(int i) {//this runs the thread for 1000ms and calls the next activity..
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(myintent);
                finish();
            }
        }).start();
    }
}