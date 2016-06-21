package com.asat.amesoft.asat.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asat.amesoft.asat.Models.Adapters.Hospital_Image_IA;
import com.asat.amesoft.asat.Models.Hospital_ImageItem;
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
public class HospitalImagesFragment extends Fragment {

    String token,Stitle,hospital;
    GridView listView;
    TextView title;

    public HospitalImagesFragment() {
        // Required empty public constructor
        this.token = Tools.getToken();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getArguments().getString("title").isEmpty()) {
            this.Stitle = getArguments().getString("title");
        }
        if (!getArguments().getString("hospital").isEmpty()) {
            this.hospital = getArguments().getString("hospital");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        connect(this.token, Tools.hospitalImages);
        View view = inflater.inflate(R.layout.fragment_hospital_images, container, false);
        listView = (GridView) view.findViewById(R.id.hospital_images_list);
        title = (TextView) view.findViewById(R.id.hospital_images_title);
        title.setText(Stitle);
        return view;
    }

    private void connect(final String token_id,String uri){
        RequestQueue queue = VolleySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
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
                params.put("token_id",token_id);
                params.put("center_id",hospital);
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
                final ArrayList<Hospital_ImageItem> lista = new ArrayList<>();
                final ArrayList<String> imagenes = new ArrayList<>();
                JSONArray images = jsonObject.getJSONArray("lst_images");
                for(int i=0; i<images.length(); i++){
                    JSONObject item = images.getJSONObject(i).getJSONObject("item_img");
                    Log.v("JSON image",item.names().toString());
                    lista.add(
                            new Hospital_ImageItem(decodeImage(item.getString("img")),item.getString("img_text"))
                    );
                    imagenes.add(item.getString("img"));
                }
                BaseAdapter adapter = new Hospital_Image_IA(getActivity(),lista);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Fragment f = new ImageDetail_Fragment();
                        Bundle arg = new Bundle();
                        arg.putString("imagen",imagenes.get(position));
                        f.setArguments(arg);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_main,f)
                                .addToBackStack(null)
                                .commit();
                    }
                });

            }
        } catch (JSONException e) {

        }
    }

    private Bitmap decodeImage(String encoded){
        byte[] decodedImage = Base64.decode(encoded, Base64.CRLF);
        return BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
    }

}
