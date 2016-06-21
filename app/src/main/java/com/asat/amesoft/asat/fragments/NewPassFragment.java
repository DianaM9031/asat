package com.asat.amesoft.asat.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asat.amesoft.asat.R;
import com.asat.amesoft.asat.Tools.Tools;
import com.asat.amesoft.asat.Tools.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewPassFragment extends Fragment {

    EditText email;
    private Button submit;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            validateFields();
        }
    };

    public NewPassFragment() {
        // Required empty public constructor
    }

    private void validateFields(){
        if(email.getText().toString().equals("")){
            submit.setEnabled(false);
        }
        else{
            submit.setEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_pass, container, false);
        submit = (Button) view.findViewById(R.id.new_submit);
        email = (EditText) view.findViewById(R.id.new_email);
        email.addTextChangedListener(textWatcher);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect(email.getText().toString());
            }
        });

        return view;
    }


    private void connect(final String token_id){
        //Volley connection
        RequestQueue queue = VolleySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Tools.newPass,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        //processResponse(response);

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
                return params;
            }
        };
        queue.add(stringRequest);

    }

}
