package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 27.12.2015..
 */
public class HomeFeedOneModel implements Serializable {


    @SerializedName("id")
    private int id;

    @SerializedName("bubble_id")
    private int bubbleId;

    @SerializedName("username")
    private String author;

    @SerializedName("author_id")
    private int authorId;

    @SerializedName("content_type_id")
    private int contentTypeId;


    @SerializedName("created_at")
    private String createdAt;


    @SerializedName("updated_at")
    private String updatedAt;


    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("description")
    private String description;

    @SerializedName("likes")
    int numOfLikes;
    @SerializedName("dislikes")
    int numOfDislikes;

    //0 if liked
    @SerializedName("iLike")
    int iLike;

    @SerializedName("iDislike")
    int iDislike;

    public int getId() {
        return id;
    }

    public int getBubbleId() {
        return bubbleId;
    }

    public String getAuthor() {
        return author;
    }

    public int getContentTypeId() {
        return contentTypeId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDescription() {
        return description;
    }

    public int getNumOfLikes() {
        return numOfLikes;
    }

    public int getNumOfDislikes() {
        return numOfDislikes;
    }

    public int getiLike() {
        return iLike;
    }

    public int getiDislike() {
        return iDislike;
    }

    public void setiLike(int iLike) {
        this.iLike = iLike;
    }

    public void setiDislike(int iDislike) {
        this.iDislike = iDislike;
    }

    public int getAuthorId() {
        return authorId;
    }
}
