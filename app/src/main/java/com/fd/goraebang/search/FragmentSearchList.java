package com.fd.goraebang.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fd.goraebang.R;
import com.fd.goraebang.consts.CONST;
import com.fd.goraebang.custom.CustomFragmentWithRecyclerView;
import com.fd.goraebang.model.Song;
import com.fd.goraebang.song.ActivitySongDetail_;
import com.fd.goraebang.song.SongFavoriteController;
import com.fd.goraebang.util.AppController;
import com.fd.goraebang.util.CallUtils;
import com.fd.goraebang.util.adapter.RecyclerAdapterSong;
import com.fd.goraebang.util.listener.CallbackFavoriteListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

@EFragment(R.layout.fragment_layout_recycler_view)
public class FragmentSearchList extends CustomFragmentWithRecyclerView implements CallbackFavoriteListener{
    private List<Song> items = new ArrayList<>();
    private String keyword = null;
    private String type = null;
    private SongFavoriteController songFavoriteController;

    private String filterGenre = null;
    private String filterAge = null;
    private String filterGender = null;

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
        isAddDefaultOnItemTouchListener = false;
        type = getArguments().getString("type");
        keyword = getArguments().getString("keyword");

        super.onCreate(savedInstanceState);

        if(adapter == null) {
            adapter = new RecyclerAdapterSong(getActivity(), items, this);
        }

        if(songFavoriteController == null){
            songFavoriteController = new SongFavoriteController(getActivity(), items, this);
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
        if(keyword == null || keyword.length() < 1){
            return;
        }

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        if(page == 0 && items.size() > 0){
            items.clear();
        }

        Call<List<Song>> call = AppController.getSongService().getSearch(AppController.USER_TOKEN, keyword, type.toLowerCase(), filterGenre, filterAge, filterGender, page);
        call.enqueue(new CallUtils<List<Song>>(call, getActivity(), getResources().getString(R.string.msgErrorCommon)) {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().size() > 0) {
                    items.addAll(response.body());
                }
                onComplete();
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                onComplete();
            }

            @Override
            public void onComplete() {
                swipeRefreshLayout.setRefreshing(false);
                updateView();
            }
        });
    }

    protected void searchKeyword(String keyword, String genre, String age, String gender){
        this.items.clear();

        this.filterGenre = genre;
        this.filterAge = age;
        this.filterGender = gender;
        this.keyword = keyword;

        loadData(0);
    }

    @Override
    public void updateView(){
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
        return;
    }

    @Override
    public void onClick(int viewId, int position) {
        if(items.size() < position){
            return;
        }

        switch (viewId){
            case CONST.LONG_CLICK_LISTENER:
                songFavoriteController.isCreateBlacklist(position);
                break;
            case R.id.btnBox:
                if(items.get(position).isFavorite()){
                    songFavoriteController.deleteFavorite(position);
                }else{
                    songFavoriteController.createFavorite(position);
                }
                break;
            default:
                Intent intent = new Intent(getActivity(), ActivitySongDetail_.class);
                intent.putExtra("song", items.get(position));
                startActivityForResult(intent, CONST.RQ_CODE_SONG_DETAIL);
        }
    }
}