package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 2.1.2016..
 */
public class CommentRequest implements Serializable {

    @SerializedName("content_id")
    int contentId;

    public CommentRequest(int contentId) {
        this.contentId = contentId;
    }
}
