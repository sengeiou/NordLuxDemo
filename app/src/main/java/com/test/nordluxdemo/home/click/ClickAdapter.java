package com.test.nordluxdemo.home.click;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.home.HomeAdapter;

public class ClickAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * -1表示item折叠，
     */
    private static int isClick = -1;
    private static int isOpen = -1;
    private Context mContext;
    public ClickAdapter(Context mContext) {
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new ClickAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_click_recycle_item02, parent, false));
            case 1:
                return new ClickAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_click_recycle_item02, parent, false));
            //case 2:
              //  return new ClickAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_click_recycle_item03, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        /*
         * 房间图标点击开关灯,按钮
         * */
        ((ClickAdapter.ViewHolder) holder).imgRoomIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,"开关灯点击了"+position,Toast.LENGTH_SHORT).show();
                if (isClick == position) {
                    //当点击的item已经被展开了, 就关闭.
                    //Log.e(TAG, "onClick1: "+ isOpen);
                    isClick = -1;
                    notifyItemChanged(position);
                } else {
                    int oldisOpen = isClick;
                    //Log.e(TAG, "onClick2: "+ isOpen);
                    isClick = position;
                    //Log.e(TAG, "onClick3: "+ isOpen);
                    notifyItemChanged(oldisOpen);
                    notifyItemChanged(isClick);
                }
            }
        });
        /*
         * 房间图标点击开关灯,具体实现
         * */
        if (isClick == position) {
            ((ClickAdapter.ViewHolder) holder).imgRoomIcon.setImageResource(R.mipmap.avatar_bg_home_normal);

        } else {
            ((ClickAdapter.ViewHolder) holder).imgRoomIcon.setImageResource(R.mipmap.avatar_bg_home_pressed);

        }




        /*
         * 房间卡片，滑动伸缩隐藏按钮
         * */
        ((ClickAdapter.ViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mListenter.onClick(position);
                if (isOpen == position) {
                    //当点击的item已经被展开了, 就关闭.
                    //Log.e(TAG, "onClick1: "+ isOpen);
                    isOpen = -1;
                    notifyItemChanged(position);
                } else {
                    int oldisOpen = isOpen;
                    //Log.e(TAG, "onClick2: "+ isOpen);
                    isOpen = position;
                    //Log.e(TAG, "onClick3: "+ isOpen);
                    notifyItemChanged(oldisOpen);
                    notifyItemChanged(isOpen);
                }


            }
        });
        /*
         * 房间卡片，滑动伸缩隐藏具体实现
         * */
        if (isOpen == position) {
            ((ClickAdapter.ViewHolder) holder).relativeLayout.setVisibility(View.VISIBLE);
            ((ClickAdapter.ViewHolder) holder).imageView.setImageResource(R.mipmap.ic_elastic_normal);

        } else {
            ((ClickAdapter.ViewHolder) holder).relativeLayout.setVisibility(View.GONE);
            ((ClickAdapter.ViewHolder) holder).imageView.setImageResource(R.mipmap.ic_elastic_normal1);

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout relativeLayout;
        private ImageView imageView,imgRoomIcon;
        public ViewHolder(View itemView) {
            super(itemView);
            //卡片滑动内容按钮
            imageView = itemView.findViewById(R.id.imageView14);
            relativeLayout = itemView.findViewById(R.id.slide);

            //房间图标
            imgRoomIcon = itemView.findViewById(R.id.imageView12);
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
