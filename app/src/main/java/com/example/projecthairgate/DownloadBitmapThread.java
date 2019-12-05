package com.example.projecthairgate;

import android.graphics.Bitmap;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class DownloadBitmapThread extends Thread {

    String URL;
    GalleryRows galleryRows;
    Bitmap bitmap;

    public DownloadBitmapThread(String URL, GalleryRows galleryRows) {
        this.URL = URL;
        this.galleryRows = galleryRows;
    }

    public void run() {
        try {
            bitmap = Picasso.get().load(URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        galleryRows.setBitmap(bitmap);
    }
}
