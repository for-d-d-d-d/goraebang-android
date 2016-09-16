package com.fd.goraebang;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.fd.goraebang.account.ActivityAccountLogin_;
import com.fd.goraebang.custom.CustomActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_launch_screen)
public class ActivityLaunchScreen extends CustomActivity {
    private static final int DELAY = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void afterViews(){
        new CountDownTimer(DELAY, 100) {
            public void onTick(long millisUntilFinished) {
                //nothing
            }

            public void onFinish() {
                Intent intent = new Intent(ActivityLaunchScreen.this, ActivityAccountLogin_.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}
