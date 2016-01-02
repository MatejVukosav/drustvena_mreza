package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 4.11.2015..
 */
public class Comment implements Serializable {


    @SerializedName("id")
    int id;
    @SerializedName("content_id")
    int contentId;
    @SerializedName("user_id")
    int userId;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("updated_at")
    String updatedAt;
    @SerializedName("comment")
    String message;
    @SerializedName("first_name")
    String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("middle_name")
    String middleName;
    @SerializedName("username")
    String username;

    public Comment() {
    }

    public Comment(int userId, String message, String firstName, String lastName, String middleName, String username) {
        this.userId = userId;
        this.message = message;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.username = username;
    }

    public long getId() {
        return id;
    }


    public int getUserId() {
        return userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getMessage() {
        return message;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setComment(String comment) {
        this.message = comment;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public int getContentId() {
        return contentId;
    }
}
