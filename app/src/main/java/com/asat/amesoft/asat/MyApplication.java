package com.asat.amesoft.asat;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Created by Jorge on 20/04/2016.
 */
public class MyApplication extends Application {

    private static MyApplication sInstance;



    private static String token;
    private static String name;
    private static String lastName;
    private static String advices_filePath;
    private static String record_filePath;

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

    public static String getRecord_filePath() {
        return record_filePath;
    }

    public static void setRecord_filePath(String record_filePath) {
        MyApplication.record_filePath = record_filePath+"/";
    }

    public static String getAdvices_filePath() {
        return advices_filePath;
    }

    public static void setAdvices_filePath(String advices_filePath) {
        MyApplication.advices_filePath = advices_filePath+"/";
    }

    public static  MyApplication getsInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return  sInstance.getApplicationContext();
    }

    public static void changeLanguage(String languageToLoad, Context context){
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                getAppContext().getResources().getDisplayMetrics());
    }

}
