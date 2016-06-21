package com.asat.amesoft.asat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asat.amesoft.asat.Tools.Tools;
import com.asat.amesoft.asat.Tools.VolleySingleton;
import com.asat.amesoft.asat.fragments.LOPDFragment;
import com.asat.amesoft.asat.fragments.LoginFragment;
import com.asat.amesoft.asat.fragments.NewPassFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    CoordinatorLayout layout;

    private boolean session;
    private SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);


        layout = (CoordinatorLayout) findViewById(R.id.content_login);
        if(savedInstanceState==null && !session) {
            change_content(new LoginFragment(), false);
        }



    }



    public void forgot_pass(View view){
        change_content(new NewPassFragment(),true);
    }

    public void login_submit(View view){

        final EditText user = (EditText) findViewById(R.id.login_user);
        final EditText pass = (EditText) findViewById(R.id.login_password);

        //Volley connection
        RequestQueue queue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Tools.login,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        processResponse(response);
                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("response","Errors  happens "+error.toString());
                        Snackbar.make(layout, error.toString(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
                )
        {
        @Override
            protected Map<String,String> getParams(){
            Map<String,String> params = new HashMap<String,String>();
            params.put("user_name",user.getText().toString());
            params.put("user_pass",pass.getText().toString());
            return params;
        }
        };
        queue.add(stringRequest);

    }

    private void processResponse(String json){
        Log.v("LOGIN",json);
        JSONObject jsonObject;
        String result="";
        try {
            jsonObject = new JSONObject(json);

            result = jsonObject.getJSONObject("response").get("result").toString();
            //Si el resultado de la consulta esta bien
            if(result.equals("OK")){
                String token=jsonObject.getString("token_id");
                String name=jsonObject.getString("user_fn");
                String lastName=jsonObject.getString("user_ln");

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("token", token);
                editor.putString("name", name);
                editor.putString("lastName", lastName);
                editor.apply();

                Tools.setToken(token);
                Tools.setName(name);
                Tools.setLastName(lastName);

                if(jsonObject.getBoolean("renew_pass")){
                    Snackbar.make(layout, R.string.msg_newpass, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    change_content(new NewPassFragment(),true);
                }
                else {
                    if (jsonObject.getBoolean("renew_lopd")) {
                        Snackbar.make(layout, R.string.msg_newlopd, Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                        Bundle args = new Bundle();
                        args.putBoolean("accept", true);
                        LOPDFragment f = new LOPDFragment();
                        f.setArguments(args);
                        change_content(f,true);
                    }
                    else{
                        loadMenu(token);
                    }
                }
            }
            else{
                Snackbar.make(layout, jsonObject.getJSONObject("response").get("msg").toString(), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadMenu(String token){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("token",token);
        startActivity(intent);
    }


    private void change_content(Fragment f, boolean back){
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_login,f);
        if(back) {
            ft.addToBackStack(null).commit();
        }else{
            ft.commit();
        }
    }





}

