package com.example.vuki.drustvena_mreza_faks.mvp.presenters.impl;

import android.text.TextUtils;

import com.example.vuki.drustvena_mreza_faks.mvp.interactors.RegistrationInteractor;
import com.example.vuki.drustvena_mreza_faks.mvp.listeners.RegistrationListener;
import com.example.vuki.drustvena_mreza_faks.mvp.presenters.RegistrationPresenter;
import com.example.vuki.drustvena_mreza_faks.mvp.views.RegisterView;

/**
 * Created by Vuki on 4.11.2015..
 */
public class RegisterPresenterImpl implements RegistrationPresenter {
    private RegisterView view;
    private RegistrationInteractor interactor;

    public RegisterPresenterImpl(RegisterView view, RegistrationInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void register(String email, String password) {
        if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){
            view.showProgress();
            interactor.register(registrationListener,email,password);
        }
    }

    private RegistrationListener registrationListener=new RegistrationListener() {
        @Override
        public void onRegistreationSuccess(String token) {
            view.hideProgress();
            view.onRegisterSucess();
        }

        @Override
        public void onRegistrationnFail(String error) {
            view.hideProgress();
            view.onRegisterFailed();
        }
    };
}
