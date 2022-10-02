package com.example.vuki.drustvena_mreza_faks.mvp.interactors.impl;

import com.example.vuki.drustvena_mreza_faks.SocialNetworkApplication;
import com.example.vuki.drustvena_mreza_faks.models.ForgottenPasswordResponse;
import com.example.vuki.drustvena_mreza_faks.mvp.interactors.ForgottenPasswordInteractor;
import com.example.vuki.drustvena_mreza_faks.mvp.listeners.ForgottenPasswordListener;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Vuki on 4.11.2015..
 */
public class ForgottenPasswordInteractorImpl implements ForgottenPasswordInteractor {
    private ForgottenPasswordListener forgottenPasswordListener;


    @Override
    public void resetPassword(ForgottenPasswordListener listener, String email) {
        forgottenPasswordListener = listener;
        SocialNetworkApplication.getApiService().postResetPassword(email, forgottenPasswordCallback);
    }

    private Callback<ForgottenPasswordResponse> forgottenPasswordCallback = new Callback<ForgottenPasswordResponse>() {
        @Override
        public void success(ForgottenPasswordResponse forgottenPasswordResponse, Response response) {
            forgottenPasswordListener.onPasswordRestartSuccess();
        }

        @Override
        public void failure(RetrofitError error) {
            forgottenPasswordListener.onPasswordRestartFail(error.getMessage());
        }
    };
}
