package com.example.projecthairgate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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

        ImageView imageView = view.findViewById(R.id.imageButton);
        //TextView textView = view.findViewById(R.id.on_card_text);

        //Sets image and title based on position
        imageView.setImageResource(listImages.get(position));
        //textView.setText(listTitle.get(position));

        /*
        OnClick fixad. Skapa alla klasser, sen gör en lista med intents för att skicka till rätt med
        hjälp av position
        */

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position) {
                    case 0:
                        view.getContext().startActivity(new Intent(view.getContext(),BookingActivity.class));
                        //int id = view.getId();
                        break;
                    case 1:
                        view.getContext().startActivity(new Intent(view.getContext(),BehandlingarAcivity.class));
                        break;
                    case 2:
                        view.getContext().startActivity(new Intent(view.getContext(),GalleryActivity.class));
                        break;
                    case 3:
                        view.getContext().startActivity(new Intent(view.getContext(),VartTeamActivity.class));
                        break;
                    /*case 4:
                        view.getContext().startActivity(new Intent(view.getContext(),AboutActivity.class));
                        break;*/
                }

            }
        });




        container.addView(view);
        return view;
    }



}
