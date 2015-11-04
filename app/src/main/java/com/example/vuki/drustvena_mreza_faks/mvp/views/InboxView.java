package com.example.vuki.drustvena_mreza_faks.mvp.views;

import com.example.vuki.drustvena_mreza_faks.models.User;

import java.util.List;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface InboxView extends BaseView {
    void onUserListReceived(List<User> users);
    void onUserListEmpty();
    void onTokenExpired();

}

