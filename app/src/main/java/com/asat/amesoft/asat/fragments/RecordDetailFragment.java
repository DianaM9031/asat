package com.asat.amesoft.asat.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asat.amesoft.asat.Models.Hospital_ImageItem;
import com.asat.amesoft.asat.MyApplication;
import com.asat.amesoft.asat.R;
import com.asat.amesoft.asat.Tools.Tools;
import com.asat.amesoft.asat.Tools.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordDetailFragment extends Fragment {

    private TextView tv_title,tv_date,tv_text;
    private String id,title,date;

    public RecordDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id=getArguments().getString("id");
        title=getArguments().getString("title");
        date=getArguments().getString("date");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record_detail, container, false);
        tv_title = (TextView) view.findViewById(R.id.record_detail_title);
        tv_date = (TextView) view.findViewById(R.id.record_detail_date);
        tv_text = (TextView) view.findViewById(R.id.record_detail_text);
        tv_title.setText(title);
        tv_date.setText(date);

        connect(Tools.recordDetail);
        return view;
    }


    private void connect(String uri){
        RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        Log.v("Response",response);
                        processResponse(response);
                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("response","Errors  happens "+error);
                    }
                }
        )
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("token_id", MyApplication.getToken());
                params.put("anam_id",id);
                return params;
            }
        };
        queue.add(stringRequest);

    }

    private void processResponse(String response) {

        JSONObject jsonObject;
        String result="";

        try {
            jsonObject = new JSONObject(response);
            result = jsonObject.getJSONObject("response").get("result").toString();
            //Si el resultado de la consulta esta bien
            if(result.equals("OK")){
                tv_text.setText(Html.fromHtml(jsonObject.getString("anam_text")));
                ArrayList<Hospital_ImageItem> lista = new ArrayList<>();
                JSONArray images = jsonObject.getJSONArray("lst_images");
                for(int i=0; i<images.length(); i++){
                    JSONObject item = images.getJSONObject(i).getJSONObject("item_img");
//                    lista.add(
//                            new Hospital_ImageItem(decodeImage(item.getString("img")),item.getString("img_text"))

//                    );
                }
//                BaseAdapter adapter = new Hospital_Image_IA(getActivity(),lista);
//                listView.setAdapter(adapter);

            }
        } catch (JSONException e) {

        }
    }

}