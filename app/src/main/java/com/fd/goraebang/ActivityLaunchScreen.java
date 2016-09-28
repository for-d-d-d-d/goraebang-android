package com.fd.goraebang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.fd.goraebang.account.ActivityAccountLogin_;
import com.fd.goraebang.consts.CONST;
import com.fd.goraebang.custom.CustomActivity;
import com.fd.goraebang.util.AppController;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_launch_screen)
public class ActivityLaunchScreen extends CustomActivity {
    private static final int DELAY = 1000;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Class<?> target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void afterViews() {
        pref = getSharedPreferences(CONST.PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();

        if (AppController.USER_ID != null && AppController.USER_MY_LIST_ID != null && AppController.USER_TOKEN != null) {
            Log.d("GORAEBANG", "USER_ID : " + AppController.USER_ID);
            Log.d("GORAEBANG", "USER_MY_LIST_ID : " + AppController.USER_MY_LIST_ID);
            Log.d("GORAEBANG", "USER_TOKEN : " + AppController.USER_TOKEN);
            target = ActivityMain_.class;
            runNextActivity();
        } else {
            // 저장된 ID 혹은 Token이 없을 경우 다시 로그인을 시도.
            String email = pref.getString("user_email", null);
            String password = pref.getString("user_password", null);

            if (email != null && password != null) {
                autoLogin(email, password);
            } else {
                // 하나라도 null이면 login 화면으로 이동.
                target = ActivityAccountLogin_.class;
                runNextActivity();
            }
        }
    }

    private void autoLogin(String email, String password) {
        target = ActivityAccountLogin_.class;
        runNextActivity();
    }

    private void runNextActivity() {
        new CountDownTimer(DELAY, 100) {
            public void onTick(long millisUntilFinished) {
                //nothing
            }

            public void onFinish() {
                Intent intent = new Intent(ActivityLaunchScreen.this, target);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}
