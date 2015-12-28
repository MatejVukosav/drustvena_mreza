package com.example.vuki.drustvena_mreza_faks.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.adapters.UserWallAdapter;
import com.example.vuki.drustvena_mreza_faks.listeners.ItemClickListener;
import com.example.vuki.drustvena_mreza_faks.mock.MockUsers;
import com.example.vuki.drustvena_mreza_faks.models.Post;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Vuki on 4.11.2015..
 */
public class UserWallFragment extends Fragment {

    public static UserWallFragment newInstance() {
        return new UserWallFragment();
    }

    @Bind(R.id.user_wall_recycler_view)
    RecyclerView recyclerView;

    private ItemClickListener<Post> itemClickListener = new ItemClickListener<Post>() {
        @Override
        public void onItemClick(Post item) {

           /* Intent intent=new Intent(getActivity(), ItemDetailActivity.class);
            intent.putExtra(ItemDetailActivity.TAG, item);
            startActivity(intent);*/
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        apiCall();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_core_user_wall, container, false);
        ButterKnife.bind(this, v);

        populateRecyclerView();
        return v;
    }

    private void populateRecyclerView() {
        UserWallAdapter adapter = new UserWallAdapter(ApiManager.getInstance().getUser(),MockUsers.getFivePosts(15).getPosts(), itemClickListener, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void apiCall(){



    }

}
