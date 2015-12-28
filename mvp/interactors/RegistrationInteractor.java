package com.example.vuki.drustvena_mreza_faks.mvp.interactors;


import com.example.vuki.drustvena_mreza_faks.mvp.listeners.RegistrationListener;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface RegistrationInteractor {
    void register(RegistrationListener listener,String email,String password);
}
