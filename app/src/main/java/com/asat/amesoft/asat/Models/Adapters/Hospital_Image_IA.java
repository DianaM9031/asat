package com.asat.amesoft.asat.Models.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asat.amesoft.asat.Models.Hospital_ImageItem;
import com.asat.amesoft.asat.R;

import java.util.ArrayList;

/**
 * Created by Jorge on 8/05/2016.
 */
public class Hospital_Image_IA extends BaseAdapter {

    ArrayList<Hospital_ImageItem> values;
    Context context;
    public Hospital_Image_IA(Context context, ArrayList<Hospital_ImageItem> values) {
//        super(context, R.layout.row_hospital_image ,values);
        this.context=context;
        this.values=values;
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
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        ViewHolder holder = null;
        if(view==null){
            LayoutInflater inflater = LayoutInflater.from(this.context);
            view = inflater.inflate(R.layout.row_hospital_image,parent,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        Hospital_ImageItem item = values.get(position);
        holder.image.setImageBitmap(item.getImage());
        holder.description.setText(item.getDescription());
        return view;
    }
}
