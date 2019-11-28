package com.example.projecthairgate;

import android.util.Log;

public class GalleryRows {

    private String img;

    public GalleryRows(String img) {
        this.img = img;
    }

    public String getImg() {
        Log.d("frank", "getImg: hämtas");
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
