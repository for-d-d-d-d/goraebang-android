package com.fd.goraebang.song;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fd.goraebang.R;
import com.fd.goraebang.consts.CONST;
import com.fd.goraebang.custom.CustomActivityWithRecyclerView;
import com.fd.goraebang.model.Song;
import com.fd.goraebang.util.AppController;
import com.fd.goraebang.util.CallUtils;
import com.fd.goraebang.util.adapter.RecyclerAdapterSong;
import com.fd.goraebang.util.listener.CallbackFavoriteListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

@EActivity(R.layout.activity_chart)
public class ActivityTopChart extends CustomActivityWithRecyclerView implements CallbackFavoriteListener {
    private List<Song> items = new ArrayList<>();
    private SongFavoriteController songFavoriteController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        isAddDefaultOnItemTouchListener = false;

        super.onCreate(savedInstanceState);

        if(adapter == null) {
            adapter = new RecyclerAdapterSong(this, items, this);
        }

        if(songFavoriteController == null){
            songFavoriteController = new SongFavoriteController(this, items, this);
        }
    }
    
    @AfterViews
    void afterViews(){
        setToolbar("고래방 TOP 100", 0, R.drawable.ic_arrow_back_white_24dp, 0, 0);

        if(adapter == null)
            return;

        super.setupRecyclerView();
        super.setupSwipeRefreshLayout();

        if(items.size() == 0) {
            loadData(0);
        }
    }

    @Override
    protected void loadData(int page) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        if(page == 0)
            items.clear();

        Call<List<Song>> call = AppController.getSongService().getTopChart(page);
        call.enqueue(new CallUtils<List<Song>>(call, this, getResources().getString(R.string.msgErrorCommon)) {
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

            }

            @Override
            public void onComplete() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void updateView(){
        setMessage("");
        adapter.notifyDataSetChanged();
        if(items.size() == 0){
            setMessage("등록된 노래가 없거나 불러오지 못 했습니다.");
        }
    }

    @Override
    protected void onRefresh() {
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
                Intent intent = new Intent(this, ActivitySongDetail_.class);
                intent.putExtra("song", items.get(position));
                startActivityForResult(intent, CONST.RQ_CODE_SONG_DETAIL);
        }
    }
}
