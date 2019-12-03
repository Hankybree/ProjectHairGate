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

    public void setImage(int image) {
        this.image = image;
    }

    public int getImageTxt() {
        return imageTxt;
    }

    public void setText(int imageTxt) {
        this.imageTxt = imageTxt;
    }
}
