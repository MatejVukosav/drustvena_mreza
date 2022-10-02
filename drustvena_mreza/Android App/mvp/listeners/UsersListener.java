package com.example.vuki.drustvena_mreza_faks.mvp.listeners;

import com.example.vuki.drustvena_mreza_faks.models.User;

import java.util.List;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface UsersListener {
    void onUsersReceived(List<User> users);

    void onTokenExpired();

    void onError(String error);
}
