package com.asat.amesoft.asat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asat.amesoft.asat.Tools.Tools;
import com.asat.amesoft.asat.Tools.VolleySingleton;
import com.asat.amesoft.asat.fragments.AboutFragment;
import com.asat.amesoft.asat.fragments.AdvicesFragment;
import com.asat.amesoft.asat.fragments.HospitalFragment;
import com.asat.amesoft.asat.fragments.HospitalRulesFragment;
import com.asat.amesoft.asat.fragments.MenuFragment;
import com.asat.amesoft.asat.fragments.NotificationsFragment;
import com.asat.amesoft.asat.fragments.RecordFragment;
import com.asat.amesoft.asat.fragments.SettingsFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    Toolbar toolbar;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //token=getIntent().getStringExtra("token");
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
       // TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);


        setSupportActionBar(toolbar);

        sharedPref = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        String language =sharedPref.getString("language","en");
        MyApplication.changeLanguage(language,this);

        if(sharedPref.contains("token")){
            MyApplication.setToken(sharedPref.getString("token","null"));
            MyApplication.setName(sharedPref.getString("name","null"));
            MyApplication.setLastName(sharedPref.getString("lastName","null"));
            keepSession(MyApplication.getToken(), Tools.keep);
        }
        else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }



        if(savedInstanceState==null) {
            change_content(new MenuFragment(), false);
        }
        //token=token.replace("K","q");

    }

    public void goHospital(View view){
       // Log.v("STATIC TOKEN",token);

        Fragment f = new HospitalFragment();
        change_content(f,true);
        toolbar.setTitle(R.string.hospital_title);
        keepSession(MyApplication.getToken(),Tools.keep);
    }

    public void goRules(View view){
        Fragment f = new HospitalRulesFragment();
        change_content(f,true);

    }

    public void goRecord(View view){
        Fragment f = new RecordFragment();
        change_content(f,true);
        toolbar.setTitle(R.string.record_title);
        keepSession(MyApplication.getToken(),Tools.keep);
    }

    public void goAdvices(View view){
        Fragment f = new AdvicesFragment();
        change_content(f,true);
        toolbar.setTitle(R.string.advices_title);
        keepSession(MyApplication.getToken(),Tools.keep);
    }

    public void goSettings(View view){
        Fragment f = new SettingsFragment();
        change_content(f,true);
        toolbar.setTitle(R.string.settings_title);
        keepSession(MyApplication.getToken(),Tools.keep);
    }

    public void goAbout(View view){
        Fragment f = new AboutFragment();
        change_content(f,true);
        toolbar.setTitle(R.string.about_title);
        keepSession(MyApplication.getToken(),Tools.keep);
    }

    public void goNotifications(View view){
        Fragment f = new NotificationsFragment();
        change_content(f,true);
        toolbar.setTitle(R.string.notifications_title);
        keepSession(MyApplication.getToken(),Tools.keep);
    }


    private void checkSession(String response) {

        JSONObject jsonObject;
        String result;

        try {
            jsonObject = new JSONObject(response);
            result = jsonObject.getJSONObject("response").get("result").toString();
            //Si el resultado de la consulta esta bien
            if(result.equals("ERROR")){
                Intent intent = new Intent(this, LoginActivity.class);
                //intent.putExtra("token",token);
                startActivity(intent);
            }
            else{

            }
        } catch (JSONException e) {

        }
    }








    private void change_content(Fragment f, boolean back){
        FragmentTransaction ft;

        if(f.getClass().equals(MenuFragment.class)){
            //toolbar.setTitle(R.string.menu_title);
           // toolbar.setNavigationIcon(null);
        }
        else{
            toolbar.setNavigationIcon(+R.drawable.ic_menu_black_24dp);
        }

        ft=getSupportFragmentManager().beginTransaction().replace(R.id.content_main,f);
        if(back){
            ft.addToBackStack(null).commit();
        }
        else{
         ft.commit(); //
        }
    }


    private void keepSession(final String token_id, String uri){
        //Volley connection
        RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        Log.v("Test session",response);
                        checkSession(response);
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

}
