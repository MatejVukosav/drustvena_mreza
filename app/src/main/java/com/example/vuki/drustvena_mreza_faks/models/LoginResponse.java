package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 4.11.2015..
 */
public class LoginResponse implements Serializable {

    @SerializedName("response")
    private User user;

    public User getUser() {
        return user;
    }

}
