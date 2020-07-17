package com.test.nordluxdemo.home;

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
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import com.test.nordluxdemo.R;
import com.test.nordluxdemo.ble.BleMainActivity;
import com.test.nordluxdemo.ble.mesh.MeshMainActivity;
import com.test.nordluxdemo.ble.mesh.MeshScanActivity;
import com.test.nordluxdemo.bridge.BridgeMenuActivity;
import com.test.nordluxdemo.home.add.group.HomeAddGroupActivity;
import com.test.nordluxdemo.home.add.product.HomeAddProductActivity;
import com.test.nordluxdemo.home.add.room.HomeAddRoomActivity;
import com.test.nordluxdemo.program.MenuActivity;
import com.test.nordluxdemo.program.click.ProgramClickActivity;
import com.test.nordluxdemo.romote.RemoteMenuActivity;
import com.test.nordluxdemo.sensor.SensorMenuActivity;
import com.test.nordluxdemo.setting.SettingMenuActivity;
import com.test.nordluxdemo.update.UpdateMenuActivity;

/*
* 主页homeActivity
*
* */
public class HomeActivity extends AppCompatActivity {
    public View viewPopu,viewSelect;
    private PopupWindow mPopupWindow;
    private RecyclerView mRvHome;
    private ImageView imgBlueTooth, imgAdd,imgRoomSelect;
    public DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView txtAddProduct, txtAddGroup, txtAddRoom;
    private static int isclick=-1;
    /*创建Drawerlayout和Toolbar联动的开关*/
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /*
        * 关闭顶部状态栏
        * */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // Translucent navigation bar
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
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
        //事件
        initListenr();

    }

    /*设置ActionBar*/
    private void setActionBar() {
        setSupportActionBar(toolbar);
        /*显示Home图标*/
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /*设置Drawerlayout的开关,并且和Home图标联动*/
    private void setDrawerToggle() {
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        /*同步drawerlayout的状态*/
        toggle.syncState();

    }

    /*设置监听器*/
    private void setListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.single_1:
//                        Intent intent01=new Intent(HomeActivity.this, HomeActivity.class);
//                        startActivity(intent01);
                        break;
                    case R.id.single_2:
                        Intent intent02 = new Intent(HomeActivity.this, MenuActivity.class);
                        startActivity(intent02);
                        break;
                    case R.id.single_3:
                        Intent intent03 = new Intent(HomeActivity.this, BridgeMenuActivity.class);
                        startActivity(intent03);
                        break;
                    case R.id.single_4:
                        Intent intent04 = new Intent(HomeActivity.this, SensorMenuActivity.class);
                        startActivity(intent04);
                        break;
                    case R.id.single_5:
                        Intent intent05 = new Intent(HomeActivity.this, RemoteMenuActivity.class);
                        startActivity(intent05);
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
                        Intent intent09 = new Intent(HomeActivity.this, SettingMenuActivity.class);
                        startActivity(intent09);
                        break;
                    case R.id.single_10:
                        Intent intent10 = new Intent(HomeActivity.this, UpdateMenuActivity.class);
                        startActivity(intent10);
                        break;
                    case R.id.single_11:
//                        Intent intent11=new Intent(MenuActivity.this,HomeActivity.class);
//                        startActivity(intent11);
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    /*去掉navigation中的滑动条*/
    private void hideScrollBar() {
        navigationView.getChildAt(0).setVerticalScrollBarEnabled(false);
    }

    private void initListenr() {
        /*
        * 添加产品，组，房间popuwindow方法
        * */
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setmPopupWindow();
            }
        });
        /*
        * 选择房间弹窗popuwindow方法
        * */
        imgRoomSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectPopuWind();
            }
        });
        /*
        * 蓝牙按钮监听事件
        * */

        imgBlueTooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isclick==-1){
                    imgBlueTooth.setImageResource(R.mipmap.ic_nav_bluetooth_on);
                    isclick=-2;
                }else {
                    imgBlueTooth.setImageResource(R.mipmap.ic_bluetooth_off);
                    isclick=-1;
                }
//                switch (isclick){
//                    case -1:
//                        imgBlueTooth.setImageResource(R.mipmap.ic_nav_bluetooth_on);
//                        //isclick==-2;
//                }
//                imgBlueTooth.setImageResource(R.mipmap.ic_bluetooth_off);
            }
        });
    }

    private void initView() {
        mRvHome = findViewById(R.id.homeRecycleView);
        imgBlueTooth = findViewById(R.id.imgBlueTooth);
        imgAdd = findViewById(R.id.imgNavAdd);
        imgRoomSelect=findViewById(R.id.imageView35);
        mRvHome.addItemDecoration(new MyDecoration());
        mRvHome.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        mRvHome.setAdapter(new HomeAdapter(HomeActivity.this));
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");

    }

    /*
    * 设置选择房间弹窗popuWindow方法
    * */
    public void setSelectPopuWind() {
        viewSelect = getLayoutInflater().inflate(R.layout.layout_pop_home_select, null);
        mPopupWindow = new PopupWindow(viewSelect, imgRoomSelect.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        //初始化popuWindow添加灯泡，组，房间
        //initViewPopuItem();
        //popuWindow添加灯泡，组，房间点击事件
        //initListenerPopuItem();
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);//设置点击外部区域可以取消popupWindow
        mPopupWindow.showAsDropDown(imgRoomSelect, 0, 0);//设置popupWindow显示,并且告诉它显示在那个View下面
    }

    /*
     * 设置添加灯泡，组，房间popuWindow方法
     * */
    public void setmPopupWindow() {
        viewPopu = getLayoutInflater().inflate(R.layout.layout_pop_home_add, null);
        mPopupWindow = new PopupWindow(viewPopu, imgAdd.getWidth() + imgAdd.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        //初始化popuWindow添加灯泡，组，房间
        initViewPopuItem();
        //popuWindow添加灯泡，组，房间点击事件
        initListenerPopuItem();
        mPopupWindow.setFocusable(true);//再次点击关闭
        mPopupWindow.setOutsideTouchable(true);//设置点击外部区域可以取消popupWindow
        mPopupWindow.showAsDropDown(imgAdd, -100, 0);//设置popupWindow显示,并且告诉它显示在那个View下面
    }

    /*
     * //初始化popuWindow添加灯泡，组，房间
     * */
    private void initViewPopuItem() {
        txtAddProduct = viewPopu.findViewById(R.id.textView25);
        txtAddGroup = viewPopu.findViewById(R.id.textView29);
        txtAddRoom = viewPopu.findViewById(R.id.textView30);
    }

    /*
     * popuWindow添加灯泡，组，房间点击事件
     *
     * */
    private void initListenerPopuItem() {

        txtAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomeActivity.this, "点击添加灯泡", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(HomeActivity.this, MeshScanActivity.class);
                startActivity(intent);

            }
        });
        txtAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomeActivity.this, "点击添加组", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(HomeActivity.this, HomeAddGroupActivity.class);
                startActivity(intent);

            }
        });
        txtAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(HomeActivity.this, "点击添加房间", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(HomeActivity.this, HomeAddRoomActivity.class);
                startActivity(intent);

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