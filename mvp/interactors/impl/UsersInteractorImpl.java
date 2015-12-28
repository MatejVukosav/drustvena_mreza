package com.example.vuki.drustvena_mreza_faks.mvp.interactors.impl;

import com.example.vuki.drustvena_mreza_faks.SocialNetworkApplication;
import com.example.vuki.drustvena_mreza_faks.models.UsersResponse;
import com.example.vuki.drustvena_mreza_faks.mvp.interactors.UsersInteractor;
import com.example.vuki.drustvena_mreza_faks.mvp.listeners.UsersListener;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Vuki on 4.11.2015..
 */
public class UsersInteractorImpl implements UsersInteractor {
    private UsersListener usersListener;

    @Override
    public void getUsers(UsersListener listener) {
        usersListener = listener;
        SocialNetworkApplication.getApiService().getUsers(usersResponseCallback);
    }

    private Callback<UsersResponse> usersResponseCallback = new BaseCallback<UsersResponse>() {


        @Override
        public void success(UsersResponse usersResponse, Response response) {
            usersListener.onUsersReceived(usersResponse.getUsers());
        }

        @Override
        public void onTokenExpired() {
            usersListener.onTokenExpired();
        }

        @Override
        public void error(RetrofitError error) {
            usersListener.onError(error.getMessage());
        }
    };
}
