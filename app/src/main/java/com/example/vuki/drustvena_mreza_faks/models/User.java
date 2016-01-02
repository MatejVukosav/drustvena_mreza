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
    @SerializedName("country_id")
    private String countryId;

    @Nullable
    @SerializedName("relationshio_status_id")
    private int relationshipStatusId;


    @Nullable
    @SerializedName("gender_id")
    private int genderId;


    @SerializedName("is_authorized")
    private UserAuthorized authorized;

    @SerializedName("image_url")
    private String profileImage;

    public User() {
    }

    public User( String username, String email, String firstName, String lastName, String middleName, String adress, String city, String countryId) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.adress = adress;
        this.city = city;
        this.countryId = countryId;
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
        if(firstName!=null) {
            return firstName;
        }else{
            return "";
        }
    }

    @Nullable
    public String getLastName() {
        if(lastName!=null) {
            return lastName;
        }else{
            return "";
        }
    }

    @Nullable
    public String getMiddleName() {
        if(middleName!=null) {
            return middleName;
        }else{
            return "";
        }
    }

    @Nullable
    public String getAdress() {
        if(adress!=null) {
            return adress;
        }else{
            return "";
        }
    }

    @Nullable
    public String getCity() {
        if(city!=null) {
            return city;
        }else{
            return "";
        }
    }

    @Nullable
    public String getCountryId() {
        if(countryId !=null) {
            return countryId;
        }else{
            return "";
        }
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

    public void setCountryId(@Nullable String countryId) {
        this.countryId = countryId;
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

    @Nullable
    public int getRelationshipStatusId() {
        return relationshipStatusId;
    }

    @Nullable
    public int getGenderId() {
        return genderId;
    }
}
