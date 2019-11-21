package com.example.projecthairgate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class MyAdapter extends PagerAdapter {

    List<Integer> listImages;
    Context context;
    LayoutInflater layoutInflater;


    public MyAdapter(List<Integer> listImages, Context context) {
        this.listImages = listImages;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.card_item,container,false);
        ImageButton imageButton = view.findViewById(R.id.imageButton);
        imageButton.setImageResource(listImages.get(position));
        Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();


        container.addView(view);
        return view;
    }
}
