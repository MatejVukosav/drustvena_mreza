package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vuki on 24.12.2015..
 */
public class CommentsResponse implements Serializable {

    @SerializedName("comments")
    List<Comment> comments;

    public CommentsResponse(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
