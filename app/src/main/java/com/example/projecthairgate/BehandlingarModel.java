package com.example.projecthairgate;

public class BehandlingarModel {

    private int image;
    private int imageTxt;

    public BehandlingarModel(int image, int imageTxt) {
        this.image = image;
        this.imageTxt = imageTxt;
    }

    public int getImage() {
        return image;
    }

    public int getImageTxt() {
        return imageTxt;
    }
}
