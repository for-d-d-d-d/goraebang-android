package com.fd.goraebang.main.home;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fd.goraebang.R;
import com.fd.goraebang.custom.CustomFragment;
import com.fd.goraebang.model.Banner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_tab_home_banner)
public class FragmentHomeBanner extends CustomFragment {
    @ViewById
    ImageView iv;

    @ViewById
    TextView tv;

    private Banner banner = null;

    public static FragmentHomeBanner newInstance(Banner banner) {
        FragmentHomeBanner f = new FragmentHomeBanner_();
        Bundle b = new Bundle();
        b.putSerializable("banner", banner);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        banner = (Banner) getArguments().getSerializable("banner");
    }

    @AfterViews
    void init(){
        if(banner == null)
            return;

        Glide.with(getActivity()).load(banner.getImage()).into(iv);
        tv.setText(banner.getTitle());
    }
}