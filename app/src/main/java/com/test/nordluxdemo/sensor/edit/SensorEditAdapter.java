package com.test.nordluxdemo.sensor.edit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.nordluxdemo.R;

public class SensorEditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ImageView imgChange;
    private static int isClick=-1;

    public SensorEditAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==0) {
            return new SensorEditAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.sensor_edit_recycle_item01, parent, false));
        }else {
            return new SensorEditAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.sensor_edit_recycle_item02, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        imgChange=holder.itemView.findViewById(R.id.imageView58);
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
                                //Toast.makeText(context,"点击了按钮", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case 1:
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return 2;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position%2==0){
            return 0;
        }else {
            return 1;
        }
    }
}
