package com.fd.goraebang.util.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fd.goraebang.R;

import java.util.List;

public class RecyclerAdapterSetting extends RecyclerView.Adapter<RecyclerAdapterSetting.ViewHolder> {
    private List<String> mValues;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public final TextView tv;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tv = (TextView) view.findViewById(R.id.tv);
        }
    }

    public RecyclerAdapterSetting(Context context, List<String> items) {
        this.mContext = context;
        this.mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_recycler_setting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String item = mValues.get(position);

        holder.tv.setText(item);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
