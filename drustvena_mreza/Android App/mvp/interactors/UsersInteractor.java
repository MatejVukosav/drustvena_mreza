package com.example.vuki.drustvena_mreza_faks.mvp.interactors;

import com.example.vuki.drustvena_mreza_faks.mvp.listeners.UsersListener;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface UsersInteractor {
    void getUsers(UsersListener listener);
}
