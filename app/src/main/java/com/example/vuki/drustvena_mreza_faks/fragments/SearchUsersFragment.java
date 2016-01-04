package com.example.vuki.drustvena_mreza_faks.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.adapters.AddFriendsAdapter;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.helpers.RetrofitHelper;
import com.example.vuki.drustvena_mreza_faks.models.SearchUsersResponse;
import com.example.vuki.drustvena_mreza_faks.models.User;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Vuki on 4.11.2015..
 */
public class SearchUsersFragment extends Fragment {

    Context context;

    public static SearchUsersFragment newInstance(int pageId) {
        return new SearchUsersFragment();
    }

    @Bind(R.id.search_user_recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.empty_list)
    TextView emptyList;

    @Override
    public void onResume() {
      //  NotesHelpers.logMessage("vv", "SEARCH USERS");


        super.onResume();

        Call<SearchUsersResponse> searchUsersResponseCall = ApiManager.getInstance().getService().getAllUsersExceptFriends();
        searchUsersResponseCall.enqueue(new Callback<SearchUsersResponse>() {
            @Override
            public void onResponse(Response<SearchUsersResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getUsersList() != null) {
                        List<User> usersList = response.body().getUsersList();
                        populateRecyclerView(usersList);
                    } else {
                        NotesHelpers.toastMessage(context, getResources().getString(R.string.error_something_went_wrong));
                    }
                } else {
                    RetrofitHelper.checkCode(response.code(),context );
                }
            }

            @Override
            public void onFailure(Throwable t) {
                NotesHelpers.toastMessage(context, "Failure "+ t.getMessage());
                NotesHelpers.logMessage("ADD FRIENDS", "add friends failure");
                t.printStackTrace();

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_core_search_users, container, false);
        ButterKnife.bind(this, v);
        context = getContext();

        return v;
    }

    private void populateRecyclerView(List<User> users) {
        if(users.size()==0){
            emptyList.setVisibility(View.VISIBLE);
        }else{
            emptyList.setVisibility(View.INVISIBLE);
        }

        AddFriendsAdapter adapter = new AddFriendsAdapter(getContext(), users,false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


}
