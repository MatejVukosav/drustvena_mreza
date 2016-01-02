package com.example.vuki.drustvena_mreza_faks.mvp.interactors;

import com.example.vuki.drustvena_mreza_faks.mvp.listeners.ForgottenPasswordListener;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface ForgottenPasswordInteractor {
    void resetPassword(ForgottenPasswordListener listener,String email);
}
