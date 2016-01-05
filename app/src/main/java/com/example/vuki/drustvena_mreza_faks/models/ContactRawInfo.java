package com.example.vuki.drustvena_mreza_faks.models;

import com.example.vuki.drustvena_mreza_faks.network.ApiManager;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 2.1.2016..
 */
public class ContactRawInfo implements Serializable {

    @SerializedName("id")
    int userId;

    @SerializedName("username")
    String username;

    @SerializedName("avatar")
    String userProfilePicture;

    @SerializedName("city")
    String city;

    @SerializedName("country_id")
    String country;

    public String getCity() {
        if (city != null) {
            return city;
        } else {
            return "";
        }
    }

    public String getCountry() {
        if (country != null) {
            return country;
        } else {
            return "";
        }
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getUserProfilePicture() {
        return ApiManager.BASE_URL + userProfilePicture;
    }
}
