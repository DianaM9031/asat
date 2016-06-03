package com.asat.amesoft.asat.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asat.amesoft.asat.Models.Adapters.Hospital_IA;
import com.asat.amesoft.asat.Models.Hospital_Item;
import com.asat.amesoft.asat.MyApplication;
import com.asat.amesoft.asat.R;
import com.asat.amesoft.asat.Tools.Tools;
import com.asat.amesoft.asat.Tools.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvicesFragment extends Fragment {

    private ListView listView;

    public AdvicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advices, container, false);
        listView = (ListView) view.findViewById(R.id.advices_list);

        TextView name = (TextView) view.findViewById(R.id.advices_name);
        TextView lastName = (TextView) view.findViewById(R.id.advices_lastname);
        name.setText(MyApplication.getName());
        lastName.setText(MyApplication.getLastName());

        connect(MyApplication.getToken(), Tools.advices);
        return view;
    }

    private void connect(final String token_id,String uri){
        //Volley connection
        RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {

                         Log.v("Advices Res",response);
                        processResponse(response);

                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("response","Errors  happens");
                    }
                }
        )
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("token_id",token_id);
                return params;
            }
        };
        queue.add(stringRequest);

    }

    private void processResponse(String response) {

        JSONObject jsonObject;
        String result;

        try {
            jsonObject = new JSONObject(response);
            result = jsonObject.getJSONObject("response").get("result").toString();
            //Si el resultado de la consulta esta bien
            if(result.equals("OK")){
                JSONArray lst_contact = jsonObject.getJSONArray("advices");

                final ArrayList<String> advice_id= new ArrayList<>();
                final ArrayList<String> lista= new ArrayList<>();

                Log.v("Lista",lst_contact.toString());
                for(int i=0; i<lst_contact.length();i++){
                    JSONObject item = lst_contact.getJSONObject(i).getJSONObject("advice_item");
                    lista.add(item.getString("advice_title"));
                    advice_id.add(item.getString("advice_id"));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),R.layout.row_advices,lista);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Fragment f = new AdvicesDetailFragment();
                        Bundle args = new Bundle();
                        args.putString("id_advice",advice_id.get(position));
                        args.putString("title",lista.get(position));
                        f.setArguments(args);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_main,f)
                                .addToBackStack(null)
                                .commit();
                    }
                });

            }
            else{
                Snackbar.make(listView, jsonObject.getJSONObject("response").get("msg").toString(), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        } catch (JSONException e) {

        }
    }


}
