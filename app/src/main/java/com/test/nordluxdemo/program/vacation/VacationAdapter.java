package com.test.nordluxdemo.program.vacation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.nordluxdemo.R;

public class VacationAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context context;

    public VacationAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType){
            case 0:
                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.program_vacation_recycle_item, parent, false));
            case 1:
                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.program_vacation_second_recycle_item, parent, false));
        }
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.program_vacation_second_recycle_item, parent, false));

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
      }
      return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //itemView.findViewById(R.id.)
        }
    }
}
