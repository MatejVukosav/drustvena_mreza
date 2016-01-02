package com.example.vuki.drustvena_mreza_faks.mvp.presenters;

import com.example.vuki.drustvena_mreza_faks.models.User;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface UsersPresenter {
    void getUsers();
    void onUserClicked(User user);

}
