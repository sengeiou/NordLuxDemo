package com.test.nordluxdemo.program.schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.home.HomeActivity;
import com.test.nordluxdemo.program.MenuActivity;
import com.test.nordluxdemo.program.schedule.newset.ScheduleNewActivity;

/*
* Schedule主页面
*
* */
public class ScheduleMenuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView imgBack,imgAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_menu);
        initView();
        initListener();

    }

    private void initView() {
        recyclerView=findViewById(R.id.scheduleMenu);
        imgBack=findViewById(R.id.imageView15);
        imgAdd=findViewById(R.id.imageView8);
        recyclerView.addItemDecoration(new MyDecoration());
        recyclerView.setLayoutManager(new LinearLayoutManager(ScheduleMenuActivity.this));
        recyclerView.setAdapter(new SchedueMenuAdapter(ScheduleMenuActivity.this));
    }

    private void initListener() {
        //返回操作
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(ScheduleMenuActivity.this, MenuActivity.class);
//                startActivity(intent);
                //ScheduleMenuActivity.this.finish();
                finish();
            }
        });
        //点击按钮进入添加schedule页面
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ScheduleMenuActivity.this, ScheduleNewActivity.class);
                startActivity(intent);
            }
        });


    }
}
class MyDecoration extends RecyclerView.ItemDecoration{
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0,0,0,30);
    }
}