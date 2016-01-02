package com.example.vuki.drustvena_mreza_faks.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 24.12.2015..
 */
public class RegisterRequest implements Serializable {

    @NonNull
    @SerializedName("username")
    String username="";

    @NonNull
    @SerializedName("password")
    String password="";

    @NonNull
    @SerializedName("email")
    private String email="";

    @Nullable
    @SerializedName("firstName")
    private String firstName;

    @Nullable
    @SerializedName("lastName")
    private String lastName;

    @Nullable
    @SerializedName("middleName")
    private String middleName;

    @Nullable
    @SerializedName("address")
    private String address;

    @Nullable
    @SerializedName("city")
    private String city;

    @Nullable
    @SerializedName("country")
    private String country;

    public RegisterRequest(@NonNull String username, @NonNull String password, @NonNull String email, String firstName, String lastName, String middleName, String address, String city, String country) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.address = address;
        this.city = city;
        this.country = country;
    }
}
