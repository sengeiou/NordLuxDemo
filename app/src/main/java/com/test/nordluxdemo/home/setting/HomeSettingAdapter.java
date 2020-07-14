package com.test.nordluxdemo.home.setting;

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

public class HomeSettingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ImageView imageView;
    public HomeSettingAdapter(Context mContext) {
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new HomeSettingAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_setting_recycle_item01, parent, false));
            case 1:
                return new HomeSettingAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_setting_recycle_item02, parent, false));

        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
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
