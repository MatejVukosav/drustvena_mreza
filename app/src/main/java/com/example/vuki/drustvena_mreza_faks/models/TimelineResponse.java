package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vuki on 24.12.2015..
 */
public class TimelineResponse implements Serializable {

    @SerializedName("posts")
    List<Post> post;

    public List<Post> getPost() {
        return post;
    }
}
