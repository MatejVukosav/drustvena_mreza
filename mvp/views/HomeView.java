package com.example.vuki.drustvena_mreza_faks.mvp.views;

import com.example.vuki.drustvena_mreza_faks.models.Post;

import java.util.List;

/**
 * Created by Vuki on 4.11.2015..
 */
public interface HomeView extends BaseView {

    void onPostsListReceived(List<Post>posts);
    void onPictureClicked();
    void onUserClicked();
    void onCommentNumClicked();
    void onLikeNumClicked();
    void onPostsListEmpty();
    void onTokenExpired();
}
