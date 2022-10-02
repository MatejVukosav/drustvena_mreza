package com.example.vuki.drustvena_mreza_faks.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 3.1.2016..
 */
public class PostStatusRequest implements Serializable{

    @SerializedName("title")
    String title;
    @SerializedName("description")
    String description;
    @NonNull
    @SerializedName("content")
    String content;

    public PostStatusRequest(@NonNull String content) {
        this.title = "Title";
        this.description = "Description";
        this.content = content;
    }
}
