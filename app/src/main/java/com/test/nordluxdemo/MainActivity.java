package com.test.nordluxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.test.nordluxdemo.home.HomeActivity;
import com.test.nordluxdemo.login.LoginActivity;
import com.test.nordluxdemo.program.MenuActivity;
import com.test.nordluxdemo.program.schedule.ScheduleMenuActivity;
import com.test.nordluxdemo.program.timer.TimerMenuActivity;
import com.test.nordluxdemo.program.vacation.VacationActivity;
import com.test.nordluxdemo.welcome.GuideActivity;

import java.util.Timer;
import java.util.TimerTask;
/*
* 首页
* */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置时间，1秒后进入引导页面GuideActivity
        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this, GuideActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        };
        timer.schedule(timerTask,1000*1);

    }
}