package com.example.vuki.drustvena_mreza_faks.mvp.interactors.impl;

import android.content.Context;

import com.example.vuki.drustvena_mreza_faks.helpers.SharedPrefsHelper;
import com.example.vuki.drustvena_mreza_faks.mvp.interactors.TokenInteractor;

/**
 * Created by Vuki on 4.11.2015..
 */
public class TokenInteractorImpl implements TokenInteractor {

    private Context context;

    public TokenInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean isUserExists() {
        return getToken()!=null;
    }

    @Override
    public String getToken() {
        return SharedPrefsHelper.getToken(context);
    }
}
