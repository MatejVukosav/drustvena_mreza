package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 2.1.2016..
 */
public class AboutUserResponse implements Serializable {

    @SerializedName("response")
    User user;

    public User getUser() {
        return user;
    }
}
