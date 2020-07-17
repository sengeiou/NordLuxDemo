package com.test.nordluxdemo.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.database.entity.Entity;
import com.test.nordluxdemo.home.click.ClickActivity;
import com.test.nordluxdemo.home.moods.MoodsMenuActivity;
import com.test.nordluxdemo.home.setting.HomeSettingActivity;
import com.test.nordluxdemo.program.MenuAdapter;
import com.test.nordluxdemo.setting.room.SettingRoomMenuActivity;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * -1表示item折叠，
     */
    private static int isOpen = -1;
    private static int isClick = -1;
    private Context mContext;

    public HomeAdapter(Context mContext ) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new HomeAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_recycle_item, parent, false));
            case 1:
                return new HomeAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_recycle_item02, parent, false));
            case 2:
                return new HomeAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_recycle_item03, parent, false));
        }
        return new HomeAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        Entity entity=new Entity();
        int i = entity.getLightCount();
        String s = String.valueOf(i);
        ((ViewHolder) holder).lightCount.setText(s);


        /*
        * 房间图标点击开关灯,按钮
        * */
        ((ViewHolder) holder).imgRoomIcon.setOnClickListener(new View.OnClickListener() {
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
            ((ViewHolder) holder).imgRoomIcon.setImageResource(R.mipmap.avatar_bg_home_normal);
            ((ViewHolder) holder).imgRoom.setImageResource(R.mipmap.img_home_myhome_normal);



        } else {
            ((ViewHolder) holder).imgRoomIcon.setImageResource(R.mipmap.avatar_bg_home_pressed);
            ((ViewHolder) holder).imgRoom.setImageResource(R.mipmap.img_home_myhome_selected);
        }


        /*
         * 房间卡片Moods按钮
         * */
        ((ViewHolder) holder).txtMoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MoodsMenuActivity.class);
                mContext.startActivity(intent);
            }
        });


        /*
         * 房间卡片Setting按钮
         * */
        ((ViewHolder) holder).txtSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HomeSettingActivity.class);
                mContext.startActivity(intent);
            }
        });


        /*
         * 房间卡片有灯点击后跳转方法
         * */
        ((ViewHolder) holder).txtClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ClickActivity.class);
                mContext.startActivity(intent);
            }
        });


        /*
         * 房间卡片，滑动伸缩隐藏按钮
         * */
        ((ViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
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
            ((ViewHolder) holder).relativeLayout.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).imageView.setImageResource(R.mipmap.ic_elastic_normal);

        } else {
            ((ViewHolder) holder).relativeLayout.setVisibility(View.GONE);
            ((ViewHolder) holder).imageView.setImageResource(R.mipmap.ic_elastic_normal1);

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
        return 2;
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView  txtMoods, txtSetting, txtClick,lightCount;
        private ImageView imageView, imgRoomIcon,imgRoom;
        private ConstraintLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //灯的数量
            lightCount=itemView.findViewById(R.id.textView2);

            //卡片滑动内容按钮
            imageView = itemView.findViewById(R.id.imageView14);
            relativeLayout = itemView.findViewById(R.id.slide);

            //moods按钮
            txtMoods = itemView.findViewById(R.id.textView54);

            //setting按钮
            txtSetting = itemView.findViewById(R.id.textView56);

            //房间背景图标
            imgRoomIcon = itemView.findViewById(R.id.imageView12);
            //房间图标
            imgRoom=itemView.findViewById(R.id.imageView13);

            //房间名称
            txtClick = itemView.findViewById(R.id.textView);

        }

    }

}
