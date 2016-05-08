package com.asat.amesoft.asat.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asat.amesoft.asat.MyApplication;
import com.asat.amesoft.asat.R;

import java.util.ArrayList;

/**
 * Created by Jorge on 7/05/2016.
 */
public class Hos_itemAdapter extends ArrayAdapter<Hospital_Item> {

    public Hos_itemAdapter(Context context, ArrayList<Hospital_Item> values) {
        super(context, R.layout.row_hospital, values);

    }

    class ViewHolder{
        TextView text;
        ImageView icon;
        public ViewHolder(View view) {
            text = (TextView) view.findViewById(R.id.hospital_row_text);
            icon = (ImageView) view.findViewById(R.id.hos_row_image);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.row_hospital, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        Hospital_Item item = getItem(position);

        holder.text.setText(item.getText());
        if(item.getIcon()!=null) {
            holder.icon.setImageBitmap(item.getIcon());
        }

        return view;
    }
}
