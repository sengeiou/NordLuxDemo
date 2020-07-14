package com.test.nordluxdemo.setting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.home.HomeActivity;
import com.test.nordluxdemo.sensor.SensorMenuAdapter;
import com.test.nordluxdemo.setting.room.SettingRoomMenuActivity;

public class SettingMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private static int isClick=-1;
    private ImageView imgChange;
    public SettingMenuAdapter(Context mContext) {
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new SettingMenuAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.setting_recycle_item01, parent, false));
            case 1:
                return new SettingMenuAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.setting_recycle_item02, parent, false));
            case 2:
                return new SettingMenuAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.setting_recycle_item03, parent, false));

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
                        holder.itemView.findViewById(R.id.imageView26).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(mContext, SettingRoomMenuActivity.class);
                                mContext.startActivity(intent);
                            }
                        });
                        holder.itemView.findViewById(R.id.textView23).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(mContext, SettingRoomMenuActivity.class);
                                mContext.startActivity(intent);
                            }
                        });

                        break;
                    case 1:
                        imgChange= holder.itemView.findViewById(R.id.imageView27);
                        imgChange.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isClick==-1){
                                    imgChange.setImageResource(R.mipmap.switch_on);
                                    isClick=-2;
                                }else {
                                    imgChange.setImageResource(R.mipmap.switch_off);
                                    isClick=-1;
                                }
                            }
                        });

                        break;
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return 3;
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
