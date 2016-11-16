package com.fd.goraebang.song;

import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fd.goraebang.R;
import com.fd.goraebang.custom.CustomActivityWithToolbar;
import com.fd.goraebang.model.Song;
import com.fd.goraebang.util.listener.CallbackFavoriteListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_song_detail)
public class ActivitySongDetail extends CustomActivityWithToolbar implements CallbackFavoriteListener {
    @ViewById
    TextView tvTitle, tvArtist, tvLyrics, tvTjnum;

    @ViewById
    ImageView iv;

    @ViewById
    ImageButton btnBox;

    private Song item = null;
    private SongFavoriteController songFavoriteController;

    @AfterViews
    void init(){
        setToolbar("상세보기", 0, R.drawable.ic_arrow_back_white_24dp, 0, 0);

        Intent intent = getIntent();
        item = (Song)intent.getSerializableExtra("song");

        tvTitle.setText(item.getTitle());
        tvArtist.setText(item.getArtistName());
        tvLyrics.setText(Html.fromHtml(item.getLyrics()));
        tvTjnum.setText(item.getSongTjnum() + "");
        Glide.with(this).load(item.getJacketSmall()).into(iv);

        updateView();

        if(songFavoriteController == null){
            songFavoriteController = new SongFavoriteController(this, item, this);
        }
    }

    @Override
    public void updateView() {
        if(item.isFavorite()){
            btnBox.setImageResource(R.drawable.ic_box_on);
        }else{
            btnBox.setImageResource(R.drawable.ic_box_off);
        }
    }

    public void onClick(View v) {
        Intent intent = null;

        switch(v.getId()){
            case R.id.btnLeft:
                finish();
                break;
            case R.id.btnBox:
                if(item.isFavorite()){
                    songFavoriteController.deleteFavorite(0);
                }else{
                    songFavoriteController.createFavorite(0);
                }
                break;
            default :
                break;
        }

        if(intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onClick(int viewId, int position) {
        // not use.
    }
}
