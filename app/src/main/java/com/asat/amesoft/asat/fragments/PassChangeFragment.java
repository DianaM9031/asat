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
public class PassChangeFragment extends Fragment {

    private EditText actual, newPass, confirm,name;
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

    private void validateFields(){
        if(
                this.actual.getText().toString().equals("")
                || this.newPass.getText().toString().equals("")
                || this.confirm.getText().toString().equals("")
                ){
            submit.setEnabled(false);
        }
        else{
            if(!this.newPass.getText().toString().equals(this.confirm.getText().toString())) {

                confirm.setError(this.getString(R.string.pass_not_match));
                submit.setEnabled(false);
            }
            else
                submit.setEnabled(true);


        }
    }

    public PassChangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pass_change, container, false);

        name = (EditText) view.findViewById(R.id.pass_change_name);
        actual = (EditText) view.findViewById(R.id.actual_pass);
        newPass = (EditText) view.findViewById(R.id.new_pass);
        confirm = (EditText) view.findViewById(R.id.confirm_pass);

        actual.addTextChangedListener(textWatcher);
        newPass.addTextChangedListener(textWatcher);
        confirm.addTextChangedListener(textWatcher);

        submit = (Button) view.findViewById(R.id.new_pass_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });

        return view;
    }

    private void connect(){
        //Volley connection
        RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Tools.change_pass,
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
                params.put("user_name",name.getText().toString());
                params.put("pass_old",actual.getText().toString());
                params.put("pass_new",newPass.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);

    }

}
