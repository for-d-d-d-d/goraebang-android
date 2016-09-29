package com.fd.goraebang.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.fd.goraebang.R;
import com.fd.goraebang.custom.CustomFragment;
import com.fd.goraebang.util.Utils;
import com.fd.goraebang.util.adapter.FragmentTabPagerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_tab_search)
public class FragmentSearch extends CustomFragment {
    @ViewById
    EditText etSearch;
    @ViewById
    ViewPager viewPager;
    @ViewById
    TabLayout tabLayout;

    private FragmentTabPagerAdapter adapter;
    private String keyword;

    public static FragmentSearch newInstance() {
        FragmentSearch f = new FragmentSearch_();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(adapter == null){
            adapter = new FragmentTabPagerAdapter(getActivity().getSupportFragmentManager());
            adapter.addFragment(FragmentSearchList.newInstance("FILTER", keyword), "조건검색");
            adapter.addFragment(FragmentSearchList.newInstance("TITLE", keyword), "제목별");
            adapter.addFragment(FragmentSearchList.newInstance("ARTIST", keyword), "아티스트별");
            adapter.addFragment(FragmentSearchList.newInstance("WORDS", keyword), "가사별");
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @AfterViews
    void afterViews() {
        etSearch.setOnEditorActionListener(new OnEditorActionListener());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Click(R.id.btnSearch)
    void onClickSearch(){
        keyword = etSearch.getText().toString();
        ((FragmentSearchList)adapter.getItem(0)).searchKeyword(keyword);
        ((FragmentSearchList)adapter.getItem(1)).searchKeyword(keyword);
        ((FragmentSearchList)adapter.getItem(2)).searchKeyword(keyword);
        ((FragmentSearchList)adapter.getItem(3)).searchKeyword(keyword);
        Utils.hideSoftKeyboard(getActivity());

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

    private void resetSearchText(){
        etSearch.setText("");
    }

}
