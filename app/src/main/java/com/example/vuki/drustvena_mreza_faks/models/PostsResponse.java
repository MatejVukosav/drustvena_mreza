package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 6.11.2015..
 */
public class PostsResponse implements Serializable {

    @SerializedName("response")
    Posts posts;

    public Posts getPosts() {
        return posts;
    }
}
