package com.fd.goraebang.search;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.fd.goraebang.R;
import com.fd.goraebang.custom.CustomActivityWithToolbar;
import com.fd.goraebang.util.Utils;
import com.fd.goraebang.util.adapter.FragmentTabPagerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_search)
public class ActivitySearch extends CustomActivityWithToolbar {
    @ViewById
    EditText etSearch;
    @ViewById
    ViewPager viewPager;
    @ViewById
    TabLayout tabLayout;

    private FragmentTabPagerAdapter adapter;
    private String keyword;

    @AfterViews
    void init(){
        setToolbar("상세보기", 0, R.drawable.ic_arrow_back_white_24dp, 0, 0);

        if(adapter == null){
            adapter = new FragmentTabPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(FragmentSearchList.newInstance("FILTER", keyword), "조건검색");
            adapter.addFragment(FragmentSearchList.newInstance("TITLE", keyword), "제목별");
            adapter.addFragment(FragmentSearchList.newInstance("ARTIST", keyword), "아티스트별");
            adapter.addFragment(FragmentSearchList.newInstance("LYRICS", keyword), "가사별");
        }
    }


    @AfterViews
    void afterViews() {
        etSearch.setOnEditorActionListener(new OnEditorActionListener());

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

    }

    void onClickSearch(){
        keyword = etSearch.getText().toString();
        ((FragmentSearchList)adapter.getItem(0)).searchKeyword(keyword);
        ((FragmentSearchList)adapter.getItem(1)).searchKeyword(keyword);
        ((FragmentSearchList)adapter.getItem(2)).searchKeyword(keyword);
        ((FragmentSearchList)adapter.getItem(3)).searchKeyword(keyword);
        Utils.hideSoftKeyboard(this);

    }

    private class OnEditorActionListener implements TextView.OnEditorActionListener{
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onClickSearch();
            }
            return false;
        }
    }

    public void onClick(View v) {
        Intent intent = null;

        switch(v.getId()){
            case R.id.btnLeft:
                finish();
                break;
            case R.id.btnSearch:
                onClickSearch();
                break;
            default :
                break;
        }

        if(intent != null) {
            startActivity(intent);
        }
    }
}
