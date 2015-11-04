package com.example.vuki.drustvena_mreza_faks.mvp.views;

import com.example.vuki.drustvena_mreza_faks.models.User;

import java.util.List;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface SearchView extends BaseView {
    void onUsersListReceived(List<User> users);
    void onUsersListEmpty();
    void onTokenExpired();
}
