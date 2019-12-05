package com.example.projecthairgate;

import android.graphics.Bitmap;

public class GalleryRows {

    private String img;
    private Bitmap bitmap;

    public GalleryRows(String img) {
        this.img = img;

        DownloadBitmapThread dbt = new DownloadBitmapThread(img, this);

        dbt.start();
    }

    public String getImg() {
        return img;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
