package com.parkhelp.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.parkhelp.R;

public class SplashEkranActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_ekran);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);   //3 sn bekletip Mainactivitye geçer.
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashEkranActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}