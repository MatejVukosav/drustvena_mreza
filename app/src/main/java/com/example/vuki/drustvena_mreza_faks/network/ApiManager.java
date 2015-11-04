package com.example.vuki.drustvena_mreza_faks.network;

import android.util.Log;

import com.example.vuki.drustvena_mreza_faks.enums.UserAuthorized;
import com.example.vuki.drustvena_mreza_faks.network.deserializers.UserAuthorizedDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.net.CookieManager;
import java.util.concurrent.Executor;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by Vuki on 4.11.2015..
 */
public class ApiManager implements ApiManagerInterface {
    private static final String TAG = "Network";

    public static final String API_ENDPOINT = "https://";

    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(UserAuthorized.class, new UserAuthorizedDeserializer())
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .create();

    private static final RestAdapter.Log LOG = new RestAdapter.Log() {
        @Override
        public void log(String message) {
            Log.d(TAG, message);
        }
    };


    private static ApiManager managerInstance;
    private SocialNetworkService service;

    public synchronized static ApiManager getManagerInstance() {
        if (managerInstance == null) {
            managerInstance = new ApiManager();
        }
        return managerInstance;
    }

    private ApiManager() {
    }

    public void init() {
        OkHttpClient okHttpClient = new OkHttpClient().setCookieHandler(new CookieManager());
        setup(null, null, new OkClient(okHttpClient));
    }

    public void init(Executor httpExecutor,Executor callbackExecutor,OkClient okClient){
        setup(httpExecutor,callbackExecutor,okClient);
    }

    public void setup(Executor httpExecutor, Executor callbackExecutor, OkClient okClient) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setClient(okClient)
                .setEndpoint(API_ENDPOINT)
                .setRequestInterceptor(new com.example.vuki.drustvena_mreza_faks.network.Interceptor())
                .setConverter(new GsonConverter(gson))
                .setLog(LOG)
                .setLogLevel(RestAdapter.LogLevel.FULL);

        if (httpExecutor != null && callbackExecutor != null) {
            builder.setExecutors(httpExecutor, callbackExecutor);
        }

        service = builder.build().create(SocialNetworkService.class);
    }


    @Override
    public SocialNetworkService getService() {
        return service;
    }
}
