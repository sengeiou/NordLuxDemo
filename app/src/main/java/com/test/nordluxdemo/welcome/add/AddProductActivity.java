package com.test.nordluxdemo.welcome.add;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.home.HomeActivity;

public class AddProductActivity extends AppCompatActivity {
    private TextView toLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initView();
        initListener();
    }

    private void initView() {
        toLogin=findViewById(R.id.textView48);
    }

    private void initListener() {
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddProductActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}