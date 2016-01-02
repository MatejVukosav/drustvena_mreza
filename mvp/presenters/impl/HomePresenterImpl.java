package com.example.vuki.drustvena_mreza_faks.mvp.presenters.impl;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.models.Post;
import com.example.vuki.drustvena_mreza_faks.mvp.interactors.HomeInteractor;
import com.example.vuki.drustvena_mreza_faks.mvp.listeners.PostsListener;
import com.example.vuki.drustvena_mreza_faks.mvp.presenters.HomePresenter;
import com.example.vuki.drustvena_mreza_faks.mvp.views.HomeView;

import java.util.List;

/**
 * Created by Vuki on 6.11.2015..
 */
public class HomePresenterImpl implements HomePresenter {

    private HomeView view;
    private HomeInteractor homeInteractor;

    public HomePresenterImpl(HomeView view, HomeInteractor homeInteractor) {
        this.view = view;
        this.homeInteractor = homeInteractor;
    }



    private PostsListener listener=new PostsListener() {
        @Override
        public void onPostsReceived(List<Post> posts) {
            view.hideProgress();
            if(posts!=null && posts.size()>0){
                view.onPostsListReceived(posts);
            }else{
                view.onPostsListEmpty();
            }
        }

        @Override
        public void onTokenExpired() {
            view.hideProgress();
            view.onTokenExpired();
        }

        @Override
        public void onError(String error) {
            view.hideProgress();
            view.showError(R.string.error_download);
        }
    };

    @Override
    public void getPosts() {
        view.showProgress();
        homeInteractor.getPosts(listener);
    }

    @Override
    public void onPictureClicked(String image) {
        view.showProgress();
    }

      @Override
    public void onUserClicked() {
        view.showProgress();
    }

    @Override
    public void onCommentNumClicked() {
        view.showProgress();
    }

    @Override
    public void onLikeNumClicked() {
        view.showProgress();
    }


}
