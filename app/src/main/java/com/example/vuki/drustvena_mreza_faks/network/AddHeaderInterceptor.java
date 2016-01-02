package com.example.vuki.drustvena_mreza_faks.network;

import android.text.TextUtils;

import com.example.vuki.drustvena_mreza_faks.SocialNetworkApplication;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.helpers.SharedPrefsHelpers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashSet;

/**
 * Created by Vuki on 27.12.2015..
 */
public class AddHeaderInterceptor implements com.squareup.okhttp.Interceptor {

    private static final String TOKEN = "token";
    private static String TAG;

    @Override
    public Response intercept(Chain chain) throws IOException {

        TAG = getClass().getSimpleName();
        HashSet<String> cookies = new HashSet<>();
        String authToken = "";

        authToken = SharedPrefsHelpers.getToken(SocialNetworkApplication.getInstance());
        String connectSid = SharedPrefsHelpers.getCookiesConnectSid(SocialNetworkApplication.getInstance());

        if (connectSid != null) {
            NotesHelpers.logMessage(TAG, "cookies");
            int i = 0;
            for (String cookie : cookies) {
                NotesHelpers.logMessage("" + i, cookie);
                i++;
            }
        } else {
            NotesHelpers.logMessage(TAG, "cookies are null");
        }

        NotesHelpers.logMessage(TOKEN, authToken);
        Request original = chain.request();
        if (!TextUtils.isEmpty(connectSid)) {
            Request request = original.newBuilder()
                    .header("user-agent", "BubblesAndroid")
                    .header("Accept", "application/vnd.yourapi.v1.full+json")
                    .header("Authorization", authToken)
                    .addHeader("cookie", connectSid)
                    .method(original.method(), original.body())
                    .build();


            return chain.proceed(request);
        } else {
            NotesHelpers.logMessage(TAG, "token is empty");
        }

        return chain.proceed(original); //or null?
    }
}
