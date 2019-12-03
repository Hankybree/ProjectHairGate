package com.example.projecthairgate;

import android.content.Context;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class BehandlingarAdapter extends PagerAdapter {

    private List<BehandlingarModel> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public BehandlingarAdapter(List<BehandlingarModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.behandlingar_item, container, false);

        ImageView behandlingarSkagg;
        ImageView textViewSkagg;

        behandlingarSkagg = view.findViewById(R.id.behandlingarSkagg);
        textViewSkagg = view.findViewById(R.id.textViewSkagg);

        behandlingarSkagg.setImageResource(models.get(position).getImage());
        textViewSkagg.setImageResource(models.get(position).getImageTxt());

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
