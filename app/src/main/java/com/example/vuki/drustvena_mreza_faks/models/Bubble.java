package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 24.12.2015..
 */
public class Bubble implements Serializable {

    @SerializedName("user_id")
    int userId;

    @SerializedName("id")
    int id;

    @SerializedName("bubble_type_id")
    int bubbleTypeId;

    @SerializedName("created_at")
    String createdAt;

    @SerializedName("updated_at")
    String updatedAt;

    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    public Bubble(int userId, int id, int bubbleTypeId, String createdAt, String updatedAt, String title, String description) {
        this.userId = userId;
        this.id = id;
        this.bubbleTypeId = bubbleTypeId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.title = title;
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public int getBubbleTypeId() {
        return bubbleTypeId;
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

    public String getDescription() {
        return description;
    }
}
