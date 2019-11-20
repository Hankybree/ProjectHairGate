package com.example.projecthairgate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class StartMenuActivity extends AppCompatActivity {

    List<Integer> listImages = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start_menu);

        initData();

        initHorizontalViewPager();
    }

    private void initData() {
         listImages.add(R.drawable.mainview_bokatid);
         listImages.add(R.drawable.mainview_behandlingar);
         listImages.add(R.drawable.mainview_galleri);
         listImages.add(R.drawable.mainview_kamera);
         listImages.add(R.drawable.mainview_team);
    }

    private void initHorizontalViewPager() {
        HorizontalInfiniteCycleViewPager pager = findViewById(R.id.horizontal_cycle);
        MyAdapter adapter = new MyAdapter(listImages,getBaseContext());
        pager.setAdapter(adapter);
    }
}
