package com.asat.amesoft.asat.fragments;


import android.content.Intent;
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
import android.widget.CompoundButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asat.amesoft.asat.MainActivity;
import com.asat.amesoft.asat.R;
import com.asat.amesoft.asat.Tools.Tools;
import com.asat.amesoft.asat.Tools.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class LOPDFragment extends Fragment {

    private boolean accept;
    private CheckBox checkBox;
    private String token="";
    private String version="";



    public LOPDFragment() {
        accept=false;
        token = Tools.getToken();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments().getBoolean("accept")){
            this.accept=getArguments().getBoolean("accept");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_lopd, container, false);

        final Button submit = (Button) view.findViewById(R.id.lopd_accept);
        final TextView textView = (TextView) view.findViewById(R.id.lopd_terms);
        if(this.accept){
            Button cancel = (Button) view.findViewById(R.id.lopd_cancel);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.lopd_checkbox);
            cancel.setVisibility(View.VISIBLE);
            checkBox.setVisibility(View.VISIBLE);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().popBackStack();
                }
            });
        }
        else{
            submit.setEnabled(true);
            submit.setText(getResources().getString(R.string.lopd_back));
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().popBackStack();
                }
            });
        }


        RequestQueue requestQueue = VolleySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, Tools.getLODP,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {

                        try {
                            textView.setText(Html.fromHtml(new JSONObject(response).getString("disclaimer_text")));
                            setVersion(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("response","Errors  happens");
                    }
                }
        );
        checkBox = (CheckBox) view.findViewById(R.id.lopd_checkbox);
        checkBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                        if(isChecked) {
                            submit.setEnabled(true);
                        }
                        else{
                            submit.setEnabled(false);
                        }
                    }
                }
        );

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAction();
            }
        });



        requestQueue.add(stringRequest);

        return view;
    }

    public void setVersion(String version) {
        String aux="";
        try {
            aux=new JSONObject(version).getString("disclaimer_version");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v("LOPD ACCEPT",aux);
        this.version = aux;
    }

    private void selectAction(){
        if(this.accept){
            connect(Tools.setLOPD);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
        else{
            //volver a la pantalla anterior
            getFragmentManager().popBackStack();
        }
    }



    private void connect(String uri){
        RequestQueue queue = VolleySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        Log.v("Record response",response);
                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("response","Errors  happens RecordF"+error);
                    }
                }
        )
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("token_id",token);
                params.put("disclaimer_version",version);
                return params;
            }
        };
        queue.add(stringRequest);
    }


}
