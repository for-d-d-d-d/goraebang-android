package com.fd.goraebang.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fd.goraebang.R;
import com.fd.goraebang.consts.CONST;
import com.fd.goraebang.custom.CustomFragmentWithRecyclerView;
import com.fd.goraebang.main.ActivitySongDetail_;
import com.fd.goraebang.model.Song;
import com.fd.goraebang.util.AppController;
import com.fd.goraebang.util.CallUtils;
import com.fd.goraebang.util.adapter.RecyclerAdapterSong;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

@EFragment(R.layout.fragment_layout_recycler_view)
public class FragmentSearchList extends CustomFragmentWithRecyclerView {
    private List<Song> items = new ArrayList<>();;
    private String keyword = null;
    private String type = null;

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
        type = getArguments().getString("type");
        keyword = getArguments().getString("keyword");

        super.onCreate(savedInstanceState);

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

        Call<List<Song>> call = null;

        if(type.equals("FILTER")) {
            call = AppController.getSongService().getSearch(keyword, page);
        }else if(type.equals("TITLE")){
            call = AppController.getSongService().getSearchByTitle(keyword, page);
        }else if(type.equals("ARTIST")){
            call = AppController.getSongService().getSearchByArtist(keyword, page);
        }else if(type.equals("LYRICS")){
            call = AppController.getSongService().getSearchByLyrics(keyword, page);
        }else{
            return;
        }
        Log.d("aaaaa",type);

        call.enqueue(new CallUtils<List<Song>>(call, getActivity(), getResources().getString(R.string.msgErrorCommon)) {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                onComplete();
                if (response.isSuccessful() && response.body() != null) {
                    items.addAll(response.body());
                    updateView();
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.d("aaaaa","onFailure " + type);
            }

            @Override
            public void onComplete() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    protected void searchKeyword(String keyword){
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
        if(items.size() < position)
            return;

        Intent intent = new Intent(getActivity(), ActivitySongDetail_.class);
        intent.putExtra("song", items.get(position));
        startActivityForResult(intent, CONST.RQ_CODE_SONG_DETAIL);
    }
}