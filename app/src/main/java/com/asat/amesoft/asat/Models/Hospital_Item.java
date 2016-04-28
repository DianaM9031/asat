package com.asat.amesoft.asat.Models;

import android.graphics.Bitmap;

/**
 * Created by Jorge on 28/04/2016.
 */
public class Hospital_Item {
    private Bitmap icon;
    private String text;

    public Hospital_Item(String text, Bitmap icon) {
        this.text = text;
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }
}
