package com.example.vuki.drustvena_mreza_faks.helpers;

import android.content.Context;

import com.example.vuki.drustvena_mreza_faks.mvp.interactors.impl.LoginInteractorImpl;
import com.example.vuki.drustvena_mreza_faks.mvp.interactors.impl.RegistrationInteractorImpl;
import com.example.vuki.drustvena_mreza_faks.mvp.interactors.impl.TokenInteractorImpl;
import com.example.vuki.drustvena_mreza_faks.mvp.presenters.LoginPresenter;
import com.example.vuki.drustvena_mreza_faks.mvp.presenters.RegistrationPresenter;
import com.example.vuki.drustvena_mreza_faks.mvp.presenters.SplashPresenter;
import com.example.vuki.drustvena_mreza_faks.mvp.presenters.impl.LoginPresenterImpl;
import com.example.vuki.drustvena_mreza_faks.mvp.presenters.impl.RegisterPresenterImpl;
import com.example.vuki.drustvena_mreza_faks.mvp.presenters.impl.SplashPresenterImpl;
import com.example.vuki.drustvena_mreza_faks.mvp.views.LoginView;
import com.example.vuki.drustvena_mreza_faks.mvp.views.RegisterView;
import com.example.vuki.drustvena_mreza_faks.mvp.views.SplashView;

/**
 * Created by Vuki on 4.11.2015..
 */
public class MvpFactory {
    public static SplashPresenter getPresenter(Context context,SplashView view){
        return  new SplashPresenterImpl(view,new TokenInteractorImpl(context));
    }

    public static LoginPresenter getPresenter(LoginView view) {
        return new LoginPresenterImpl(view, new LoginInteractorImpl());
    }

    public static RegistrationPresenter getPresenter(RegisterView view) {
        return new RegisterPresenterImpl(view, new RegistrationInteractorImpl());
    }



}
