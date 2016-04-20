package com.asat.amesoft.asat.Tools;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.asat.amesoft.asat.MyApplication;

/**
 * Created by Jorge on 20/04/2016.
 */
public class VolleySingleton {

    private static VolleySingleton sInstance=null;
    private RequestQueue requestQueue;
    private VolleySingleton(){
        requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
    }

    public static VolleySingleton getsInstance(){
        if(sInstance==null){
            sInstance=new VolleySingleton();
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
