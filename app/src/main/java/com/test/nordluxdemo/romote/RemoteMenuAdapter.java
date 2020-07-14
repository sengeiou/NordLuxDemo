package com.test.nordluxdemo.romote;

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
import com.test.nordluxdemo.romote.edit.RemoteEditActivity;

public class RemoteMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private static int isOpen=-1;
    private ImageView imgRemoteBG,imgRemote;
    public RemoteMenuAdapter(Context mContext) {
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new RemoteMenuAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.remote_recycle_item01, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        imgRemoteBG=holder.itemView.findViewById(R.id.imageView81);
        imgRemote=holder.itemView.findViewById(R.id.imageView4);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, RemoteEditActivity.class);
                mContext.startActivity(intent);
            }
        });
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isOpen==-1){
//                    isOpen=-2;
//                    imgRemote.setImageResource(R.mipmap.img_remote_blue);
//                    imgRemoteBG.setImageResource(R.mipmap.avatar_bg_remote);
//                }else {
//                    imgRemote.setImageResource(R.mipmap.img_remote_blue);
//                    imgRemoteBG.setImageResource(R.mipmap.avatar_bg_remote);
//                    isOpen=-1;
//                }
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return 1;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
