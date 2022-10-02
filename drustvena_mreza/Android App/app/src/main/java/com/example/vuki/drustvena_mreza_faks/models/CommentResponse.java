package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 3.1.2016..
 */
public class CommentResponse implements Serializable {

    @SerializedName("response")
    Comment comment;

    public Comment getComment() {
        return comment;
    }
}
