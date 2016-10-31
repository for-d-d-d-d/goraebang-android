package com.fd.goraebang.util.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fd.goraebang.R;
import com.fd.goraebang.model.Song;
import com.fd.goraebang.util.AppController;

import java.util.List;

public class RecyclerAdapterSongGrid extends RecyclerView.Adapter<RecyclerAdapterSongGrid.ViewHolder> {
    private List<Song> mValues;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public final CardView cardView;
        public final TextView tvTitle;
        public final TextView tvArtist;
        public final TextView tvTjnum;
        public final ImageView iv;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            cardView = (CardView) view.findViewById(R.id.cardView);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvArtist = (TextView) view.findViewById(R.id.tvArtist);
            tvTjnum = (TextView) view.findViewById(R.id.tvTjnum);
            iv = (ImageView) view.findViewById(R.id.iv);
        }
    }

    public RecyclerAdapterSongGrid(Context context, List<Song> items) {
        this.mValues = items;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.layout_item_recycler_song_grid;
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Song item = mValues.get(position);

        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams)holder.cardView.getLayoutParams();
        layoutParams.height = AppController.GRID_COLUMN_HEIGHT;

        holder.tvTitle.setText(item.getTitle());
        holder.tvArtist.setText(item.getArtistName());
        holder.tvTjnum.setText(item.getSongTjnum());
        Glide.with(mContext).load(item.getJacketSmall()).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
