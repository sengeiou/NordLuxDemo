package com.test.nordluxdemo.home.add.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.home.add.product.HomeAddProductAdapter;

public class HomeAddGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    public HomeAddGroupAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new HomeAddGroupAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_add_group_recycle_item01, parent, false));
            case 1:
                return new HomeAddGroupAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.moods_recycle_item02, parent, false));

        }
          return new HomeAddGroupAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_add_product_recycle_item01, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
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
