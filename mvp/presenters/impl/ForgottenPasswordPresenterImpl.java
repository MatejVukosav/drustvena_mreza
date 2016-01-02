package com.example.vuki.drustvena_mreza_faks.mvp.presenters.impl;

import android.text.TextUtils;

import com.example.vuki.drustvena_mreza_faks.mvp.interactors.ForgottenPasswordInteractor;
import com.example.vuki.drustvena_mreza_faks.mvp.listeners.ForgottenPasswordListener;
import com.example.vuki.drustvena_mreza_faks.mvp.presenters.ForgottenPasswordPresenter;
import com.example.vuki.drustvena_mreza_faks.mvp.views.ForgottenPasswordView;

/**
 * Created by Vuki on 4.11.2015..
 */
public class ForgottenPasswordPresenterImpl implements ForgottenPasswordPresenter {
    private ForgottenPasswordView view;
    private ForgottenPasswordInteractor forgottenPasswordInteractor;

    @Override
    public void sendEmail(String email) {
        if (!TextUtils.isEmpty(email)) {
            view.showProgress();
            forgottenPasswordInteractor.resetPassword(listener, email);
        }
    }

    private ForgottenPasswordListener listener = new ForgottenPasswordListener() {
        @Override
        public void onPasswordRestartSuccess() {
            view.hideProgress();
            view.onForgottenPasswordSuccesfulSend();
        }

        @Override
        public void onPasswordRestartFail(String error) {
            view.hideProgress();
            view.onForgottenPasswordFailed();
        }
    };
}
