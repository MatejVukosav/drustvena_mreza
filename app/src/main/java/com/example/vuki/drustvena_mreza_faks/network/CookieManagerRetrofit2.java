package com.example.vuki.drustvena_mreza_faks.network;

import com.example.vuki.drustvena_mreza_faks.SocialNetworkApplication;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.helpers.SharedPrefsHelpers;

import java.io.IOException;
import java.net.CookieManager;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Created by Vuki on 27.12.2015..
 */
public class CookieManagerRetrofit2 extends CookieManager {

    private static String TAG;


    @Override
    public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) throws IOException {

        TAG=getClass().getSimpleName();

        if (requestHeaders != null && requestHeaders.get("Set-Cookie") != null)
            for (String header : requestHeaders.get("Cookie")) {
                if (header.contains("connection.sid")) {
                    SharedPrefsHelpers.setToken(SocialNetworkApplication.getInstance(), header);
                    NotesHelpers.logMessage(TAG,header);
                }
            }

        return super.get(uri, requestHeaders);

    }

    @Override
    public void put(URI uri, Map<String, List<String>> responseHeaders) throws IOException {
        super.put(uri, responseHeaders);

        TAG=getClass().getSimpleName();

        if (responseHeaders != null && responseHeaders.get("Cookie") != null)
            for (String header : responseHeaders.get("Cookie")) {
                if (header.contains("connection.sid")) {
                    SharedPrefsHelpers.setToken(SocialNetworkApplication.getInstance(), header);
                    NotesHelpers.logMessage(TAG,header);
                }
            }

    }
}
