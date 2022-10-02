package com.example.vuki.drustvena_mreza_faks.models;

import com.example.vuki.drustvena_mreza_faks.adapters.HomeRecyclerViewAdapter;
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

    @SerializedName("user_id")
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

    @SerializedName("avatar")
    String userProfilePicture;

    public String getUserProfilePicture() {
        return/* ApiManager.BASE_URL+*/userProfilePicture;
    }

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
        if (contentTypeId == HomeRecyclerViewAdapter.TYPE_IMAGE) {
            return /*ApiManager.BASE_URL +*/ content;
        } else {
            return content;
        }
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

    public HomeFeedOneModel(int iDislike, int iLike, int numOfDislikes, int numOfLikes, String description,
                            String content, String title, String updatedAt, String createdAt, int contentTypeId, int authorId, String author, int bubbleId, int id) {
        this.iDislike = iDislike;
        this.iLike = iLike;
        this.numOfDislikes = numOfDislikes;
        this.numOfLikes = numOfLikes;
        this.description = description;
        this.content = content;
        this.title = title;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.contentTypeId = contentTypeId;
        this.authorId = authorId;
        this.author = author;
        this.bubbleId = bubbleId;
        this.id = id;
    }


}
