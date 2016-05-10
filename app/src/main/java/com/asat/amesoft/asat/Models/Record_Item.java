package com.asat.amesoft.asat.Models;

import java.util.Date;

/**
 * Created by Jorge on 10/05/2016.
 */
public class Record_Item {

    private String id;
    private String title;
    private String date;

    public Record_Item(String id, String title, String date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
