package com.test.nordluxdemo.program.schedule;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.program.schedule.edit.ScheduleEditActivity;


public class SchedueMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private static int isClick = -1;
    private TextView txtSet;
    private ImageView imgChange;

    public SchedueMenuAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.program_schedule_recycle_item, parent, false));
            case 1:
                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.bridge_recycle_item01, parent, false));

        }
        return null;


    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        //更换图片方法
                        imgChange = holder.itemView.findViewById(R.id.imageView10);
                        imgChange.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isClick == -1) {
                                    imgChange.setImageResource(R.mipmap.switch_on);
                                    isClick = 2;
                                } else {
                                    imgChange.setImageResource(R.mipmap.switch_off);
                                    isClick=-1;
                                }
                            }
                        });
                        /*
                        * 跳转至scheduleEdit方法
                        * */
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, ScheduleEditActivity.class);
                                context.startActivity(intent);
                            }
                        });
                        break;
                    case 1:
                        holder.itemView.findViewById(R.id.imageView24).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(context, "点击了第二个item的图片" + position, Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;
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

            /*
             * 通过itemView跳转到ScheduleEditActivity
             * */
//           itemView.setOnClickListener(new View.OnClickListener() {
////               @Override
////               public void onClick(View v) {
////                   Intent intent=new Intent(context, ScheduleEditActivity.class);
////                   context.startActivity(intent);
////               }
////           });
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return 0;
            case 1:
                return 1;
        }
        return 1;

    }


}
