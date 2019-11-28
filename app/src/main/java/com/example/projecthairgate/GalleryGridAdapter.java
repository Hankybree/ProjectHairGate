package com.example.projecthairgate;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GalleryGridAdapter extends RecyclerView.Adapter<GalleryGridAdapter.ImageViewHolder> {

    Context mContext;
    List<GalleryRows> mData;
    //ImageView img;

    public GalleryGridAdapter(Context mContext, List<GalleryRows> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gallery_row_item, parent,false);

        Log.d("frank", "Adapter oncreate körs");

        return new ImageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        //holder.img.setImageResource(mData.get(position).getImg());

        Picasso.get().load(mData.get(position).getImg()).into(holder.img);

        holder.img.setImageDrawable(holder.img.getDrawable());

        Log.d("frank", "Hallå");

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.row_img);
            Log.d("frank", "det funkar");
        }
    }
}
