package com.fd.goraebang.main;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.fd.goraebang.R;
import com.fd.goraebang.custom.CustomActivityWithToolbar;
import com.fd.goraebang.model.Song;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_song_detail)
public class ActivitySongDetail extends CustomActivityWithToolbar {
    @ViewById
    TextView tvTitle;

    private Song item = null;

    @AfterViews
    void init(){
        setToolbar("상세보기", 0, R.drawable.ic_arrow_back_white_24dp, 0, 0);

        Intent intent = getIntent();
        item = (Song)intent.getSerializableExtra("song");

        tvTitle.setText(item.getTitle());
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
