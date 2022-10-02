package com.example.vuki.drustvena_mreza_faks.network;

import android.content.Context;

import com.example.vuki.drustvena_mreza_faks.helpers.SharedPrefsHelpers;

/**
 * Created by Vuki on 27.12.2015..
 */
public class Token implements TokenInterface{

    private Context context;

    public Token(Context context) {
        this.context = context;
    }

    @Override
    public boolean isUserExists() {
        return getToken()!=null;
    }

    @Override
    public String getToken() {
        return SharedPrefsHelpers.getToken(context);
    }
}
