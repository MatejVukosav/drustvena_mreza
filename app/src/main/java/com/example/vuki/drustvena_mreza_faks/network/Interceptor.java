package com.example.vuki.drustvena_mreza_faks.network;

import android.text.TextUtils;

import com.example.vuki.drustvena_mreza_faks.SocialNetworkApplication;
import com.example.vuki.drustvena_mreza_faks.helpers.SharedPrefsHelper;

/**
 * Created by Vuki on 4.11.2015..
 */
public class Interceptor implements retrofit.RequestInterceptor {

    private static final String TOKEN="token";

    @Override
    public void intercept(RequestFacade request) {
        String token= SharedPrefsHelper.getToken(SocialNetworkApplication.getInstance());
        if(!TextUtils.isEmpty(token)){
            request.addQueryParam(TOKEN,token);
        }
    }
}
