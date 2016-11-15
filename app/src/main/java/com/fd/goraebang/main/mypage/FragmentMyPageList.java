package com.fd.goraebang.main.mypage;

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
public class FragmentMyPageList extends CustomFragmentWithRecyclerView implements CallbackFavoriteListener{
    private List<Song> items = null;
    private String type = null;
    private String emptyMessage = null;
    private SongFavoriteController songFavoriteController;

    public static FragmentMyPageList newInstance(String type) {
        FragmentMyPageList f = new FragmentMyPageList_();
        Bundle b = new Bundle();
        b.putString("type", type);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        isAddDefaultOnItemTouchListener = false;

        super.onCreate(savedInstanceState);

        type = getArguments().getString("type");

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
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        if(page == 0){
            items.clear();
        }

        Call<List<Song>> call = null;

        if(type.equals("ANALYSIS")){
            emptyMessage = "취향분석 데이터가 없습니다.";
            call = AppController.getSongService().getMyPageAnalysis(AppController.USER_ID, page);
        }else if(type.equals("FAVORITE")){
            emptyMessage = "마이리스트가 비어 있습니다.";
            call = AppController.getSongService().getMyPageFavorite(AppController.USER_ID, AppController.USER_MY_LIST_ID, page);
        }else if(type.equals("BLACKLIST")){
            emptyMessage = "블랙리스트가 없습니다.";
            call = AppController.getSongService().getMyPageBlacklist(AppController.USER_ID, page);
        }else{
            return;
        }

        call.enqueue(new CallUtils<List<Song>>(call, getActivity(), getResources().getString(R.string.msgErrorCommon)) {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if (response.isSuccessful() && response.body() != null) {
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

    @Override
    public void updateView(){
        setMessage("");
        adapter.notifyDataSetChanged();

        if(items.size() == 0){
            setMessage(emptyMessage);
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
                if (type.equals("BLACKLIST")) {
                    songFavoriteController.isDeleteBlacklist(position);
                } else {
                    songFavoriteController.isCreateBlacklist(position);
                }
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