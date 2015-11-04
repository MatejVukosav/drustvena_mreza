package com.example.vuki.drustvena_mreza_faks;

import android.app.Application;

import com.example.vuki.drustvena_mreza_faks.network.SocialNetworkService;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;
import com.example.vuki.drustvena_mreza_faks.network.ApiManagerInterface;

/**
 * Created by Vuki on 4.11.2015..
 */
public class SocialNetworkApplication extends Application {

    private static SocialNetworkApplication instance;
    protected ApiManagerInterface apiManagerInterface; //for tests

    public static boolean DEBUG=true;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }

    private void init() {
        ApiManager.getManagerInstance().init();
        apiManagerInterface = ApiManager.getManagerInstance();//for tests
    }

    public static SocialNetworkApplication getInstance() {
        return instance;
    }

    public static SocialNetworkService getApiService() {
        return ApiManager.getManagerInstance().getService();
    }

}


