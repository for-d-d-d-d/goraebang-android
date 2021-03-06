package com.fd.goraebang.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fd.goraebang.R;
import com.fd.goraebang.consts.CONST;
import com.fd.goraebang.custom.CustomFragmentWithRecyclerView;
import com.fd.goraebang.model.Song;
import com.fd.goraebang.search.ActivitySearch_;
import com.fd.goraebang.song.ActivitySongDetail_;
import com.fd.goraebang.song.SongFavoriteController;
import com.fd.goraebang.util.AppController;
import com.fd.goraebang.util.CallUtils;
import com.fd.goraebang.util.adapter.RecyclerAdapterSong;
import com.fd.goraebang.util.listener.CallbackFavoriteListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

@EFragment(R.layout.fragment_tab_search)
public class FragmentSearch extends CustomFragmentWithRecyclerView implements CallbackFavoriteListener{
    private List<Song> items = null;
    private SongFavoriteController songFavoriteController;

    public static FragmentSearch newInstance() {
        FragmentSearch f = new FragmentSearch_();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        isShowDivider = true;
        isInfiniteScroll = false;
        isAddDefaultOnItemTouchListener = false;

        super.onCreate(savedInstanceState);

        if(items == null){
            items = new ArrayList<>();
        }

        if(adapter == null) {
            adapter = new RecyclerAdapterSong(getActivity(), items, this);
        }

        if(songFavoriteController == null){
            songFavoriteController = new SongFavoriteController(getActivity(), items, this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @AfterViews
    void afterViews() {
        if(adapter == null)
            return;

        super.setupRecyclerView();
        super.setupSwipeRefreshLayout();

        if(items.size() == 0) {
            loadData(0);
        }
    }

    @Click(R.id.llSearchBar)
    void onClickSearchText(){
        startActivity(new Intent(getActivity(), ActivitySearch_.class));
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

        Call<List<Song>> call = AppController.getSongService().getTopChart(AppController.USER_TOKEN, page);
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
                Intent intent = new Intent(getActivity(), ActivitySongDetail_.class);
                intent.putExtra("song", items.get(position));
                startActivityForResult(intent, CONST.RQ_CODE_SONG_DETAIL);
        }
    }
}
