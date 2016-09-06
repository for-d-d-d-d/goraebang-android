package com.fd.goraebang.main;

import android.os.Bundle;

import com.fd.goraebang.R;
import com.fd.goraebang.custom.CustomFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_tab_recommend)
public class FragmentRecommend extends CustomFragment {
    public static FragmentRecommend newInstance() {
        FragmentRecommend f = new FragmentRecommend_();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @AfterViews
    void afterViews() {

    }
}
