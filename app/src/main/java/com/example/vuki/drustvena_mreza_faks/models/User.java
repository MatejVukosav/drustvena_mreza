package com.example.vuki.drustvena_mreza_faks.models;

import android.support.annotation.Nullable;

import com.example.vuki.drustvena_mreza_faks.enums.UserAuthorized;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 4.11.2015..
 */
public class User implements Serializable{

    @SerializedName("token")
    private String token;

    @SerializedName("id")
    int userId;


    @SerializedName("username")
    String username;


    @SerializedName("email")
    private String email;

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
    @SerializedName("adress")
    private String adress;

    @Nullable
    @SerializedName("city")
    private String city;

    @Nullable
    @SerializedName("country")
    private String country;


    @SerializedName("is_authorized")
    private UserAuthorized authorized;

    @SerializedName("image_url")
    private String profileImage;

    public User() {
    }

    public User( String username, String email, String firstName, String lastName, String middleName, String adress, String city, String country) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.adress = adress;
        this.city = city;
        this.country = country;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    @Nullable
    public String getFirstName() {
        return firstName;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }

    @Nullable
    public String getMiddleName() {
        return middleName;
    }

    @Nullable
    public String getAdress() {
        return adress;
    }

    @Nullable
    public String getCity() {
        return city;
    }

    @Nullable
    public String getCountry() {
        return country;
    }

    public UserAuthorized getAuthorized() {
        return authorized;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(@Nullable String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(@Nullable String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(@Nullable String middleName) {
        this.middleName = middleName;
    }

    public void setAdress(@Nullable String adress) {
        this.adress = adress;
    }

    public void setCity(@Nullable String city) {
        this.city = city;
    }

    public void setCountry(@Nullable String country) {
        this.country = country;
    }

    public void setAuthorized(UserAuthorized authorized) {
        this.authorized = authorized;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getUserId() {
        return userId;
    }
}
