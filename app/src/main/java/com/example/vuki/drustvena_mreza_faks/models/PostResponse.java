package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 6.11.2015..
 */
public class PostResponse implements Serializable {
    @SerializedName("response")
    private Post post;

    public Post getPost() {
        return post;
    }
}
