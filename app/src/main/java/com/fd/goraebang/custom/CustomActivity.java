package com.fd.goraebang.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fd.goraebang.R;
import com.fd.goraebang.util.AppController;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class CustomActivity extends AppCompatActivity{
    protected boolean isAnimation = true;
    protected Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(isAnimation)
            this.overridePendingTransition(R.anim.animation_slide_cover_left_in, R.anim.animation_zoom_out);

        super.onCreate(savedInstanceState);
        initGA();
    }

    @Override
    public void finish() {
        super.finish();

        if(isAnimation)
            this.overridePendingTransition(R.anim.animation_zoom_in, R.anim.animation_slide_cover_right_out);
    }

    protected void initGA(){
        // [START shared_tracker]
        AppController application = (AppController) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName(getClass().getSimpleName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END shared_tracker]
    }
}
