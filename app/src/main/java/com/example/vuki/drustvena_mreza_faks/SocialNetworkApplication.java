package com.example.vuki.drustvena_mreza_faks;

import android.app.Application;
import android.content.Context;

import com.example.vuki.drustvena_mreza_faks.network.ApiManagerService;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;
import com.example.vuki.drustvena_mreza_faks.network.ApiManagerInterface;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

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
        configurateDefaultImageLoader(this);
        init();
    }

    private void init() {
        ApiManager.getInstance();
    }


    public static ApiManagerService getApiService() {
        return ApiManager.getInstance().getService();
    }

    public static void configurateDefaultImageLoader(Context context){
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY-2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();

        ImageLoader.getInstance().init(config);
    }

}


