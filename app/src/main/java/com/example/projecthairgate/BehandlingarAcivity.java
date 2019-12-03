package com.example.projecthairgate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.text.Layout;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;

public class BehandlingarAcivity extends AppCompatActivity {

    ViewPager viewPager;
    BehandlingarAdapter adapter;
    List<BehandlingarModel> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behandlingar);

        models = new ArrayList<>();
        models.add(new BehandlingarModel(R.drawable.klipp_farg_bild, R.drawable.klipp_farg_txtbild));
        models.add(new BehandlingarModel(R.drawable.bearded_man, R.drawable.barbershop_txtbild));
        models.add(new BehandlingarModel(R.drawable.brud_bal_bild, R.drawable.brud_bal_txtbild));
        models.add(new BehandlingarModel(R.drawable.naglar_bild, R.drawable.naglar_txtbild));
        models.add(new BehandlingarModel(R.drawable.blowdry_bild, R.drawable.blowdry_txtbild));

        adapter = new BehandlingarAdapter(models, this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        Integer[] colors_temp = {
                getResources().getColor(R.color.colorWhite),
                getResources().getColor(R.color.colorWhite)
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (adapter.getCount() -1 ) && position < (colors.length -1))   {
                    viewPager.setBackgroundColor(

                            (Integer)argbEvaluator.evaluate(
                                            positionOffset,
                                            colors[position],
                                            colors[position +1])
                    );
                } else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
