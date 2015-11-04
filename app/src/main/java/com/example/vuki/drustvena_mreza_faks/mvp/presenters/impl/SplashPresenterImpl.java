package com.example.vuki.drustvena_mreza_faks.mvp.presenters.impl;

import com.example.vuki.drustvena_mreza_faks.mvp.interactors.TokenInteractor;
import com.example.vuki.drustvena_mreza_faks.mvp.presenters.SplashPresenter;
import com.example.vuki.drustvena_mreza_faks.mvp.views.SplashView;

/**
 * Created by Vuki on 4.11.2015..
 */
public class SplashPresenterImpl implements SplashPresenter {
    private SplashView view;
    private TokenInteractor tokenInteractor;

    public SplashPresenterImpl(SplashView view, TokenInteractor tokenInteractor) {
        this.view = view;
        this.tokenInteractor = tokenInteractor;
    }

    @Override
    public void checkUserExist() {
        if(tokenInteractor.isUserExists()){
            view.showHome();
        }else{
            view.showLogin();
        }
    }
}

