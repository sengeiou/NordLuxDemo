package com.test.nordluxdemo.program.timer.edit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.program.schedule.edit.ScheduleEditAdapter;

public class TimerEditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ImageView imageView;
    public TimerEditAdapter(Context mContext) {
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new TimerEditAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.timer_edit_recycle_item01, parent, false));
            case 1:
                return new TimerEditAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.timer_edit_recycle_item02, parent, false));
            case 2:
                return new TimerEditAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.timer_edit_recycle_item03, parent, false));

        }
        return new TimerEditAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.schedule_edit_recycle_item05, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
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
