package com.test.nordluxdemo.program.timer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.program.timer.edit.TimerEditActivity;

public class TimerMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ImageView imgChange;
    private Context context;
    private static int siClick;

    public TimerMenuAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.program_timer_recycle_item, parent, false));
        } else {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.program_timer_recycle_item, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        imgChange = holder.itemView.findViewById(R.id.imageView10);
        imgChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (siClick == -1) {
                    imgChange.setImageResource(R.mipmap.switch_on);
                    siClick = -2;
                } else {
                    imgChange.setImageResource(R.mipmap.switch_off);
                    siClick = -1;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TimerEditActivity.class);
                    context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
