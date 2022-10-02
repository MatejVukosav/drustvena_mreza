package com.example.vuki.drustvena_mreza_faks.mvp.interactors.impl;


import com.example.vuki.drustvena_mreza_faks.SocialNetworkApplication;
import com.example.vuki.drustvena_mreza_faks.models.RegisterResponse;
import com.example.vuki.drustvena_mreza_faks.mvp.interactors.RegistrationInteractor;
import com.example.vuki.drustvena_mreza_faks.mvp.listeners.RegistrationListener;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Vuki on 4.11.2015..
 */
public class RegistrationInteractorImpl implements RegistrationInteractor {
    private RegistrationListener registrationListener;


    @Override
    public void register(RegistrationListener listener, String email, String password) {
        registrationListener=listener;
        Register request=new Register(email,password);
        SocialNetworkApplication.getApiService().postRegister(request,registerResponseCallback);
    }

    private Callback<RegisterResponse> registerResponseCallback=new Callback<RegisterResponse>() {
        @Override
        public void success(RegisterResponse registerResponse, Response response) {
            registrationListener.onRegistreationSuccess(registerResponse.getUser().getToken());
        }

        @Override
        public void failure(RetrofitError error) {
        registrationListener.onRegistrationnFail(error.getMessage());
        }
    };
}
