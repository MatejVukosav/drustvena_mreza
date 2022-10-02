package com.example.vuki.drustvena_mreza_faks.network;

import com.example.vuki.drustvena_mreza_faks.models.AboutUserResponse;
import com.example.vuki.drustvena_mreza_faks.models.BubblesResponse;
import com.example.vuki.drustvena_mreza_faks.models.CheckIfFriends;
import com.example.vuki.drustvena_mreza_faks.models.CommentResponse;
import com.example.vuki.drustvena_mreza_faks.models.CommentsResponse;
import com.example.vuki.drustvena_mreza_faks.models.ContactsResponse;
import com.example.vuki.drustvena_mreza_faks.models.GalleryResponse;
import com.example.vuki.drustvena_mreza_faks.models.HomeFeedResponse;
import com.example.vuki.drustvena_mreza_faks.models.IsEmailAvailable;
import com.example.vuki.drustvena_mreza_faks.models.IsUsernameAvailable;
import com.example.vuki.drustvena_mreza_faks.models.LoginRequest;
import com.example.vuki.drustvena_mreza_faks.models.LoginResponse;
import com.example.vuki.drustvena_mreza_faks.models.PostCommentRequest;
import com.example.vuki.drustvena_mreza_faks.models.PostResponse;
import com.example.vuki.drustvena_mreza_faks.models.PostStatusRequest;
import com.example.vuki.drustvena_mreza_faks.models.RegisterRequest;
import com.example.vuki.drustvena_mreza_faks.models.RegisterResponse;
import com.example.vuki.drustvena_mreza_faks.models.SearchUserRequest;
import com.example.vuki.drustvena_mreza_faks.models.SearchUsersResponse;
import com.example.vuki.drustvena_mreza_faks.models.TimelineResponse;
import com.example.vuki.drustvena_mreza_faks.models.UsersResponse;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface ApiManagerService {


    @GET("api/isUsernameAvailable")
    Call<Boolean> getIsUsernameAvailable(IsUsernameAvailable username);

    @GET("api/isEmailAvailable")
    Call<Boolean> getIsEmailAvailable(IsEmailAvailable email);

    @POST("api/sign-up")
    Call<RegisterResponse> getSignUp(@Body RegisterRequest registerRequest);

    @POST("api/sign-in")
    Call<LoginResponse> postSignIn(@Body LoginRequest loginRequest);

    @POST("api/sign-out")
    Call<Void> postSignOut();

    //searchUsers show all non friends
    @GET("api/user/nonFriends")
    Call<SearchUsersResponse> getAllUsersExceptFriends();

    //add friend
    @POST("api/user/contactRequest")
    Call<Void> addFriend(@Body SearchUserRequest user_id);

    //remove friend
    @POST("api/user/removeContact")
    Call<Void> removeFriend(@Body SearchUserRequest user_id);

    //home news content
    @GET("api/home/feed")
    Call<HomeFeedResponse> getHomeFeed();

    //user wall content
    @GET("api/content/timeline")
    Call<TimelineResponse> getUserContent(@Query("user_id") int userId);
    //
    @GET("api/content/myBubblesList")
    Call<BubblesResponse> getUserBubbles();

    //get all comments
    @GET("api/content/comments/{content_id}")
    Call<CommentsResponse> getComments(@Path("content_id") int content_id);
    //post single comment
    @POST("api/content/comment/{content_id}")
    Call<CommentResponse> postComment(@Path("content_id") int content_id,@Body PostCommentRequest comment);

    //post single status
    @POST("api/content/post/{bubble_id}")
    Call<PostResponse> postStatus(@Path("bubble_id") int bubble_id, @Body PostStatusRequest statusRequest);

    //post like for post
    @POST("api/content/like/{id}")
    Call<Void> postLike(@Path("id") int contentId);

    @GET("api/user/info")
    Call<AboutUserResponse> getUserAbout(@Query("id") int userId);

    @GET("api/user/contacts")
    Call<ContactsResponse> getFriends(@Query("id") int userId);

    @GET("api/user/gallery")
    Call<GalleryResponse> getGalleries(@Query("user_id") int userId);
    //get users for given search term
    @GET("api/user/search")
    Call<UsersResponse> getSearchItem(@Query("term") String searchTerm);

    //check if users are friends
    @GET("api/user/isContact")
    Call<CheckIfFriends> checkIfFriends(@Query("id") int friendId);



    /*******************************************************************************/


    /*@POST("/api/v1/users/login")
    void login(@Body LoginRequest request, Callback<LoginResponse> callback);

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
    void putComment(@Path("post_id") int postId,@Body Comment request,  Callback<CommentResponse> callback);*/


}
