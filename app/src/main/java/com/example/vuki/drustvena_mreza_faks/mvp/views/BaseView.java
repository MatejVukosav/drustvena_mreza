package com.example.vuki.drustvena_mreza_faks.mvp.views;

import android.support.annotation.StringRes;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface BaseView {

    void showProgress();

    void hideProgress();

    void showError(@StringRes int error);
}
