package com.test.nordluxdemo.home.moods;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.home.moods.edit.MoodsEditActivity;
import com.test.nordluxdemo.home.moods.newset.MoodsNewActivity;

public class MoodsMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    public MoodsMenuAdapter(Context mContext) {
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new MoodsMenuAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.moods_recycle_item01, parent, false));
            case 1:
                return new MoodsMenuAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.moods_recycle_item02, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(mContext, MoodsNewActivity.class);
                                mContext.startActivity(intent);
                            }
                        });
                        break;
                    case 1:
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(mContext, MoodsEditActivity.class);
                                mContext.startActivity(intent);
                            }
                        });
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 2;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
        }
        return 1;
    }
}
