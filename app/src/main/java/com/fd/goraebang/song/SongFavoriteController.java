package com.fd.goraebang.song;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.fd.goraebang.R;
import com.fd.goraebang.model.Song;
import com.fd.goraebang.util.AppController;
import com.fd.goraebang.util.CallUtils;
import com.fd.goraebang.util.CustomAlertDialog;
import com.fd.goraebang.util.listener.CallbackFavoriteListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class SongFavoriteController {
    private CustomAlertDialog mCustomAlertDialog;
    private List<Song> items = null;
    private Context mContext;
    private CallbackFavoriteListener mListener;

    public SongFavoriteController(Context context, Song item, CallbackFavoriteListener listener) {
        this.items = new ArrayList<>();
        this.items.add(item);
        this.mContext = context;
        this.mListener = listener;
    }

    public SongFavoriteController(Context context, List<Song> items, CallbackFavoriteListener listener) {
        this.items = items;
        this.mContext = context;
        this.mListener = listener;
    }

    public void deleteFavorite(final int position){
        if(items.size() < position || !(mListener instanceof CallbackFavoriteListener)) {
            return;
        }

        Call<List<Song>> call = AppController.getSongService().deleteMyListSong(AppController.USER_MY_LIST_ID, items.get(position).getMySongId(), AppController.USER_ID, items.get(position).getId());
        call.enqueue(new CallUtils<List<Song>>(call, mContext, mContext.getResources().getString(R.string.msgErrorCommon)) {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if (response.isSuccessful()) {
                    items.get(position).setFavorite(false);
                    items.get(position).setCntFavorite(items.get(position).getCntFavorite() - 1);
                    mListener.updateView();
                }
            }
            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
    }

    public void createFavorite(final int position){
        if(items.size() < position || !(mListener instanceof CallbackFavoriteListener)) {
            return;
        }

        Call<Song> call = AppController.getSongService().createMyListSong(AppController.USER_MY_LIST_ID, AppController.USER_ID, items.get(position).getId());
        call.enqueue(new CallUtils<Song>(call, mContext, mContext.getResources().getString(R.string.msgErrorCommon)) {
            @Override
            public void onResponse(Call<Song> call, Response<Song> response) {
                if (response.isSuccessful() && response.body().getId() > 0) {
                    items.get(position).setFavorite(true);
                    items.get(position).setCntFavorite(items.get(position).getCntFavorite() + 1);
                    mListener.updateView();
                }
            }
            @Override
            public void onFailure(Call<Song> call, Throwable t) {
            }
        });
    }

    public void isCreateBlacklist(final int position){
        if(items.size() < position || !(mListener instanceof CallbackFavoriteListener)) {
            return;
        }

        mCustomAlertDialog = new CustomAlertDialog(mContext,
                "블랙리스트에 추가하시겠습니까 ?",
                "아니오", "예",
                new View.OnClickListener() {
                    public void onClick(View v) {
                        mCustomAlertDialog.dismiss();
                    }
                },
                new View.OnClickListener() {
                    public void onClick(View v) {
                        mCustomAlertDialog.dismiss();
                        createBlacklist(position);
                    }
                });
        mCustomAlertDialog.show();

    }

    public void isDeleteBlacklist(final int position){
        if(items.size() < position || !(mListener instanceof CallbackFavoriteListener)) {
            return;
        }

        mCustomAlertDialog = new CustomAlertDialog(mContext,
                "블랙리스트에서 제거하시겠습니까 ?",
                "아니오", "예",
                new View.OnClickListener() {
                    public void onClick(View v) {
                        mCustomAlertDialog.dismiss();
                    }
                },
                new View.OnClickListener() {
                    public void onClick(View v) {
                        mCustomAlertDialog.dismiss();
                        deleteBlacklist(position);
                    }
                });
        mCustomAlertDialog.show();

    }

    public void createBlacklist(final int position){
        Call<Song> call = AppController.getSongService().createBlacklistSong(AppController.USER_ID, items.get(position).getId());
        call.enqueue(new CallUtils<Song>(call, mContext, mContext.getResources().getString(R.string.msgErrorCommon)) {
            @Override
            public void onResponse(Call<Song> call, Response<Song> response) {
                if (response.isSuccessful()) {
                    items.remove(position);
                    mListener.updateView();
                }
            }
            @Override
            public void onFailure(Call<Song> call, Throwable t) {

            }
        });
    }

    public void deleteBlacklist(final int position){
        Call<List<Song>> call = AppController.getSongService().deleteBlacklistSong(items.get(position).getId(), AppController.USER_ID);
        call.enqueue(new CallUtils<List<Song>>(call, mContext, mContext.getResources().getString(R.string.msgErrorCommon)) {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if (response.isSuccessful()) {
                    items.remove(position);
                    mListener.updateView();
                }
            }
            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
    }
}
