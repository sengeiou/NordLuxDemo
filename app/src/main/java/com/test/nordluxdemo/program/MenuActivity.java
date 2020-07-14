package com.test.nordluxdemo.program;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.test.nordluxdemo.R;
import com.test.nordluxdemo.bridge.BridgeMenuActivity;
import com.test.nordluxdemo.home.HomeActivity;
import com.test.nordluxdemo.romote.RemoteMenuActivity;
import com.test.nordluxdemo.sensor.SensorMenuActivity;
import com.test.nordluxdemo.setting.SettingMenuActivity;
import com.test.nordluxdemo.update.UpdateMenuActivity;

public class MenuActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    /*创建Drawerlayout和Toolbar联动的开关*/
    private ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        //初始化view
        initView();
        /*隐藏滑动条*/
        hideScrollBar();
        /*设置ActionBar*/
        setActionBar();
        /*设置Drawerlayout开关*/
        setDrawerToggle();
        /*设置监听器*/
        setListener();

    }

    private void initView() {
        recyclerView=findViewById(R.id.programRecycleView);
        recyclerView.addItemDecoration(new MyDecoration());
        recyclerView.setLayoutManager(new LinearLayoutManager(MenuActivity.this));
        recyclerView.setAdapter(new MenuAdapter(MenuActivity.this));
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigation_view);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("");
    }
    private void hideScrollBar() {
        navigationView.getChildAt(0).setVerticalScrollBarEnabled(false);
    }
    private void setActionBar() {
        setSupportActionBar(toolbar);
    }
    private void setDrawerToggle() {
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        /*同步drawerlayout的状态*/
        toggle.syncState();
    }
    private void setListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.single_1:
                       Intent intent01=new Intent(MenuActivity.this, HomeActivity.class);
                       startActivity(intent01);
                        finish();
                        break;
                    case R.id.single_2:
//                        Intent intent02=new Intent(MenuActivity.this, MenuActivity.class);
//                        startActivity(intent02);
                        break;
                    case R.id.single_3:
                        Intent intent03=new Intent(MenuActivity.this, BridgeMenuActivity.class);
                        startActivity(intent03);
                        finish();
                        break;
                    case R.id.single_4:
                        Intent intent04=new Intent(MenuActivity.this, SensorMenuActivity.class);
                        startActivity(intent04);
                        finish();
                        break;
                    case R.id.single_5:
                        Intent intent05=new Intent(MenuActivity.this, RemoteMenuActivity.class);
                        startActivity(intent05);
                        finish();
                        break;
                    case R.id.single_6:
//                        Intent intent06=new Intent(MenuActivity.this,HomeActivity.class);
//                        startActivity(intent06);
                        break;
                    case R.id.single_7:
//                        Intent intent07=new Intent(MenuActivity.this,HomeActivity.class);
//                        startActivity(intent07);
                        break;
                    case R.id.single_8:
//                        Intent intent08=new Intent(MenuActivity.this,HomeActivity.class);
//                        startActivity(intent08);
                        break;
                    case R.id.single_9:
                        Intent intent09=new Intent(MenuActivity.this, SettingMenuActivity.class);
                        startActivity(intent09);
                        finish();
                        break;
                    case R.id.single_10:
                        Intent intent10=new Intent(MenuActivity.this, UpdateMenuActivity.class);
                        startActivity(intent10);
                        finish();
                        break;
                    case R.id.single_11:
//                        Intent intent11=new Intent(MenuActivity.this,HomeActivity.class);
//                        startActivity(intent11);
                        finish();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
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