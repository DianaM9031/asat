package com.asat.amesoft.asat.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asat.amesoft.asat.MyApplication;
import com.asat.amesoft.asat.R;
import com.asat.amesoft.asat.Tools.Tools;
import com.asat.amesoft.asat.Tools.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordDetailFragment extends Fragment {

    private TextView tv_title,tv_date,tv_text;
    private String id,title,date;
    private ListView listView;

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
        listView = (ListView) view.findViewById(R.id.record_list);

        connect(Tools.recordDetail);
        return view;
    }


    private void connect(String uri){
        RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        Log.v("Record detail response",response);
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
                final ArrayList<String> lista = new ArrayList<>();
                final ArrayList<String> files = new ArrayList<>();
                final ArrayList<String> ext = new ArrayList<>();
                JSONArray reports = jsonObject.getJSONArray("reports");

                for(int i=0; i<reports.length(); i++){
                    JSONObject item = reports.getJSONObject(i).getJSONObject("report");

                    Log.v("iteam record file",item.getString("report_type"));
                    lista.add(item.getString("report_text"));
                    files.add(item.getString("report_file"));
                    ext.add(item.getString("report_type"));

                }
              ArrayAdapter<String> adapter;
                if(getActivity()!=null) {
                    adapter = new ArrayAdapter<>(getActivity(), R.layout.row_advices, lista);
                    listView.setAdapter(adapter);
                }

                for(int i=0; i<files.size(); i++){
                    saveFile(files.get(i),lista.get(i));
                }

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MimeTypeMap mime = MimeTypeMap.getSingleton();
                        String mimeType = mime.getMimeTypeFromExtension(ext.get(position));
                        File file = new File(MyApplication.getRecord_filePath()+lista.get(position));
                        Intent intent = new Intent(Intent.ACTION_VIEW);

                        intent.setDataAndType(Uri.fromFile(file), mimeType);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    }
                });

            }
        } catch (JSONException e) {

        }
    }

    private void saveFile(String encoded, String name) {
        if (getActivity() != null) {

            File filePath = new File(MyApplication.getRecord_filePath()+name);


            Log.v("File URI", filePath.toString());
            byte[] file = Base64.decode(encoded, Base64.CRLF);
            FileOutputStream os = null;
            try {
                os = new FileOutputStream(filePath, false);
                os.write(file);
                os.flush();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
