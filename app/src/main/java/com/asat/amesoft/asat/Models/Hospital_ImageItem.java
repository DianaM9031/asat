package com.asat.amesoft.asat.Models;

import android.graphics.Bitmap;

/**
 * Created by Jorge on 8/05/2016.
 */
public class Hospital_ImageItem {

    private Bitmap image;
    private String description;

    public Hospital_ImageItem(Bitmap image, String description) {
        this.image = image;
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
