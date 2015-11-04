package com.example.vuki.drustvena_mreza_faks.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Vuki on 4.11.2015..
 */
public class SharedPrefsHelper {

    private static final String SHARED_PREFS_ID = "com.example.vuki.drustvena_mreza_faks";
    private static final String TOKEN = "token";

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFS_ID, Context.MODE_PRIVATE);
    }

    public static String getToken(Context context) {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getString(TOKEN, null);
    }

    public static void setToken(Context context,String token){
        SharedPreferences prefs=getPreferences(context);
        prefs.edit().putString(TOKEN,token).apply();
    }


}
