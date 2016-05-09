package com.asat.amesoft.asat.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asat.amesoft.asat.R;

import java.util.ArrayList;

/**
 * Created by Jorge on 8/05/2016.
 */
public class Hos_ImageItemAdapter extends ArrayAdapter<Hospital_ImageItem> {

    public Hos_ImageItemAdapter(Context context, ArrayList<Hospital_ImageItem> values) {
        super(context, R.layout.row_hospital_image ,values);
    }

    class ViewHolder{
        TextView description;
        ImageView image;
        public ViewHolder(View view){
            image = (ImageView) view.findViewById(R.id.hospital_images_image);
            description = (TextView) view.findViewById(R.id.hospital_images_description);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        ViewHolder holder = null;
        if(view==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.row_hospital_image,parent,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        Hospital_ImageItem item = getItem(position);
        holder.image.setImageBitmap(item.getImage());
        holder.description.setText(item.getDescription());
        return view;
    }
}
