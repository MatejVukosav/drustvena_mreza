package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 2.1.2016..
 */
public class ContactRawInfo implements Serializable {

    @SerializedName("id")
    int id;

    @SerializedName("username")
    String username;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
