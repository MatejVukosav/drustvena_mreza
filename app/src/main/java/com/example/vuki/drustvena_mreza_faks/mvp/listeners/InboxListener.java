package com.example.vuki.drustvena_mreza_faks.mvp.listeners;

import com.example.vuki.drustvena_mreza_faks.models.User;

import java.util.List;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface InboxListener {
    void onUserListReceived(List<User> users);

    void onError(String error);

    void onTokenExpired();
}
