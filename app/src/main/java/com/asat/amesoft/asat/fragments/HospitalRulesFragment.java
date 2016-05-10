package com.asat.amesoft.asat.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.asat.amesoft.asat.MyApplication;
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
public class HospitalRulesFragment extends Fragment {

    String token,Stitle,hospital;
    TextView title, ruleTitle, rules;

    public HospitalRulesFragment() {
        // Required empty public constructor
        this.token = MyApplication.getToken();
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
        View view = inflater.inflate(R.layout.fragment_hospital_rules, container, false);
        title = (TextView) view.findViewById(R.id.hospital_title);
        ruleTitle = (TextView) view.findViewById(R.id.rules_title);
        rules = (TextView) view.findViewById(R.id.rules_rules);

        title.setText(Stitle);
        connect(this.token, Tools.hospitalRules);
        return view;
    }


    private void connect(final String token_id,String uri){
        RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();
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
                        Log.v("response","Errors  happens");
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
        String rules_title="";
        String rules_text="";
        try {
            jsonObject = new JSONObject(response);
            result = jsonObject.getJSONObject("response").get("result").toString();
            //Si el resultado de la consulta esta bien
            if(result.equals("OK")){
                rules_title=jsonObject.getString("rules_title");
                rules_text=jsonObject.getString("rules_text");

                ruleTitle.setText(rules_title);
                rules.setText(rules_text);
            }
        } catch (JSONException e) {

        }
    }


}
