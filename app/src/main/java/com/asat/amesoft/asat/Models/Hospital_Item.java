package com.asat.amesoft.asat.Models;

import android.graphics.Bitmap;

/**
 * Created by Jorge on 28/04/2016.
 */
public class Hospital_Item {
    private String icon;
    private String text;

    public Hospital_Item(String text, String icon) {
        this.icon = icon;
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
