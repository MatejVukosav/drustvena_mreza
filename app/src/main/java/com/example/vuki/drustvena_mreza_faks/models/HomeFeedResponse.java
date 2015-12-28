package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vuki on 27.12.2015..
 */
public class HomeFeedResponse implements Serializable {

    @SerializedName("contents")
    List<HomeFeedOneModel> homeFeedOneModel;

    public List<HomeFeedOneModel> getHomeFeedOneModel() {
        return homeFeedOneModel;
    }
}
