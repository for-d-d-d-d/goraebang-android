package com.fd.goraebang.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fd.goraebang.R;
import com.fd.goraebang.consts.CONST;
import com.fd.goraebang.custom.CustomFragment;
import com.fd.goraebang.model.Song;
import com.fd.goraebang.song.ActivitySongDetail_;
import com.fd.goraebang.util.adapter.RecyclerAdapterSongGrid;
import com.fd.goraebang.util.listener.RecyclerItemClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.ArrayList;

@EFragment(R.layout.fragment_tab_home_top_chart)
public class FragmentHomeTopChart extends CustomFragment {
    @ViewById
    RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
    private ArrayList<Song> items = null;

    public static FragmentHomeTopChart newInstance(ArrayList<Song> items) {
        FragmentHomeTopChart f = new FragmentHomeTopChart_();
        Bundle b = new Bundle();
        b.putSerializable("items", (Serializable) items);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        items = (ArrayList<Song>) getArguments().getSerializable("items");

        if(adapter == null) {
            adapter = new RecyclerAdapterSongGrid(getActivity(), items);
        }
    }

    @AfterViews
    void init(){
        if(adapter == null || items == null || items.size() < 1)
            return;

        LinearLayoutManager mLinearLayoutManager = new GridLayoutManager(getActivity(), 3);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new OnItemClickListener()));
    }

    private class OnItemClickListener extends RecyclerItemClickListener.SimpleOnItemClickListener{
        @Override
        public void onItemClick(View view, int position) {
            FragmentHomeTopChart.this.onItemClick(view, position);
        }
    }

    protected void onItemClick(View view, int position) {
        if(items.size() < position)
            return;

        Intent intent = new Intent(getActivity(), ActivitySongDetail_.class);
        intent.putExtra("song", items.get(position));
        startActivityForResult(intent, CONST.RQ_CODE_SONG_DETAIL);
    }
}