package com.example.vuki.drustvena_mreza_faks.mvp.interactors;

import com.example.vuki.drustvena_mreza_faks.mvp.listeners.LoginListener;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface LoginInteractor {
    void login(LoginListener listener,String email,String password);
}
