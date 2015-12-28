package com.example.vuki.drustvena_mreza_faks.mvp.interactors.impl;

import com.example.vuki.drustvena_mreza_faks.SocialNetworkApplication;
import com.example.vuki.drustvena_mreza_faks.models.UsersResponse;
import com.example.vuki.drustvena_mreza_faks.mvp.interactors.InboxInteractor;
import com.example.vuki.drustvena_mreza_faks.mvp.listeners.InboxListener;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Vuki on 4.11.2015..
 */
public class InboxInteractorImpl implements InboxInteractor {

    private InboxListener inboxListener;

    @Override
    public void getInboxUsers(InboxListener listener) {
        inboxListener = listener;
        SocialNetworkApplication.getApiService().getInboxUsers(inboxResponseCallback);
    }

    private Callback<UsersResponse> inboxResponseCallback = new BaseCallback<UsersResponse>() {
        @Override
        public void onTokenExpired() {
            inboxListener.onTokenExpired();
        }

        @Override
        public void error(RetrofitError error) {
            inboxListener.onError(error.getMessage());
        }

        @Override
        public void success(UsersResponse usersResponse, Response response) {
            inboxListener.onUserListReceived(usersResponse.getUsers());
        }
    };
}
