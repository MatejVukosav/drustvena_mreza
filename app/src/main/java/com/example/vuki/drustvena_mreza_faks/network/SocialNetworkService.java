package com.example.vuki.drustvena_mreza_faks.network;

import com.example.vuki.drustvena_mreza_faks.models.Comment;
import com.example.vuki.drustvena_mreza_faks.models.CommentResponse;
import com.example.vuki.drustvena_mreza_faks.models.ForgottenPasswordResponse;
import com.example.vuki.drustvena_mreza_faks.models.LoginRequest;
import com.example.vuki.drustvena_mreza_faks.models.LoginResponse;
import com.example.vuki.drustvena_mreza_faks.models.PostsResponse;
import com.example.vuki.drustvena_mreza_faks.models.Register;
import com.example.vuki.drustvena_mreza_faks.models.RegisterResponse;
import com.example.vuki.drustvena_mreza_faks.models.UserResponse;
import com.example.vuki.drustvena_mreza_faks.models.UsersResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface SocialNetworkService {


    @POST("/api/v1/users/login")
    void login(@Body LoginRequest request, Callback<LoginResponse> callback);

    @POST("/api/v1/users/register")
    void postRegister(@Body Register register, Callback<RegisterResponse> callback);

    @POST("/api/v1/users/register")
    void postResetPassword(@Body String email, Callback<ForgottenPasswordResponse> callback);

    @GET("/api/v1/posts")
    void getPosts(Callback<PostsResponse> callback);

    @GET("/api/v1/posts")
    void getUsers(Callback<UsersResponse> callback);

    @GET("/api/v1/posts")
    void getUser(Callback<UserResponse> callback);

    @GET("/api/v1/posts")
    void getInboxUsers(Callback<UsersResponse> callback);

    @GET("/api/v1/posts/{post}/comments")
    void getComments(@Path("post") int postId, @Query("page") int page, @Query("per_page") int itemsPerPage, Callback<CommentResponse> callback);

    @POST("/api/v1/posts/{post_id}/comments")
    void putComment(@Path("post_id") int postId,@Body Comment request,  Callback<CommentResponse> callback);


}
