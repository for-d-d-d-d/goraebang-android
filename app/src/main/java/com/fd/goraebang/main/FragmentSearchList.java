package com.fd.goraebang.main;

import android.os.Bundle;
import android.view.View;

import com.fd.goraebang.R;
import com.fd.goraebang.custom.CustomFragmentWithRecyclerView;
import com.fd.goraebang.model.Song;
import com.fd.goraebang.util.adapter.RecyclerAdapterSong;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_layout_recycler_view)
public class FragmentSearchList extends CustomFragmentWithRecyclerView {
    private List<Song> items = null;
    private String keyword = null;

    public static FragmentSearchList newInstance(String type, String keyword) {
        FragmentSearchList f = new FragmentSearchList_();
        Bundle b = new Bundle();
        b.putString("type", type);
        b.putString("keyword", keyword);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        if(keyword == null)
            keyword = getArguments().getString("keyword");

        super.onCreate(savedInstanceState);

        if(items == null){
            items = new ArrayList<>();
        }

        if(adapter == null) {
            adapter = new RecyclerAdapterSong(getActivity(), items);
        }
    }

    @AfterViews
    void init(){
        if(adapter == null)
            return;

        super.setupRecyclerView();
        super.setupSwipeRefreshLayout();

        if(items.size() == 0) {
            loadData(0);
        }
    }

    @Override
    protected void loadData(int page){
        swipeRefreshLayout.setRefreshing(true);

        if(page == 0 && items.size() > 0){
            items.clear();
        }

        if(keyword == null || keyword.length() < 1){
            return;
        }
//
//        Call<List<Post>> call = AppController.getPostService().getSearch(AppController.AUTHORIZATION, keyword, type, offset, limit);
//        call.enqueue(new CallUtils<List<Post>>(call, getActivity(), getResources().getString(R.string.msgErrorLoadList)) {
//            @Override
//            public void onResponse(Response<List<Post>> response) {
//                onComplete();
//
//                if (response.isSuccess()) {
//                    if (response.body().size() > 0 && response.body().get(0).getEmptyImageUrl() != null) {
//                        emptyImageUrl = response.body().get(0).getEmptyImageUrl();
//                    } else {
//                        items.addAll(response.body());
//                    }
//                    updateView();
//                } else {
//                    setMessage(msg);
//                }
//            }
//
//            @Override
//            public void onComplete() {
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
    }

    protected void searchKeyword(String keyword){
        if(items == null)
            return;

        items.clear();
        this.keyword = keyword;

        loadData(0);
    }

    void updateView(){
        setMessage("");
        adapter.notifyDataSetChanged();

        if(items.size() == 0){
            setMessage("검색결과가 없습니다.");
        }
    }

    @Override
    protected void onRefresh() {
        items.clear();
        adapter.notifyDataSetChanged();
        loadData(0);
    }

    @Override
    protected void onItemClick(View view, int position) {
    }
}