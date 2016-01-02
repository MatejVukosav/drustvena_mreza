package com.example.vuki.drustvena_mreza_faks.mvp.listeners;

import com.example.vuki.drustvena_mreza_faks.models.Post;

import java.util.List;

/**
 * Created by Vuki on 6.11.2015..
 */
public interface PostsListener {
    void onPostsReceived(List<Post> posts);
    void onTokenExpired();
    void onError(String error);
}
