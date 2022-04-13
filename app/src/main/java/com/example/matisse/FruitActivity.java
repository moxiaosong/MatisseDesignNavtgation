package com.example.matisse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.matisse.databinding.ActivityFruitBinding;

public class FruitActivity extends AppCompatActivity {
    private ActivityFruitBinding binding;
    public static final String FRUIT_NAME = "fruit_name";
    public static final String FRUIT_IMAGE_ID ="fruit_image_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFruitBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        Intent intent =getIntent();
        String fruitName = intent.getStringExtra(FRUIT_NAME);
        int fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID,0);
        Log.e("###", "fruitImageId: " + fruitImageId);
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar(); //使用系统默认的返回按钮
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
//        binding.toolbar.setNavigationIcon(R.drawable.ic_back); //设置一个返回按钮
//        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.collapsingToolbar.setTitle(fruitName); //将水果名设置成当前界面的标题
        binding.collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        Glide.with(this).load(fruitImageId).into(binding.fruitImageView); //使用Glide加载传入的水果图片
        String fruitContent = generateFruitContent(fruitName);
        binding.fruitContentText.setText(fruitContent);

    }

    private String generateFruitContent(String fruitName) {
        StringBuilder fruitContent = new StringBuilder();
        for (int i = 0;i <= 500;i++){
            fruitContent.append(fruitName + " ");
        }
        return fruitContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}