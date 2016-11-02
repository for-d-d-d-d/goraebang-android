package com.fd.goraebang.custom;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.fd.goraebang.util.AppController;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class CustomFragment extends Fragment {
    protected Tracker mTracker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initGA();
    }

    protected void initGA(){
        // [START shared_tracker]
        AppController application = (AppController) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName(getClass().getSimpleName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END shared_tracker]
    }

    protected void onClickFavorite(){

    }
}
