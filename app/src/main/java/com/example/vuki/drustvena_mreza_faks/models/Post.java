package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vuki on 4.11.2015..
 */
public class Post {

    @SerializedName("id")
    long id;

    @SerializedName("content")
    private String content;


    @SerializedName("created_at")
    private String createdAt;


    @SerializedName("user")
    private User user;

    @SerializedName("likes_num")
    private int likes_num;


    @SerializedName("comment_num")
    private int comment_num;

    @SerializedName("creator")
    private User creator;

    @SerializedName("image_url")
    private String postImage;

    @SerializedName("comments")
    private List<Comment> comments;


    public int getCommentsSize() {
        return comments != null && comments.size() > 0 ? comments.size() : 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLikes_num() {
        return likes_num;
    }

    public void setLikes_num(int likes_num) {
        this.likes_num = likes_num;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
