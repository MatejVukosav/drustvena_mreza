package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 27.12.2015..
 */
public class SearchUserRequest implements Serializable {
    @SerializedName("user_id")
    int userId;

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

