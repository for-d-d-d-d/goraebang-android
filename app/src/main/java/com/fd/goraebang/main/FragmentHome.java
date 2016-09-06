package com.fd.goraebang.main;

import android.os.Bundle;
import android.view.View;

import com.fd.goraebang.R;
import com.fd.goraebang.custom.CustomFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;

@EFragment(R.layout.fragment_tab_home)
public class FragmentHome extends CustomFragment {
    public static FragmentHome newInstance() {
        FragmentHome f = new FragmentHome_();
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
