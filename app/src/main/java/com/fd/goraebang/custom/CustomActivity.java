package com.fd.goraebang.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.fd.goraebang.R;

public class CustomActivity extends AppCompatActivity{
    protected boolean isAnimation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(isAnimation)
            this.overridePendingTransition(R.anim.animation_slide_cover_left_in, R.anim.animation_zoom_out);

        super.onCreate(savedInstanceState);
    }
    @Override
    public void finish() {
        super.finish();

        if(isAnimation)
            this.overridePendingTransition(R.anim.animation_zoom_in, R.anim.animation_slide_cover_right_out);
    }

}
