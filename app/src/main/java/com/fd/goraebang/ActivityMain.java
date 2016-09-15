package com.fd.goraebang;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.fd.goraebang.custom.CustomActivity;
import com.fd.goraebang.main.FragmentHome;
import com.fd.goraebang.main.FragmentMyList;
import com.fd.goraebang.main.FragmentRecommend;
import com.fd.goraebang.main.FragmentSearch;
import com.fd.goraebang.main.FragmentSettings;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class ActivityMain extends CustomActivity {
    @ViewById
    TabLayout tabLayout;

    private Fragment fragmentHome, fragmentSearch, fragmentRecommend, fragmentMyList, fragmentSettings;
    private int tabPosition = 0;
    private boolean exit;

    private TextView selectedTab, unSelectedTab;
    private TextView[] tabs = new TextView[5];
    private final String[] TAB_NAME = {"HOME", "SEARCH", "RECOMMEND", "MY LIST", "SETTING"};
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
    }

    @AfterViews
    void afterViews(){
        tabLayout.setOnTabSelectedListener(new onTabSelectListener());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        if(fm == null)
            fm = getSupportFragmentManager();

        fragmentHome = FragmentHome.newInstance();
        fragmentSearch = FragmentSearch.newInstance();
        fragmentRecommend = FragmentRecommend.newInstance();
        fragmentMyList = FragmentMyList.newInstance();
        fragmentSettings = FragmentSettings.newInstance();

        for(int i = 0; i < 5; i++){
            tabs[i] = (TextView) LayoutInflater.from(this).inflate(R.layout.layout_custom_tab, null);
            tabs[i].setText(TAB_NAME[i]);

            Drawable d = getResources().getDrawable(TAB_ICONS[i]);
            d.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);

            tabs[i].setCompoundDrawablesWithIntrinsicBounds(null, d, null, null);
            tabLayout.addTab(tabLayout.newTab().setCustomView(tabs[i]), i);
        }
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
                    ft.replace(R.id.frLayout, fragmentHome);
                    break;
                case 1:
                    ft.replace(R.id.frLayout, fragmentSearch);
                    break;
                case 2:
                    ft.replace(R.id.frLayout, fragmentRecommend);
                    break;
                case 3:
                    ft.replace(R.id.frLayout, fragmentMyList);
                    break;
                case 4:
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
}
