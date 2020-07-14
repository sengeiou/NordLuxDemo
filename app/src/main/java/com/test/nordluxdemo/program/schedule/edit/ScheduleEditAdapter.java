package com.test.nordluxdemo.program.schedule.edit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.program.schedule.newset.ScheduleNewAdapter;

public class ScheduleEditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ImageView imageView;
    public ScheduleEditAdapter(Context mContext) {
        this.mContext=mContext;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new ScheduleEditAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.schedule_edit_recycle_item01, parent, false));
            case 1:
                return new ScheduleEditAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.schedule_edit_recycle_item02, parent, false));
            case 2:
                return new ScheduleEditAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.schedule_edit_recycle_item03, parent, false));
            case 3:
                return new ScheduleEditAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.schedule_edit_recycle_item04, parent, false));
            case 4:
                return new ScheduleEditAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.schedule_edit_recycle_item05, parent, false));

        }
        return new ScheduleEditAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.schedule_edit_recycle_item05, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);

        switch (position){
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;

        }
        return 2;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
