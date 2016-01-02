package com.example.vuki.drustvena_mreza_faks.mvp.listeners;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface LoginListener {
    void onLoginSuccess(String token);

    void onLoginFail(String error);
}
