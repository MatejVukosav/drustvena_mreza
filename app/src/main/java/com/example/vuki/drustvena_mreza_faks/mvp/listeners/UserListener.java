package com.example.vuki.drustvena_mreza_faks.mvp.listeners;

import com.example.vuki.drustvena_mreza_faks.models.User;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface UserListener  {
    void onUserReceived(User user);

    void onTokenExpired();

    void onError(String error);
}
