package com.example.vuki.drustvena_mreza_faks.network;

import com.example.vuki.drustvena_mreza_faks.SocialNetworkApplication;
import com.example.vuki.drustvena_mreza_faks.helpers.SharedPrefsHelpers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Vuki on 27.12.2015..
 */
public class AddCookiesInterceptor implements Interceptor {
    private static String TAG;

    @Override
    public Response intercept(Chain chain) throws IOException {

        TAG = getClass().getSimpleName();
        Request.Builder builder = chain.request().newBuilder();

        String connectSid = SharedPrefsHelpers.getCookiesConnectSid(SocialNetworkApplication.getInstance());
        builder.addHeader("cookie", connectSid);

       /* String cookieId = SharedPrefsHelpers.getToken(SocialNetworkApplication.getInstance());
        builder.addHeader("Set-Cookie", cookieId);*/


        return chain.proceed(builder.build());
    }
}
