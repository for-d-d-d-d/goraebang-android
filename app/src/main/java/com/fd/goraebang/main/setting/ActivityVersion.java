package com.fd.goraebang.main.setting;

import android.os.Bundle;

import com.fd.goraebang.R;
import com.fd.goraebang.custom.CustomActivityWithToolbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_version)
public class ActivityVersion extends CustomActivityWithToolbar{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @AfterViews
    void afterViews(){
        setToolbar("버전", 0, R.drawable.ic_arrow_back_white_24dp, 0, 0);

    }
}
