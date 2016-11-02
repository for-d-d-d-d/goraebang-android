package com.fd.goraebang.search;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.fd.goraebang.R;
import com.fd.goraebang.custom.CustomActivityWithToolbar;
import com.fd.goraebang.util.AppController;
import com.fd.goraebang.util.CallUtils;
import com.fd.goraebang.util.Utils;
import com.fd.goraebang.util.adapter.FragmentTabPagerAdapter;
import com.fd.goraebang.util.adapter.RecyclerAdapterText;
import com.fd.goraebang.util.decorator.RecyclerDividerDecoration;
import com.fd.goraebang.util.listener.RecyclerItemClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Response;

@EActivity(R.layout.activity_search)
public class ActivitySearch extends CustomActivityWithToolbar {
    @ViewById
    EditText etSearch;
    @ViewById
    ViewPager viewPager;
    @ViewById
    TabLayout tabLayout;
    @ViewById
    RecyclerView recyclerView;
    @ViewById
    SwipeRefreshLayout swipeRefreshLayout;

    private List<String> items = new ArrayList<>();
    private FragmentTabPagerAdapter adapter;
    private String keyword;
    private RecyclerAdapterText keywordAdapter = null;
    private Timer timer = new Timer();
    private final long DELAY = 700;

    @AfterViews
    void afterViews(){
        setToolbar("검색", 0, R.drawable.ic_arrow_back_white_24dp, R.drawable.ic_local_bar_white_24dp, 0);

        if(adapter == null){
            adapter = new FragmentTabPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(FragmentSearchList.newInstance("TITLE", keyword), "제목별");
            adapter.addFragment(FragmentSearchList.newInstance("ARTIST", keyword), "아티스트별");
            adapter.addFragment(FragmentSearchList.newInstance("LYRICS", keyword), "가사별");
        }

        if(keywordAdapter == null) {
            keywordAdapter = new RecyclerAdapterText(this, items);
        }

        etSearch.setOnEditorActionListener(new OnEditorActionListener());
        etSearch.setOnFocusChangeListener(new OnFocusChangeListener());
        etSearch.addTextChangedListener(new OnTextWatcher());

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);

        setupRecyclerView();
        setupSwipeRefreshLayout();
        updateView();
    }
    
    protected void setupRecyclerView() {
        recyclerView.setAdapter(keywordAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new OnItemClickListener()));
        recyclerView.addItemDecoration(new RecyclerDividerDecoration(this));
    }

    private void onClickSearch(){
        etSearch.setClickable(false);
        etSearch.setEnabled(false);
        etSearch.setFocusable(false);
        keyword = etSearch.getText().toString();

        recyclerView.setVisibility(View.GONE);
        items.clear();

        ((FragmentSearchList)adapter.getItem(0)).searchKeyword(keyword);
        ((FragmentSearchList)adapter.getItem(1)).searchKeyword(keyword);
        ((FragmentSearchList)adapter.getItem(2)).searchKeyword(keyword);

        Utils.hideSoftKeyboard(this);

        etSearch.setClickable(true);
        etSearch.setEnabled(true);
        etSearch.setFocusable(true);
        etSearch.setFocusableInTouchMode(true);
    }

    @UiThread
    void loadKeywords(){
        items.clear();
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        String keyword = etSearch.getText().toString();
        Call<List<HashMap<String, ArrayList<String>>>> call = AppController.getSongService().getSearchByAutoComplete(AppController.USER_TOKEN, keyword, "title", 0, true);
        call.enqueue(new CallUtils<List<HashMap<String, ArrayList<String>>>>(call, this, getResources().getString(R.string.msgErrorCommon)) {
            @Override
            public void onResponse(Call<List<HashMap<String, ArrayList<String>>>> call, Response<List<HashMap<String, ArrayList<String>>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().size() > 0) {
                    for(HashMap<String, ArrayList<String>> item:response.body()){
                        try{
                            ArrayList<String> list = item.get("artists");
                            items.addAll(list);
                        }catch (Exception e){ }
                        try{
                            ArrayList<String> list = item.get("title");
                            items.addAll(list);
                        }catch (Exception e){ }
                        try{
                            ArrayList<String> list = item.get("lyrics");
                            items.addAll(list);
                        }catch (Exception e){ }
                    }
                }
                onComplete();
            }

            @Override
            public void onFailure(Call<List<HashMap<String, ArrayList<String>>>> call, Throwable t) {
                onComplete();
            }

            @Override
            public void onComplete() {
                swipeRefreshLayout.setEnabled(false);
                swipeRefreshLayout.setRefreshing(false);
                updateView();
            }
        });

        updateView();
    }

    private void updateView(){
        keywordAdapter.notifyDataSetChanged();
    }

    protected void setupSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshListener());
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    private void openFilter(){
        String filters[] = {"장르별", "연도별", "성별"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("조건 검색")
                .setItems(filters, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("aaaaaa","aaa : " + which);
                    }
                });
        
        builder.show();
    }

    private class SwipeRefreshListener implements SwipeRefreshLayout.OnRefreshListener{
        @Override
        public void onRefresh() {
            this.onRefresh();
        }
    }
    private class OnItemClickListener extends RecyclerItemClickListener.SimpleOnItemClickListener{
        @Override
        public void onItemClick(View view, int position) {
            if(items.size() < position)
                return;

            etSearch.setText(items.get(position));
            onClickSearch();
        }
    }

    private class OnTextWatcher implements TextWatcher {
        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            if(timer != null)
                timer.cancel();
        }
        @Override
        public void afterTextChanged(Editable arg0) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    loadKeywords();
                }

            }, DELAY);
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }
    }

    private class OnFocusChangeListener implements EditText.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view, boolean bFocus) {
            if(bFocus){
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
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
            case R.id.btnRight:
                openFilter();
                break;
            default :
                break;
        }

        if(intent != null) {
            startActivity(intent);
        }
    }
}
