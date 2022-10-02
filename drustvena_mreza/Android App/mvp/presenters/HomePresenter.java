package com.example.vuki.drustvena_mreza_faks.mvp.presenters;

/**
 * Created by Vuki on 6.11.2015..
 */
public interface HomePresenter {

    void getPosts();
    void onPictureClicked(String image);
    void onUserClicked();
    void onCommentNumClicked();
    void onLikeNumClicked();

}
