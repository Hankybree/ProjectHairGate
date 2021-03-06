package com.example.projecthairgate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GalleryGridAdapter extends RecyclerView.Adapter<GalleryGridAdapter.ImageViewHolder> {

    private Context mContext;
    private List<GalleryRows> mData;
    private ImageView selectedImage;
    private int positionOfDbPics;
    private ImageView iv;

    public GalleryGridAdapter(Context mContext, List<GalleryRows> mData, ImageView iv) {
        this.mContext = mContext;
        this.mData = mData;
        this.iv = iv;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gallery_row_item, parent,false);

        return new ImageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, final int position) {

        Picasso.get().load(mData.get(position).getImg()).into(holder.img);

        // Väljer en image ur recyclerviewen (För att kunna använda vid faceswap)
        selectedImage = holder.itemView.findViewById(R.id.row_img);

        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                positionOfDbPics = position;
                iv.setImageBitmap(mData.get(position).getBitmap());
            }
        });
    }

    public int getPositionOfDbPics() {
        return positionOfDbPics;
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
        }
    }


}
