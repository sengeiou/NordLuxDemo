package com.test.nordluxdemo.home.add.room;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.home.add.product.HomeAddProductActivity;
import com.test.nordluxdemo.home.add.product.HomeAddProductAdapter;

public class HomeAddRoomActivity extends AppCompatActivity {
    private ImageView imgClose;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        initView();
        initListener();
    }
    private void initView() {
        imgClose=findViewById(R.id.imageView29);
        recyclerView=findViewById(R.id.homeAddRoomRecycleView);
        recyclerView.addItemDecoration(new MyDecoration());
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeAddRoomActivity.this));
        recyclerView.setAdapter(new HomeAddRoomAdapter(HomeAddRoomActivity.this));
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