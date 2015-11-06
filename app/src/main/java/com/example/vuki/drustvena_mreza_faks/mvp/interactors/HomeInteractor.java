package com.example.vuki.drustvena_mreza_faks.mvp.interactors;

import com.example.vuki.drustvena_mreza_faks.mvp.listeners.PostsListener;

/**
 * Created by Vuki on 6.11.2015..
 */
public interface HomeInteractor {

    void getPosts(PostsListener listener);

}
