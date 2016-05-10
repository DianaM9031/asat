package com.asat.amesoft.asat.Models.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.asat.amesoft.asat.Models.Hospital_Item;
import com.asat.amesoft.asat.MyApplication;
import com.asat.amesoft.asat.R;
import com.asat.amesoft.asat.Tools.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by Jorge on 7/05/2016.
 */
public class Hos_itemAdapter extends ArrayAdapter<Hospital_Item> {

    ImageLoader mImageLoader;
    public Hos_itemAdapter(Context context, ArrayList<Hospital_Item> values) {
        super(context, R.layout.row_hospital, values);
        mImageLoader = VolleySingleton.getInstance().getImageLoader();
    }

    class ViewHolder{
        TextView text;
        NetworkImageView icon;
        public ViewHolder(View view) {
            text = (TextView) view.findViewById(R.id.hospital_row_text);
            icon = (NetworkImageView) view.findViewById(R.id.hospital_row_icon);
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
        holder.icon.setImageUrl(item.getIcon(), mImageLoader);

        return view;
    }
}
