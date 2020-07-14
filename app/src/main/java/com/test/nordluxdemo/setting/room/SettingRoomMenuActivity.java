package com.test.nordluxdemo.setting.room;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.home.moods.MoodsMenuActivity;
import com.test.nordluxdemo.home.moods.MoodsMenuAdapter;
import com.test.nordluxdemo.setting.SettingMenuAdapter;

public class SettingRoomMenuActivity extends AppCompatActivity {
    private ImageView imgClose;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_room);
        initView();
        initListener();
    }
    private void initView() {
        imgClose=findViewById(R.id.imageView44);
        recyclerView=findViewById(R.id.settingRoomRecv);
        recyclerView.addItemDecoration(new MyDecoration());
        recyclerView.setLayoutManager(new LinearLayoutManager(SettingRoomMenuActivity.this));
        recyclerView.setAdapter(new SettingRoomMenuAdapter(SettingRoomMenuActivity.this));
    }

    private void initListener() {
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
class MyDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, 30);
    }
}