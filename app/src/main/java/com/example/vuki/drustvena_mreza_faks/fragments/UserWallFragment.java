package com.example.vuki.drustvena_mreza_faks.fragments;

import android.content.Context;
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
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.helpers.RetrofitHelper;
import com.example.vuki.drustvena_mreza_faks.models.Post;
import com.example.vuki.drustvena_mreza_faks.models.TimelineResponse;
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
public class UserWallFragment extends Fragment {

    public static UserWallFragment newInstance(int pageId) {
        return new UserWallFragment();
    }

    @Bind(R.id.user_wall_recycler_view)
    RecyclerView recyclerView;
    static Context context;
    int mUserId;


    @Override
    public void onResume() {
        super.onResume();
        //  NotesHelpers.logMessage("vv", "USER_WALL");
        apiCall();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_core_user_wall, container, false);
        ButterKnife.bind(this, v);
        context = getContext();

        if (ApiManager.getInstance().getUser() != null) {
            mUserId = ApiManager.getInstance().getUser().getUserId();
            getApiCall(mUserId);
        } else {
            NotesHelpers.toastMessage(context, "Server error: please log out and try again");
        }

        return v;
    }

    private void getApiCall(int userId) {
//TODO dohvatit user wall od usera

        Call<TimelineResponse> timelineResponseCall = ApiManager.getInstance().getService().getUserContent(userId);
        timelineResponseCall.enqueue(new Callback<TimelineResponse>() {
            @Override
            public void onResponse(Response<TimelineResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getPosts() != null) {
                        populateRecyclerView(response.body().getPosts());
                    } else {
                        NotesHelpers.toastMessage(context, "Error " + "Response body is empty");
                    }
                } else {
                    RetrofitHelper.checkCode(response.code(), context);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                NotesHelpers.toastMessage(context, "Failure " + t.getMessage());
                t.printStackTrace();
            }
        });


    }

    private void populateRecyclerView(List<Post> posts) {
        UserWallAdapter adapter = new UserWallAdapter(ApiManager.getInstance().getUser(), posts, getActivity(), true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void apiCall() {


    }

}
