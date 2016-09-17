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

public class RecyclerAdapterProduct extends RecyclerView.Adapter<RecyclerAdapterProduct.ViewHolder> {
    private List<Song> mValues;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public final TextView tvName;
        public final TextView tvPrice;
        public final TextView tvMall;
        public final ImageView iv;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvMall = (TextView) view.findViewById(R.id.tvMall);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            iv = (ImageView) view.findViewById(R.id.iv);
        }
    }

    public RecyclerAdapterProduct(Context context, List<Song> items) {
        this.mContext = context;
        this.mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_recycler_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Song item = mValues.get(position);

        holder.tvName.setText(item.getName());
        holder.tvMall.setText(item.getMall().getName());
        holder.tvPrice.setText(item.getPrice() + "");
        Glide.with(mContext).load(item.getImage()).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
