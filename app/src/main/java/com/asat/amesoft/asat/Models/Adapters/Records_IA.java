package com.asat.amesoft.asat.Models.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.asat.amesoft.asat.Models.Record_Item;
import com.asat.amesoft.asat.R;

import java.util.ArrayList;

/**
 * Created by Jorge on 10/05/2016.
 */
public class Records_IA extends ArrayAdapter<Record_Item> {
    public Records_IA(Context context, ArrayList<Record_Item> values) {
        super(context, R.layout.row_record ,values);
    }

    class ViewHolder{
        TextView title;
        TextView date;

        public ViewHolder(View view) {
            this.title = (TextView) view.findViewById(R.id.row_record_title);
            this.date = (TextView) view.findViewById(R.id.row_record_date);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;
        if(view==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.row_record,parent,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        holder.title.setText(getItem(position).getTitle());
        holder.date.setText(getItem(position).getDate().toString());

        return view;
    }
}
