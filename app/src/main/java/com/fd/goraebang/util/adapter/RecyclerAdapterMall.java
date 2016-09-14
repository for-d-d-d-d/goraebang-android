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
import com.fd.goraebang.model.Mall;

import java.util.List;

public class RecyclerAdapterMall extends RecyclerView.Adapter<RecyclerAdapterMall.ViewHolder> {
    private List<Mall> mValues;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public final TextView tvName;
        public final ImageView iv;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvName = (TextView) view.findViewById(R.id.tvName);
            iv = (ImageView) view.findViewById(R.id.iv);
        }
    }

    public RecyclerAdapterMall(Context context, List<Mall> items) {
        this.mValues = items;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_recycler_mall, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Mall item = mValues.get(position);

        holder.tvName.setText(item.getName());
        Glide.with(mContext).load(item.getName()).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
