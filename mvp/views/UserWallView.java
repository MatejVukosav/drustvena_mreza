package com.example.vuki.drustvena_mreza_faks.mvp.views;

import com.example.vuki.drustvena_mreza_faks.models.User;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface UserWallView extends BaseView {

    void onUserDetailsReceived(User user);
    void onTokenExpired();

}
