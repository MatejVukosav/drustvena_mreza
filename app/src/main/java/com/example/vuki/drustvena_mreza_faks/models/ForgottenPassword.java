package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 4.11.2015..
 */
public class ForgottenPassword implements Serializable {

    @SerializedName("email")
    private String email;

    public ForgottenPassword(String email) {
        this.email = email;
    }
}
