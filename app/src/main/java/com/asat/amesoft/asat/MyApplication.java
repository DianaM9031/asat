package com.asat.amesoft.asat;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jorge on 20/04/2016.
 */
public class MyApplication extends Application {

    private static MyApplication sInstance;



    private static String token;
    private static String name;
    private static String lastName;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        MyApplication.lastName = lastName;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        MyApplication.name = name;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        MyApplication.token = token;
    }

    public static  MyApplication getsInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return  sInstance.getApplicationContext();
    }
}
