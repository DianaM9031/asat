package com.asat.amesoft.asat;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asat.amesoft.asat.Tools.Tools;
import com.asat.amesoft.asat.Tools.VolleySingleton;
import com.asat.amesoft.asat.fragments.LOPDFragment;
import com.asat.amesoft.asat.fragments.LoginFragment;
import com.asat.amesoft.asat.fragments.NewPassFragment;
import com.asat.amesoft.asat.fragments.PassChangeFragment;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {


    String message="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        Bundle bundle = new Bundle();
        bundle.putBoolean("accept",true);
        change_content(new LoginFragment());



    }

    public void forgot_pass(View view){
        change_content(new PassChangeFragment());
    }

    public void login_submit(View view){
        final EditText user = (EditText) findViewById(R.id.login_user);
        final EditText pass = (EditText) findViewById(R.id.login_password);
        RequestQueue queue = VolleySingleton.getsInstance().getRequestQueue();


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
                        Log.v("response","shit  happens");
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
        JSONObject jsonObject;
        String result="";
        try {
            jsonObject = new JSONObject(json);

            result = jsonObject.getJSONObject("response").get("result").toString();
            //Si el resultado de la consulta esta bien
            if(result.equals("OK")){
                if(jsonObject.getBoolean("renew_pass")){
                    Snackbar.make(getCurrentFocus(), R.string.msg_newpass, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    change_content(new NewPassFragment());
                }
                else {
                    if (!jsonObject.getBoolean("renew_lopd")) {
                        Snackbar.make(getCurrentFocus(), R.string.msg_newlopd, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        loadLOPD();

                    }
                }

            }
            Log.v("REALLY OUTSIDE",result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadLOPD(){

        final Bundle args = new Bundle();
        args.putBoolean("accept", true);


        LOPDFragment f = new LOPDFragment();
        f.setArguments(args);
        change_content(f);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private void change_content(Fragment f){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_login,f).addToBackStack(null)
                .commit();
    }


}

