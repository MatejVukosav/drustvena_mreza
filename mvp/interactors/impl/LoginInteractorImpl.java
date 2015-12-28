package com.example.vuki.drustvena_mreza_faks.mvp.interactors.impl;

import com.example.vuki.drustvena_mreza_faks.SocialNetworkApplication;
import com.example.vuki.drustvena_mreza_faks.models.LoginRequest;
import com.example.vuki.drustvena_mreza_faks.models.LoginResponse;
import com.example.vuki.drustvena_mreza_faks.mvp.interactors.LoginInteractor;
import com.example.vuki.drustvena_mreza_faks.mvp.listeners.LoginListener;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Vuki on 4.11.2015..
 */
public class LoginInteractorImpl implements LoginInteractor {
    private LoginListener loginListener;


    @Override
    public void login(LoginListener listener, String email, String password) {
        loginListener = listener;
        LoginRequest request = new LoginRequest(email, password);
        SocialNetworkApplication.getApiService().login(request, loginResponseCallback);
    }

    private Callback<LoginResponse> loginResponseCallback = new Callback<LoginResponse>() {
        @Override
        public void success(LoginResponse loginResponse, Response response) {
            loginListener.onLoginSuccess(loginResponse.getUser().getToken());
        }

        @Override
        public void failure(RetrofitError error) {
            loginListener.onLoginFail(error.getMessage());
        }
    };

}
