package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 4.11.2015..
 */
public class Register implements Serializable {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public Register(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
