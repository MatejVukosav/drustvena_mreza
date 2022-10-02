package com.example.vuki.drustvena_mreza_faks.network;

import com.example.vuki.drustvena_mreza_faks.enums.UserAuthorized;
import com.example.vuki.drustvena_mreza_faks.models.User;
import com.example.vuki.drustvena_mreza_faks.network.deserializers.DateDeserializers;
import com.example.vuki.drustvena_mreza_faks.network.deserializers.UserAuthorizedDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.Date;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Vuki on 4.11.2015..
 */
public class ApiManager implements ApiManagerInterface {
    private static final String TAG = "Network";

   // public static final String BASE_URL = "http://192.168.1.5:8080";
    // public static final String BASE_URL = "http://192.168.1.10:8000";
    //public static final String BASE_URL = "http://bubbles.vukilab.com:8080/";

    public static final String BASE_URL = "https://www.bubbles.com.hr";
    //public static final String BASE_URL = "http://10.129.36.202:8080";

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(UserAuthorized.class, new UserAuthorizedDeserializer())
            .registerTypeAdapter(Date.class, new DateDeserializers())
            .create();


    public static Gson getGson() {
        return gson;
    }

    private static ApiManager managerInstance;
    private ApiManagerService service;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public synchronized static ApiManager getInstance() {
        if (managerInstance == null) {
            managerInstance = new ApiManager();
        }
        return managerInstance;
    }

    private ApiManager() {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new ReceivedCookiesInterceptor());
        client.interceptors().add(new AddHeaderInterceptor());
      // client.interceptors().add(new LoggingInterceptor());


        Retrofit retrofit = new Retrofit.Builder()
                //.addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(client)
                .build();
        service = retrofit.create(ApiManagerService.class);
    }


    @Override
    public ApiManagerService getService() {
        return service;
    }
}
