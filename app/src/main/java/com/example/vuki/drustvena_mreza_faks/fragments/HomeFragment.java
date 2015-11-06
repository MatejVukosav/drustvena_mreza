package com.example.vuki.drustvena_mreza_faks.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.SocialNetworkApplication;
import com.example.vuki.drustvena_mreza_faks.activities.LoginActivity;
import com.example.vuki.drustvena_mreza_faks.adapters.CoreHomeRecyclerViewAdapter;
import com.example.vuki.drustvena_mreza_faks.helpers.MvpFactory;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.listeners.ItemClickListener;
import com.example.vuki.drustvena_mreza_faks.mock.MockUsers;
import com.example.vuki.drustvena_mreza_faks.models.Post;
import com.example.vuki.drustvena_mreza_faks.mvp.presenters.HomePresenter;
import com.example.vuki.drustvena_mreza_faks.mvp.views.HomeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Vuki on 4.11.2015..
 */
public class HomeFragment extends Fragment implements HomeView {
    private static String TAG;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    HomePresenter presenter;
    private CoreHomeRecyclerViewAdapter adapter;
    @Bind(R.id.core_home_empty_view)
    TextView emptyList;

    @Bind(R.id.core_home_recyclerView)
    RecyclerView recyclerView;

    private ItemClickListener<Post> itemClickListener = new ItemClickListener<Post>() {
        @Override
        public void onItemClick(Post item) {
            presenter.onPictureClicked(item.getPostImage());
           /* Intent intent=new Intent(getActivity(), ItemDetailActivity.class);
            intent.putExtra(ItemDetailActivity.TAG, item);
            startActivity(intent);*/
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_core_home, container, false);
        ButterKnife.bind(this, v);
        TAG = getClass().getSimpleName();

        presenter = MvpFactory.getPresenter(this);
        presenter.getPosts();

        return v;
    }



    private void populateRecycler(List<Post> posts) {
        recyclerView.setVisibility(View.VISIBLE);
        CoreHomeRecyclerViewAdapter adapter = new CoreHomeRecyclerViewAdapter(posts, itemClickListener, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    }


    @Override
    public void onPostsListReceived(List<Post> posts) {
        emptyList.setVisibility(View.GONE);
        if (SocialNetworkApplication.DEBUG) {
            populateRecycler(MockUsers.getFivePosts(15).getPosts());
        } else {
            populateRecycler(posts);
        }
    }


    @Override
    public void onPostsListEmpty() {
        emptyList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTokenExpired() {
        Intent intent=new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(@StringRes int error) {
        showError(error);
    }

    @Override
    public void onPictureClicked() {
        NotesHelpers.toastMessage(getContext(),"picture clicked");
    }

    @Override
    public void onUserClicked() {
        NotesHelpers.toastMessage(getContext(),"ucer clicked");
    }

    @Override
    public void onCommentNumClicked() {
        NotesHelpers.toastMessage(getContext(),"comment num clicked");
    }

    @Override
    public void onLikeNumClicked() {
        NotesHelpers.toastMessage(getContext(),"like num clicked");
    }

}
