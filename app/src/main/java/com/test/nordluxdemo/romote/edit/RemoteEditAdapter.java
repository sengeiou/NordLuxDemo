package com.test.nordluxdemo.romote.edit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.program.MenuAdapter;

public class RemoteEditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;


    public RemoteEditAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case 0:
                return new RemoteEditAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.remote_edit_recycle_item01, parent, false));
            case 1:
                return new RemoteEditAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.remote_edit_recycle_item02, parent, false));
            case 2:
                return new RemoteEditAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.program_recycle_item03, parent, false));
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

