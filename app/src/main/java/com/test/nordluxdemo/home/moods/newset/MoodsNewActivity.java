package com.test.nordluxdemo.home.moods.newset;

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

public class MoodsNewActivity extends AppCompatActivity {
    private ImageView imgClose,imgBack;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moods_new);
        initView();
        initListener();
    }

    private void initListener() {
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {
        imgClose=findViewById(R.id.imageView29);
        imgBack=findViewById(R.id.imageView96);
        recyclerView=findViewById(R.id.moodsNewRecycleView);
        recyclerView.addItemDecoration(new MyDecoration());
        recyclerView.setLayoutManager(new LinearLayoutManager(MoodsNewActivity.this));
        recyclerView.setAdapter(new MoodsNewAdapter(MoodsNewActivity.this));
    }
}
class MyDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, 30);
    }
}