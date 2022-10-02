package com.example.vuki.drustvena_mreza_faks.mvp.interactors.impl;

import com.example.vuki.drustvena_mreza_faks.SocialNetworkApplication;
import com.example.vuki.drustvena_mreza_faks.models.UserResponse;
import com.example.vuki.drustvena_mreza_faks.mvp.interactors.UserInteractor;
import com.example.vuki.drustvena_mreza_faks.mvp.listeners.UserListener;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Vuki on 4.11.2015..
 */
public class UserInteractorImpl implements UserInteractor {

    private UserListener userListener;

    @Override
    public void getUser(UserListener listener) {
        userListener = listener;
        SocialNetworkApplication.getApiService().getUser(userResponseCallback);
    }

    private Callback<UserResponse> userResponseCallback = new BaseCallback<UserResponse>() {

        @Override
        public void success(UserResponse userResponse, Response response) {
            userListener.onUserReceived(userResponse.getUser());

        }

        @Override
        public void onTokenExpired() {
            userListener.onTokenExpired();
        }

        @Override
        public void error(RetrofitError error) {
            userListener.onError(error.getMessage());
        }
    };
}
