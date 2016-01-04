package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vuki on 3.1.2016..
 */
public class GalleryResponse implements Serializable {

    @SerializedName("response")
    List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }
}
