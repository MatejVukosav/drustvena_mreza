package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vuki on 27.12.2015..
 */
public class SearchUsersResponse implements Serializable {

    @SerializedName("response")
    List<User> usersList;

    public List<User> getUsersList() {
        return usersList;
    }
}
