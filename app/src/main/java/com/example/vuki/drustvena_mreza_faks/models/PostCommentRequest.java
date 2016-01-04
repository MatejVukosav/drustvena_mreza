package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 3.1.2016..
 */
public class PostCommentRequest implements Serializable {

    @SerializedName("comment")
    String comment;

    public PostCommentRequest(String comment) {
        this.comment = comment;
    }
}
