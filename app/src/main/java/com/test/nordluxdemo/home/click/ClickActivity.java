package com.test.nordluxdemo.home.click;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.setting.room.SettingRoomMenuActivity;
import com.test.nordluxdemo.setting.room.SettingRoomMenuAdapter;

public class ClickActivity extends AppCompatActivity {
    private ImageView imgClose, imgAdd;
    private PopupWindow mPopupWindow;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);
        initView();
        initListener();
    }

    private void initListener() {
        //返回按钮
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //添加product，group，room按钮
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setmPopupWindow();
            }


        });
    }

    private void initView() {
        imgClose = findViewById(R.id.imageView44);
        imgAdd = findViewById(R.id.imageView46);
        recyclerView = findViewById(R.id.clickRoomRecv);
        recyclerView.addItemDecoration(new MyDecoration());
        recyclerView.setLayoutManager(new LinearLayoutManager(ClickActivity.this));
        recyclerView.setAdapter(new ClickAdapter(ClickActivity.this));
    }

    /*
     * 设置popuwindow方法
     * */
    private void setmPopupWindow() {
        View view = getLayoutInflater().inflate(R.layout.layout_pop_home_add, null);
        mPopupWindow = new PopupWindow(view, imgAdd.getWidth()+imgAdd.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(true);//设置点击外部区域可以取消popupWindow
        mPopupWindow.showAsDropDown(imgAdd,-100,0);//设置popupWindow显示,并且告诉它显示在那个View下面
    }


}

class MyDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, 30);
    }
}