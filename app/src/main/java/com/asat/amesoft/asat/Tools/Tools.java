package com.asat.amesoft.asat.Tools;

import android.content.res.Configuration;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by Jorge on 20/04/2016.
 */
public class Tools {

    public static boolean permission;

    public static final String baseURL = "http://interno.talentosoftware.com:55955/api/";
    public static final String login = baseURL+"login";
    public static final String keep = baseURL+"keepAlive";

    public static final String getLODP = baseURL+"getDisclaimerText";
    public static final String setLOPD = baseURL+"setDisclaimerTextAccepted";

    public static final String change_pass = baseURL+"changePassword";
    public static final String newPass = baseURL+"NewPass";

    public static final String hospital = baseURL+"getCenterDetail";
    public static final String hospitalRules = baseURL+"getCenterRules";
    public static final String hospitalImages = baseURL+"getCenterImages";

    public static final String record = baseURL+"getHistory";
    public static final String recordDetail = baseURL+"getHistoryDetail";

    public static final String advices = baseURL+"getAdvices";
    public static final String advicesDetail = baseURL+"getAdviceDetail";


    public static final String notifications = baseURL+"getReminders";


    //file path
//    public static final String notifications= Environment.getExternalStorageDirectory();
//    public static final String notifications;

    public static boolean saveFile(String encoded, String name, String uri) {
        if(permission){

            File filePath = new File(uri + name);
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
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
            return false;

    }


}
