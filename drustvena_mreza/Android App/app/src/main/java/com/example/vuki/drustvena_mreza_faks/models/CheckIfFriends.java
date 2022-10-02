package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 4.1.2016..
 */
public class CheckIfFriends implements Serializable {

    @SerializedName("response")
    boolean friends;

    public boolean isFriends() {
        return friends;
    }
}
