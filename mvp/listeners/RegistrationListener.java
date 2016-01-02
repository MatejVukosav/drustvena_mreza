package com.example.vuki.drustvena_mreza_faks.mvp.listeners;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface RegistrationListener {
    void onRegistreationSuccess(String token);
    void onRegistrationnFail(String error);
}
