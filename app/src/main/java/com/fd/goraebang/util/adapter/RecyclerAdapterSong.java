package com.fd.goraebang.util.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fd.goraebang.R;
import com.fd.goraebang.model.Song;

import java.util.List;

public class RecyclerAdapterSong extends RecyclerView.Adapter<RecyclerAdapterSong.ViewHolder> {
    private List<Song> mValues;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public final TextView tvTitle;
        public final TextView tvDate;
        public final TextView tvArtist;
        public final TextView tvCntFavorite;
        public final TextView tvWords;
        public final ImageView iv;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvArtist = (TextView) view.findViewById(R.id.tvArtist);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvWords = (TextView) view.findViewById(R.id.tvWords);
            tvCntFavorite = (TextView) view.findViewById(R.id.tvCntFavorite);
            iv = (ImageView) view.findViewById(R.id.iv);
        }
    }

    public RecyclerAdapterSong(Context context, List<Song> items) {
        this.mContext = context;
        this.mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_recycler_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Song item = mValues.get(position);

        holder.tvTitle.setText(item.getTitle());
        holder.tvArtist.setText(item.getArtistName());
        holder.tvWords.setText(item.getLyrics());
        holder.tvCntFavorite.setText(item.getCntFavorite() + "");
        Glide.with(mContext).load(item.getJacketSmall()).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
