package com.example.vuki.drustvena_mreza_faks.mvp.interactors.impl;

import com.example.vuki.drustvena_mreza_faks.SocialNetworkApplication;
import com.example.vuki.drustvena_mreza_faks.mock.MockUsers;
import com.example.vuki.drustvena_mreza_faks.models.PostsResponse;
import com.example.vuki.drustvena_mreza_faks.mvp.interactors.HomeInteractor;
import com.example.vuki.drustvena_mreza_faks.mvp.listeners.PostsListener;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Vuki on 6.11.2015..
 */
public class HomeInteractorImpl implements HomeInteractor {
    private PostsListener postsListener;

    @Override
    public void getPosts(PostsListener listener) {
        postsListener = listener;
        if(SocialNetworkApplication.DEBUG){
            postsListener.onPostsReceived(MockUsers.getFivePosts(15).getPosts());
        }else{
            SocialNetworkApplication.getApiService().getPosts(postsResponseCallback);
        }


    }


    private Callback<PostsResponse> postsResponseCallback = new BaseCallback<PostsResponse>() {

        @Override
        public void success(PostsResponse postsResponse, Response response) {
            postsListener.onPostsReceived(postsResponse.getPosts().getPosts());
        }

        @Override
        public void onTokenExpired() {
            postsListener.onTokenExpired();
        }

        @Override
        public void error(RetrofitError error) {
            postsListener.onError(error.getMessage());
        }
    };


}
