package com.example.vuki.drustvena_mreza_faks.mvp.presenters.impl;

import android.text.TextUtils;

import com.example.vuki.drustvena_mreza_faks.SocialNetworkApplication;
import com.example.vuki.drustvena_mreza_faks.helpers.SharedPrefsHelper;
import com.example.vuki.drustvena_mreza_faks.mvp.interactors.LoginInteractor;
import com.example.vuki.drustvena_mreza_faks.mvp.listeners.LoginListener;
import com.example.vuki.drustvena_mreza_faks.mvp.presenters.LoginPresenter;
import com.example.vuki.drustvena_mreza_faks.mvp.views.LoginView;

/**
 * Created by Vuki on 4.11.2015..
 */
public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView, LoginInteractor loginInteractor) {
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }

    @Override
    public void login(String email, String password) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            loginView.showProgress();
            loginInteractor.login(loginListener,email,password);
        }

    }

    private LoginListener loginListener=new LoginListener() {
        @Override
        public void onLoginSuccess(String token) {
            SharedPrefsHelper.setToken(SocialNetworkApplication.getInstance(),token);
            loginView.hideProgress();
            loginView.onLoginSucess();
        }

        @Override
        public void onLoginFail(String error) {
            loginView.hideProgress();
            loginView.onLoginFailed();
        }
    };
}
