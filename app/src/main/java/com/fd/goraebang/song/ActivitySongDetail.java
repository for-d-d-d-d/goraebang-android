package com.fd.goraebang.song;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fd.goraebang.R;
import com.fd.goraebang.custom.CustomActivityWithToolbar;
import com.fd.goraebang.model.Song;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_song_detail)
public class ActivitySongDetail extends CustomActivityWithToolbar {
    @ViewById
    TextView tvTitle, tvArtist, tvLyrics, tvCntFavorite;

    @ViewById
    ImageView iv;

    private Song item = null;

    @AfterViews
    void init(){
        setToolbar("상세보기", 0, R.drawable.ic_arrow_back_white_24dp, 0, 0);

        Intent intent = getIntent();
        item = (Song)intent.getSerializableExtra("song");

        tvTitle.setText(item.getTitle());
        tvArtist.setText(item.getArtistName());
        tvLyrics.setText(Html.fromHtml(item.getLyrics()));
        tvCntFavorite.setText(item.getCntFavorite() + "");
        Glide.with(this).load(item.getJacketSmall()).into(iv);
    }


    public void onClick(View v) {
        Intent intent = null;

        switch(v.getId()){
            case R.id.btnLeft:
                finish();
                break;
            default :
                break;
        }

        if(intent != null) {
            startActivity(intent);
        }
    }
}
