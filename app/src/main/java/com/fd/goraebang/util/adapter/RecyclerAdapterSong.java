package com.fd.goraebang.util.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fd.goraebang.R;
import com.fd.goraebang.consts.CONST;
import com.fd.goraebang.model.Song;
import com.fd.goraebang.util.listener.CallbackFavoriteListener;

import java.util.List;

public class RecyclerAdapterSong extends RecyclerView.Adapter<RecyclerAdapterSong.ViewHolder> {
    private List<Song> mValues;
    private Context mContext;
    private boolean isShowLyrics;
    private CallbackFavoriteListener mListener;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public final TextView tvTitle;
        public final TextView tvArtist;
        public final TextView tvCntFavorite;
        public final TextView tvTjnum;
        public final TextView tvLyrics;
        public final TextView tvRelease;
        public final ImageView iv;
        public final ImageView ivBox;
        public final ImageView ivBoxSmall;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvArtist = (TextView) view.findViewById(R.id.tvArtist);
            tvLyrics = (TextView) view.findViewById(R.id.tvLyrics);
            tvRelease = (TextView) view.findViewById(R.id.tvRelease);
            tvCntFavorite = (TextView) view.findViewById(R.id.tvCntFavorite);
            tvTjnum = (TextView) view.findViewById(R.id.tvTjnum);
            iv = (ImageView) view.findViewById(R.id.iv);
            ivBox = (ImageView) view.findViewById(R.id.ivBox);
            ivBoxSmall = (ImageView) view.findViewById(R.id.ivBoxSmall);
        }
    }

    public RecyclerAdapterSong(Context context, List<Song> items, CallbackFavoriteListener listener) {
        this(context, items, listener, false);
    }

    public RecyclerAdapterSong(Context context, List<Song> items, CallbackFavoriteListener listener, boolean isShowLyrics) {
        this.mContext = context;
        this.mValues = items;
        this.isShowLyrics = isShowLyrics;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_recycler_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Song item = mValues.get(position);

        holder.tvTitle.setText(item.getTitle());
        holder.tvArtist.setText(item.getArtistName());
        if(this.isShowLyrics){
            holder.tvLyrics.setText(Html.fromHtml(item.getLyrics() + ""));
        }else{
            holder.tvCntFavorite.setText(item.getCntFavorite() + "");
            holder.tvRelease.setText(item.getRelease());
        }

        if (item.isFavorite()) {
            holder.ivBox.setImageResource(R.drawable.ic_box_on);
            holder.ivBoxSmall.setImageResource(R.drawable.ic_box_on);
        } else {
            holder.ivBox.setImageResource(R.drawable.ic_box_off);
            holder.ivBoxSmall.setImageResource(R.drawable.ic_box_off);
        }

        holder.tvTjnum.setText(item.getSongTjnum());
        Glide.with(mContext).load(item.getJacketSmall()).into(holder.iv);

        holder.ivBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null)
                    mListener.onClick(v.getId(), position);
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mListener != null)
                    mListener.onClick(CONST.LONG_CLICK_LISTENER, position);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
