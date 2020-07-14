package com.test.nordluxdemo.program;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.home.HomeAdapter;
import com.test.nordluxdemo.program.schedule.ScheduleMenuActivity;
import com.test.nordluxdemo.program.timer.TimerMenuActivity;
import com.test.nordluxdemo.program.vacation.VacationActivity;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;


    public MenuAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case 0:
                return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.program_recycle_item01, parent, false));
            case 1:
                return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.program_recycle_item02, parent, false));
            case 2:
                return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.program_recycle_item03, parent, false));
        }
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.program_recycle_item01, parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(mContext, ScheduleMenuActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(mContext, TimerMenuActivity.class);
                        mContext.startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(mContext, VacationActivity.class);
                        mContext.startActivity(intent2);
                        break;
                }
                //Toast.makeText(mContext,"点击"+position,Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
        }
        return 2;

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
