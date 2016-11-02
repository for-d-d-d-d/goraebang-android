package com.fd.goraebang.main.mypage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fd.goraebang.R;
import com.fd.goraebang.custom.CustomFragment;
import com.fd.goraebang.util.AppController;
import com.fd.goraebang.util.adapter.FragmentTabPagerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

@EFragment(R.layout.fragment_tab_mypage)
public class FragmentMyPage extends CustomFragment implements AppBarLayout.OnOffsetChangedListener {
    @ViewById
    ViewPager viewPager;
    @ViewById
    Toolbar toolbar;
    @ViewById
    TabLayout tabLayout;
    @ViewById
    CircleImageView ivProfile;
    @ViewById
    ImageView ivProfileBackground;
    @ViewById
    AppBarLayout appbar;
    @ViewById
    LinearLayout llTitleContainer;
    @ViewById
    TextView tvName, tvMyListCount;
    @ViewById
    ImageView btnRight;

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.8f;
    private static final float PERCENTAGE_TO_HIDE_PROFILE_IMAGE     = 0.5f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean isProfileVisible           = true;
    private boolean isTheTitleVisible          = false;
    private boolean isTheTitleContainerVisible = true;
    private int mMaxScrollSize;
    private float percentage = 0;

    private SharedPreferences pref;

    private FragmentTabPagerAdapter adapter = null;

    public static FragmentMyPage newInstance() {
        FragmentMyPage f = new FragmentMyPage_();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(adapter == null){
            adapter = new FragmentTabPagerAdapter(getChildFragmentManager());
            adapter.addFragment(FragmentMyPageList.newInstance("ANALYSIS"), "취향분석");
            adapter.addFragment(FragmentMyPageList.newInstance("FAVORITE"), "마이리스트");
            adapter.addFragment(FragmentMyPageList.newInstance("BLACKLIST"), "블랙리스트");
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @AfterViews
    void afterViews() {
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        appbar.addOnOffsetChangedListener(this);
        mMaxScrollSize = appbar.getTotalScrollRange();

        updateView();
    }

    private void updateView(){
        if(AppController.USER != null) {
            if (AppController.USER.getThumbnail() != null)
                Glide.with(getActivity()).load(AppController.USER.getThumbnail()).into(ivProfile);

            if (AppController.USER.getImage() != null)
                Glide.with(getActivity()).load(AppController.USER.getImage())
                        .bitmapTransform(new BlurTransformation(getActivity(), 25))
                        .into(ivProfileBackground);

            tvName.setText(AppController.USER.getName());
            tvMyListCount.setText(String.format("저장된 곡의 개수 %d", AppController.USER.getMylistCount()));
        }
    }

    @Click(R.id.btnSetting)
    void onClickProfile(){
        Intent intent = new Intent(getActivity(), ActivityMyProfile_.class);
        startActivity(intent);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();
        percentage = (float) Math.abs(verticalOffset) / (float) mMaxScrollSize;

        handleAlphaOnTitle(percentage);
        handleProfileImage(percentage);
    }

    private void handleProfileImage(float percentage){
        if (percentage >= PERCENTAGE_TO_HIDE_PROFILE_IMAGE && isProfileVisible) {
            isProfileVisible = false;
            ivProfile.animate().scaleY(0).scaleX(0).setDuration(200).start();
        }
        if (percentage <= PERCENTAGE_TO_HIDE_PROFILE_IMAGE && !isProfileVisible) {
            isProfileVisible = true;
            ivProfile.animate().scaleY(1).scaleX(1).start();
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS && isTheTitleContainerVisible) {
            startAlphaAnimation(llTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
            isTheTitleContainerVisible = false;
        }
        if (percentage <= PERCENTAGE_TO_HIDE_TITLE_DETAILS && !isTheTitleContainerVisible) {
            startAlphaAnimation(llTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
            isTheTitleContainerVisible = true;
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
