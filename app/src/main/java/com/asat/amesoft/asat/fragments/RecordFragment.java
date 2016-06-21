package com.asat.amesoft.asat.fragments;


import android.os.Bundle;
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
import com.asat.amesoft.asat.Models.Adapters.Records_IA;
import com.asat.amesoft.asat.Models.Record_Item;
import com.asat.amesoft.asat.R;
import com.asat.amesoft.asat.Tools.Tools;
import com.asat.amesoft.asat.Tools.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends Fragment {
    String token;
    ListView listView;

    public RecordFragment() {
        // Required empty public constructor
        this.token= Tools.getToken();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);

        TextView name = (TextView) view.findViewById(R.id.record_name);
        TextView lastName = (TextView) view.findViewById(R.id.record_lastname);
        name.setText(Tools.getName());
        lastName.setText(Tools.getLastName());

        listView = (ListView) view.findViewById(R.id.record_list);
        connect(this.token, Tools.record);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void connect(final String token_id, String uri){
        RequestQueue queue = VolleySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        Log.v("Record response",response);
                        processResponse(response);
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
                params.put("token_id",token_id);
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

            if(result.equals("OK")){
                final ArrayList<Record_Item> lista = new ArrayList<>();
                JSONArray images = jsonObject.getJSONArray("anamnesis");
                for(int i=0; i<images.length(); i++){
                    JSONObject item = images.getJSONObject(i).getJSONObject("anam_item");
                    String fecha = item.getString("anam_date");
                    //fecha=fecha.substring(0,fecha.length()-3);

                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
                    Date date = formatter.parse(fecha);

                    formatter = new SimpleDateFormat("dd/MM/yyy");

//                    formatter = new SimpleDateFormat("MM/dd/yyyy");

                    lista.add(
                            new Record_Item(item.getString("anam_id"),item.getString("anam_title"),formatter.format(date))
//                            new Record_Item(item.getString("anam_id"),item.getString("anam_title"),fecha)

                    );
                }
                ArrayAdapter adapter;
                if(getActivity()!=null) {
                    adapter = new Records_IA(getActivity(), lista);
                    listView.setAdapter(adapter);
                }


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Fragment f = new RecordDetailFragment();
                        Bundle args = new Bundle();
                        args.putString("title",lista.get(position).getTitle());
                        args.putString("date",lista.get(position).getDate());
                        args.putString("id",lista.get(position).getId());
                        f.setArguments(args);
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_main,f).addToBackStack(null)
                                .commit();
                    }
                });


            }
        } catch (JSONException e) {

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
