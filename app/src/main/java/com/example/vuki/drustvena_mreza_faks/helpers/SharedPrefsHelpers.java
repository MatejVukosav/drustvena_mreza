package com.example.vuki.drustvena_mreza_faks.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vuki on 4.11.2015..
 */
public class SharedPrefsHelpers {

    public static final String SHARED_PREFS_ID = "com.example.vuki.drustvena_mreza_faks";
    public static final String SHARED_PREFS_TOKEN = "com.example.vuki.drustvena_mreza_faks_token";
    public static final String SHARED_PREFS_COOKIES = "com.example.vuki.drustvena_mreza_faks_cookies";
    public static final String SHARED_PREFS_COOKIES_CONNECT_SID = "com.example.vuki.drustvena_mreza_faks_cookies_connect_sid";

    private static final String TOKEN = "token";

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFS_ID, Context.MODE_PRIVATE);
    }

    public static String getToken(Context context) {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getString(SHARED_PREFS_TOKEN, "");
    }

    public static void setToken(Context context, String token) {
        SharedPreferences prefs = getPreferences(context);
        prefs.edit().putString(SHARED_PREFS_TOKEN, token).apply();
    }


    public static Set<String> getCookies(Context context) {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getStringSet(SHARED_PREFS_COOKIES, new HashSet<String>());
    }

    public static void setCookies(Context context,  HashSet<String> cookies) {
        SharedPreferences prefs = getPreferences(context);
        prefs.edit().putStringSet(SHARED_PREFS_COOKIES, cookies).apply();
    }


  public static String getCookiesConnectSid(Context context) {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getString(SHARED_PREFS_COOKIES_CONNECT_SID, "");
    }

    public static void setCookiesConnectSid(Context context,  String cookies) {
        SharedPreferences prefs = getPreferences(context);
        prefs.edit().putString(SHARED_PREFS_COOKIES_CONNECT_SID, cookies).apply();
    }


}
