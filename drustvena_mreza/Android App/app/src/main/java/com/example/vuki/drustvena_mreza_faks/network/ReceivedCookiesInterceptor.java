package com.example.vuki.drustvena_mreza_faks.network;

import android.content.Context;

import com.example.vuki.drustvena_mreza_faks.SocialNetworkApplication;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.helpers.SharedPrefsHelpers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashSet;

/**
 * Created by Vuki on 27.12.2015..
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    private final String TAG = getClass().getSimpleName();


    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        String[] upTill = new String[2];
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();
            String cookieId = "";

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
                upTill = header.split(";", 2); // connect.sid=dfasfasdfgasgdfih2729tg52zrqzqwefz
                String[] headerParst = upTill[0].split("connect.sid=");
                cookieId = headerParst[0];
                NotesHelpers.logMessage(TAG, cookieId);
            }

            Context context = SocialNetworkApplication.getInstance();
            SharedPrefsHelpers.setToken(context, cookieId);
            SharedPrefsHelpers.setCookies(context, cookies);
            SharedPrefsHelpers.setCookiesConnectSid(context, upTill[0]);

            NotesHelpers.logMessage(TAG, "cookies: " + cookieId);

        }


        return originalResponse;
    }
}
