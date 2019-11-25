package com.example.projecthairgate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class StartMenuActivity extends AppCompatActivity {

    List<Integer> listImages = new ArrayList<>();
    List<String> listTitle = new ArrayList<>();

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
         listImages.add(R.drawable.mainview_nyteam);

         listTitle.add("BOKA TID");
         listTitle.add("BEHANDLINGAR");
         listTitle.add("GALLERI");
         listTitle.add("KAMERA");
         listTitle.add("VÅRT TEAM");

        Log.d("jakob", "initData successful");
    }



    private void initHorizontalViewPager() {
        HorizontalInfiniteCycleViewPager pager = findViewById(R.id.horizontal_cycle);
        //onswipe YoYo shadow fade-in fade-out
        MyAdapter adapter = new MyAdapter(listImages, listTitle,getBaseContext());
        pager.setAdapter(adapter);
    }



}
