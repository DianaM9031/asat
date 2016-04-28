package com.asat.amesoft.asat.Tools;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.asat.amesoft.asat.R;

import java.util.ArrayList;

/**
 * Created by Jorge on 27/04/2016.
 */

class MenuItem{
    int iconId;
    String name;
    MenuItem(int iconId,String name){
        this.iconId=iconId;
        this.name=name;
    }
}

public class MenuAdapter extends BaseAdapter {

    ArrayList<MenuItem> menuItems;

    public MenuAdapter(Context context){
        menuItems = new ArrayList<>();
        String[] temp = context.getResources().getStringArray(R.array.menu_options);
        int [] icons = {R.drawable.hospital,R.drawable.history,R.drawable.advises,R.drawable.notifications,R.drawable.configure,R.drawable.about};
        for(int i=0; i<temp.length;i++){
            menuItems.add(new MenuItem(icons[i],temp[i]));
        }
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
