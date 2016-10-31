package com.fd.goraebang.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fd.goraebang.R;
import com.fd.goraebang.account.ActivityAccountLogin_;
import com.fd.goraebang.consts.CONST;
import com.fd.goraebang.custom.CustomActivityWithToolbar;
import com.fd.goraebang.main.home.FragmentHome;
import com.fd.goraebang.main.mypage.FragmentMyPage;
import com.fd.goraebang.main.setting.FragmentSettings;
import com.fd.goraebang.model.User;
import com.fd.goraebang.util.AppController;
import com.fd.goraebang.util.CallUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;
import retrofit2.Response;

@EActivity(R.layout.activity_main)
public class ActivityMain extends CustomActivityWithToolbar {
    @ViewById
    TabLayout tabLayout;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private Fragment fragmentHome, fragmentSearch, fragmentRecommend, fragmentMyPage, fragmentSettings;
    private int tabPosition = 0;
    private boolean exit;

    private TextView selectedTab, unSelectedTab;
    private TextView[] tabs = new TextView[5];
    private final String[] TAB_NAME = {"HOME", "SEARCH", "RECOMMEND", "MY PAGE", "SETTING"};
    private final int[] TAB_ICONS = {
            R.drawable.ic_tab_home,
            R.drawable.ic_tab_search,
            R.drawable.ic_tab_recommand,
            R.drawable.ic_tab_mylist,
            R.drawable.ic_tab_settings
    };

    private FragmentManager fm = null;
    private FragmentTransaction ft = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getSharedPreferences(CONST.PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
    }

    @AfterViews
    void afterViews(){
        setToolbar("고래방", 0, 0, 0, 0);

        tabLayout.setOnTabSelectedListener(new onTabSelectListener());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        if(fm == null)
            fm = getSupportFragmentManager();

        fragmentHome = FragmentHome.newInstance();
        fragmentSearch = FragmentSearch.newInstance();
        fragmentRecommend = FragmentRecommend.newInstance();
        fragmentMyPage = FragmentMyPage.newInstance();
        fragmentSettings = FragmentSettings.newInstance();

        for(int i = 0; i < 5; i++){
            tabs[i] = (TextView) LayoutInflater.from(this).inflate(R.layout.layout_custom_tab, null);
            tabs[i].setText(TAB_NAME[i]);

            Drawable d = getResources().getDrawable(TAB_ICONS[i]);
            d.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);

            tabs[i].setCompoundDrawablesWithIntrinsicBounds(null, d, null, null);
            tabLayout.addTab(tabLayout.newTab().setCustomView(tabs[i]), i);
        }

        tabLayout.getTabAt(3).getCustomView().setSelected(true);

        loadUser();
    }

    private void loadUser() {
        Call<User> call = AppController.getAccountService().me(AppController.USER_TOKEN);
        call.enqueue(new CallUtils<User>(call, this, getResources().getString(R.string.msgErrorCommon)) {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AppController.USER = response.body();
                }
                onComplete();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                onComplete();
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private class onTabSelectListener implements TabLayout.OnTabSelectedListener{
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            tabPosition = tab.getPosition();
            ft = fm.beginTransaction();

            Drawable d = getResources().getDrawable(TAB_ICONS[tabPosition]);
            d.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.MULTIPLY);

            selectedTab = (TextView)tab.getCustomView();
            selectedTab.setTextColor(getResources().getColor(R.color.red));
            selectedTab.setCompoundDrawablesWithIntrinsicBounds(null, d, null, null);

            switch(tabPosition){
                case 0:
                    setToolbar("고래방", 0, 0, 0, 0);
                    ft.replace(R.id.frLayout, fragmentHome);
                    break;
                case 1:
                    setToolbar("검색", 0, 0, 0, 0);
                    ft.replace(R.id.frLayout, fragmentSearch);
                    break;
                case 2:
                    setToolbar("추천", 0, 0, 0, 0);
                    ft.replace(R.id.frLayout, fragmentRecommend);
                    break;
                case 3:
                    setToolbar("마이페이지", 0, 0, 0, 0);
                    ft.replace(R.id.frLayout, fragmentMyPage);
                    break;
                case 4:
                    setToolbar("설정", 0, 0, 0, 0);
                    ft.replace(R.id.frLayout, fragmentSettings);
                    break;
            }

            ft.addToBackStack(null);
            ft.commit();
        }
        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            Drawable d = getResources().getDrawable(TAB_ICONS[tabPosition]);
            d.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);

            unSelectedTab = (TextView)tab.getCustomView();
            unSelectedTab.setTextColor(getResources().getColor(R.color.gray));
            selectedTab.setCompoundDrawablesWithIntrinsicBounds(null, d, null, null);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) { }
    }

    @Override
    public void onBackPressed() {
        if(exit) {
            finish();
        } else {
            Toast.makeText(this, getResources().getString(R.string.is_finish),
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2 * 1000);
        }
    }

    public void logout(){
        AppController.USER = null;
        AppController.USER_ID = null;
        AppController.USER_MY_LIST_ID = null;
        AppController.USER_TOKEN = null;
        editor.putString("user_id", null);
        editor.putString("user_my_list_id", null);
        editor.putString("user_token", null);
        editor.putString("email", null);
        editor.putString("password", null);
        editor.commit();
        startActivity(new Intent(ActivityMain.this, ActivityAccountLogin_.class));
        finish();
    }

    public void onClick(View v) {
        Intent intent = null;

        switch(v.getId()){
            default :
                break;
        }

        if(intent != null) {
            startActivity(intent);
        }
    }
}
