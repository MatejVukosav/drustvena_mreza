package com.example.vuki.drustvena_mreza_faks;

import android.app.Application;

import com.example.vuki.drustvena_mreza_faks.network.ApiManagerService;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;
import com.example.vuki.drustvena_mreza_faks.network.ApiManagerInterface;

/**
 * Created by Vuki on 4.11.2015..
 */
public class SocialNetworkApplication extends Application {

    private static SocialNetworkApplication instance;

    public static SocialNetworkApplication getInstance() {
        return instance;
    }
    protected ApiManagerInterface apiManagerInterface; //for tests

    public static boolean DEBUG=true;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }

    private void init() {
        ApiManager.getInstance();
    }


    public static ApiManagerService getApiService() {
        return ApiManager.getInstance().getService();
    }

}


