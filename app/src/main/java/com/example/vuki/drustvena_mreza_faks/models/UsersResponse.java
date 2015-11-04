package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vuki on 4.11.2015..
 */
public class UsersResponse implements Serializable{

    @SerializedName("response")
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }
}
