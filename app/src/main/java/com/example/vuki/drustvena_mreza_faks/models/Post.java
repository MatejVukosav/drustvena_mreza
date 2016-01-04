package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 4.11.2015..
 */
public class Post implements Serializable{

    /*
        1. for tekstual posts
        2. for image
     */

    @SerializedName("id")
    int id;

    @SerializedName("bubble_id")
    int bubbleId;

    @SerializedName("content_type_id")
    int contentTypeId;


    @SerializedName("created_at")
    String createdAt;


    @SerializedName("updated_at")
    String updatedAt;


    @SerializedName("title")
    String title;

    @SerializedName("content")
    String content;

    @SerializedName("description")
    String description;

    @SerializedName("likes")
    int numOfLikes;
    @SerializedName("dislikes")
    int numOfDislikes;

    @SerializedName("iLike")
    int iLike;

    @SerializedName("iDislike")
    int iDislike;

    @SerializedName("username")
    String username;

    @SerializedName("avatar")
    String profileImage;

    public int getiDislike() {
        return iDislike;
    }

    public String getUsername() {
        return username;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public int getContentTypeId() {
        return contentTypeId;
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

    public int isiDislike() {
        return iDislike;
    }

    public int getId() {
        return id;
    }

    public int getBubbleId() {
        return bubbleId;
    }

    public int getContetnTypeId() {
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

    public void setiLike(int iLike) {
        this.iLike = iLike;
    }

    public void setiDislike(int iDislike) {
        this.iDislike = iDislike;
    }

    public Post(int id, int bubbleId, int contetnTypeId, String createdAt, String updatedAt, String title, String content, String description) {
        this.id = id;
        this.bubbleId = bubbleId;
        this.contentTypeId = contetnTypeId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.title = title;
        this.content = content;
        this.description = description;
    }

    public Post() {
    }
}
