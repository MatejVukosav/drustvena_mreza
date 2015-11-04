package com.example.vuki.drustvena_mreza_faks.models;

import com.example.vuki.drustvena_mreza_faks.enums.UserAuthorized;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vuki on 4.11.2015..
 */
public class User {

    @SerializedName("token")
    private String token;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("email")
    private String email;

    @SerializedName("is_authorized")
    private UserAuthorized authorized;

    @SerializedName("image_url")
    private String postImage;

    public String getPostImage() {
        return postImage;
    }

    public String getToken() {
        return token;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public UserAuthorized getAuthorized() {
        return authorized;
    }


/*
    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public boolean isHasCard() {
        return hasCard;
    }

    public boolean isHasFacebook() {
        return hasFacebook;
    }

    public boolean isHelpEnabled() {
        return helpEnabled;
    }

    public String getName() {
        return name;
    }



    public String getSurname() {
        return surname;
    }

    public int getUserId() {
        return userId;
    }

    private int userId;
    private String name;
    private String surname;

    public String getUsername() {
        return username;
    }

    private String username;
    private String picture;
    private String email;
    private String gender;
    private boolean hasCard;
    private boolean hasFacebook;
    private String birthday;
    private boolean helpEnabled;
    private List<User> followedUsers;
    private List<User> disfollowedUsers;
    private List<Post> likedPosts;


    public List<User> getFollowedUsers() {
        return followedUsers;
    }

    public void setFollowedUsers(List<User> followedUsers) {
        this.followedUsers = followedUsers;
    }

    public List<User> getDisfollowedUsers() {
        return disfollowedUsers;
    }

    public void setDisfollowedUsers(List<User> disfollowedUsers) {
        this.disfollowedUsers = disfollowedUsers;
    }

    public boolean isHelp() {
        return helpEnabled;
    }

    public void setHelp(boolean helpEnabled) {
        this.helpEnabled = helpEnabled;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getAge() {
        if (this.birthday != null) {
            try { //2.4.1992
                String[] data = this.birthday.split("\\.");
                int birthYear = Integer.parseInt(data[2]);
                return 2015 - birthYear;
            } catch (Exception e) {
                NotesHelpers.logMessage("User: " + userId, Resources.getSystem().getString(R.string.error_get_age));
                e.printStackTrace();
                return 20;
            }
        }
        return 20;
    }




    public User(int userId, String name, String surname, String username, String email, String birthday, boolean helpEnabled) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.birthday = birthday;
        this.helpEnabled=helpEnabled;
    }*/

}
