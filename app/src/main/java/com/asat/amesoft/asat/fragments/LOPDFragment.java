package com.asat.amesoft.asat.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asat.amesoft.asat.R;
import com.asat.amesoft.asat.Tools.Tools;
import com.asat.amesoft.asat.Tools.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class LOPDFragment extends Fragment {


    private boolean accept;
    public LOPDFragment() {
        accept=false;
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().getBoolean("accept")){
            this.accept=getArguments().getBoolean("accept");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lopd, container, false);
        final TextView textView = (TextView) view.findViewById(R.id.lopd_terms);
        if(this.accept){
            Button accept = (Button) view.findViewById(R.id.lopd_cancel);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.lopd_checkbox);
            accept.setVisibility(View.VISIBLE);
            checkBox.setVisibility(View.VISIBLE);
        }

        RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Tools.getLODP,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {

                        try {
                            textView.setText(Html.fromHtml(new JSONObject(response).getString("disclaimer_text")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("response","shit  happens");
                    }
                }
        );
        requestQueue.add(stringRequest);

        return view;
    }

}
