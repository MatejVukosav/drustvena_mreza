package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 6.11.2015..
 */
public class PostResponse implements Serializable {


    @SerializedName("response")
     Post post;

    @SerializedName("error")
     String error;


    public Post getPost() {
        return post;
    }

    public String getError() {
        return error;
    }
}
