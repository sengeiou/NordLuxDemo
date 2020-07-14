package com.test.nordluxdemo.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.login.LoginActivity;
import com.test.nordluxdemo.welcome.add.AddProductActivity;
/*
* 引导页面，选择使用蓝牙或使用网关
* */
public class GuideActivity extends AppCompatActivity {

    private View blueTooth,bridge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //初始化控件（找到每个控件id，并赋值到本地控件）
        initView();
        //控件添加单机事件
        initListener();
    }

    private void initView() {
       blueTooth= findViewById(R.id.view5);
       bridge= findViewById(R.id.view6);

    }

    private void initListener() {
        //蓝牙按钮，进入添加产品页面
        blueTooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GuideActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });
        //网关按钮，进入登录界面
        bridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten=new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(inten);
            }
        });

    }
}