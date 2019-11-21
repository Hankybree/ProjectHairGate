package com.example.projecthairgate;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class MyAdapter extends PagerAdapter {

    List<Integer> listImages;
    List<String> listTitle;
    Context context;
    LayoutInflater layoutInflater;


    public MyAdapter(List<Integer> listImages,List<String> listTitle, Context context) {
        this.listImages = listImages;
        this.context = context;
        this.listTitle = listTitle;
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
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = layoutInflater.inflate(R.layout.card_item,container,false);

        final ImageButton imageButton = view.findViewById(R.id.imageButton);
        TextView textView = view.findViewById(R.id.on_card_text);

        //Sets image and title based on position
        imageButton.setImageResource(listImages.get(position));
        textView.setText(listTitle.get(position));

        /*
        OnClick fixad. Skapa alla klasser, sen gör en lista med intents för att skicka till rätt med
        hjälp av position
        */
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked "+listTitle.get(position), Toast.LENGTH_SHORT).show();
            }
        });


        container.addView(view);
        return view;
    }
}
