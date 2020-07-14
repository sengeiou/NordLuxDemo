package com.test.nordluxdemo.sensor.edit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.program.schedule.SchedueMenuAdapter;
import com.test.nordluxdemo.program.schedule.ScheduleMenuActivity;

public class SensorEditActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_edit);
        initView();
        initListener();
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
    }

    private void initView() {
        recyclerView = findViewById(R.id.scheduleMenu);
        imgBack = findViewById(R.id.imageView15);
        recyclerView.addItemDecoration(new MyDecoration());
        recyclerView.setLayoutManager(new LinearLayoutManager(SensorEditActivity.this));
        recyclerView.setAdapter(new SensorEditAdapter(SensorEditActivity.this));
    }
}

class MyDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, 30);
    }
}