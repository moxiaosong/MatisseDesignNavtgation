package com.example.matisse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.matisse.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private Fruit[] fruits = {new Fruit("Apple",R.drawable.apple),new Fruit("Banana",R.drawable.banana),
            new Fruit("Pear",R.drawable.pear),new Fruit("Grape",R.drawable.grape),
            new Fruit("Orange",R.drawable.orange),new Fruit("Watermelon",R.drawable.watermelon),
            new Fruit("Pineapple",R.drawable.pineapple),new Fruit("Strawberry",R.drawable.strawberry),
            new Fruit("Cherry",R.drawable.cherry),new Fruit("Mango",R.drawable.mango)};

    private List<Fruit> fruitList = new ArrayList<>();
    private FruitAdapter adapter;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout,
                binding.toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        binding.toolbar.setNavigationIcon(R.drawable.ic_menu);
        binding.toolbar.setOnClickListener(v ->{
            binding.drawerLayout.openDrawer(GravityCompat.START);
        });

        binding.navView.setCheckedItem(R.id.nav_call);  //调用setCheckedItem()方法将Call菜单设置为默认居中
        binding.navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_call:
                    Toast.makeText(MainActivity.this,"call",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_friends:
                    Toast.makeText(MainActivity.this,"friends",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_location:
                    Toast.makeText(MainActivity.this,"location",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_mail:
                    Toast.makeText(MainActivity.this,"mail",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_task:
                    Toast.makeText(MainActivity.this,"task",Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {  //悬浮按钮的点击事件
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Data deleted",Snackbar.LENGTH_SHORT) //创建一个Snackbar对象
                        .setAction("Undo", new View.OnClickListener() { //设置一个动作
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,"FAB clicked",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        initFruits();
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList);
        binding.recyclerView.setAdapter(adapter);

        binding.swipeRefresh.setColorSchemeColors(R.color.colorPrimary); //设置下拉刷新进度条的颜色
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { //设置下拉刷新的监听器
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.backup:
                Toast.makeText(this,"You cllicked Backup",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"You cllicked Delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this,"You cllicked Setting",Toast.LENGTH_SHORT).show();
                break;
            case R.id.home:
                binding.drawerLayout.openDrawer(GravityCompat.START);
            default:
        }
        return true;
    }

    private void initFruits() {
        fruitList.clear();
        for (int i = 0;i < 50;i++){
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    private void refreshFruits() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initFruits(); //重新生成数据
                adapter.notifyDataSetChanged();  //通知数据发送改变
                binding.swipeRefresh.setRefreshing(false); //表示刷新数据结束并隐藏刷新进度条
            }
        },1000);


//        new Thread(new Runnable() { //子线程进行耗时操作
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2000);  //将线程沉睡2秒
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }
//                runOnUiThread(new Runnable() {  //将线程切换到主线程
//                    @Override
//                    public void run() {
//                        initFruits(); //重新生成数据
//                        adapter.notifyDataSetChanged();  //通知数据发送改变
//                        binding.swipeRefresh.setRefreshing(false); //表示刷新数据结束并隐藏刷新进度条
//                    }
//                });
//            }
//
//
//        });
    }


}