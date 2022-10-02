package com.example.vuki.drustvena_mreza_faks.mvp.presenters.impl;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.models.User;
import com.example.vuki.drustvena_mreza_faks.mvp.interactors.InboxInteractor;
import com.example.vuki.drustvena_mreza_faks.mvp.listeners.InboxListener;
import com.example.vuki.drustvena_mreza_faks.mvp.views.InboxView;

import java.util.List;

/**
 * Created by Vuki on 4.11.2015..
 */
public class InboxPresenterImpl implements com.example.vuki.drustvena_mreza_faks.mvp.presenters.InboxPresenter {

    private InboxView view;
    private InboxInteractor inboxInteractor;

    public InboxPresenterImpl(InboxView view, InboxInteractor inboxInteractor) {
        this.view = view;
        this.inboxInteractor = inboxInteractor;
    }

    @Override
    public void getUsers() {
        view.showProgress();
        inboxInteractor.getInboxUsers(listener);
    }

    private InboxListener listener = new InboxListener() {
        @Override
        public void onUserListReceived(List<User> users) {
            view.hideProgress();
            if(users!=null) {
                view.onUserListReceived(users);
            }else{
                view.onUserListEmpty();
            }
        }

        @Override
        public void onError(String error) {
            view.hideProgress();
            view.showError(R.string.error_download);
        }

        @Override
        public void onTokenExpired() {
            view.onTokenExpired();
        }
    };
}
