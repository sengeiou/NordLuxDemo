package com.test.nordluxdemo.program.schedule.edit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.program.schedule.newset.ScheduleNewActivity;
import com.test.nordluxdemo.program.schedule.newset.ScheduleNewAdapter;

public class ScheduleEditActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView imgBack,imgClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_edit);
        initView();
        initListener();
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

    private void initView() {
        imgBack=findViewById(R.id.imageView33);
        imgClose=findViewById(R.id.imageView36);
        recyclerView=findViewById(R.id.RVNewSchedule);
        recyclerView.addItemDecoration(new MyDecoration());
        recyclerView.setLayoutManager(new LinearLayoutManager(ScheduleEditActivity.this));
        recyclerView.setAdapter(new ScheduleEditAdapter(ScheduleEditActivity.this));
    }
}
class MyDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, 30);
    }
}