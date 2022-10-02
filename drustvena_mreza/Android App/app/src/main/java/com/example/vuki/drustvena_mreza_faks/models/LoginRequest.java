package com.example.vuki.drustvena_mreza_faks.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 4.11.2015..
 */
public class LoginRequest implements Serializable {

    @NonNull
    @SerializedName("username")
    private String username="";

    @NonNull
    @SerializedName("password")
    private String password="";

    public LoginRequest(@NonNull String username, @NonNull String password) {
        this.username = username;
        this.password = password;
    }
}
