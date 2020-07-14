package com.test.nordluxdemo.program.schedule.newset;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.test.nordluxdemo.R;




public class ScheduleNewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView imgBack,imgClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_new);
        initView();
        initListener();
    }

    private void initView() {
        imgBack=findViewById(R.id.imageView33);
        imgClose=findViewById(R.id.imageView36);
        recyclerView=findViewById(R.id.RVNewSchedule);
        recyclerView.addItemDecoration(new MyDecoration());
        recyclerView.setLayoutManager(new LinearLayoutManager(ScheduleNewActivity.this));
        recyclerView.setAdapter(new ScheduleNewAdapter(ScheduleNewActivity.this));
    }

    private void initListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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