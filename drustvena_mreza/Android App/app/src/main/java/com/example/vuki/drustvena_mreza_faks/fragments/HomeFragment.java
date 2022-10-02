package com.example.vuki.drustvena_mreza_faks.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.adapters.HomeRecyclerViewAdapter;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.helpers.RetrofitHelper;
import com.example.vuki.drustvena_mreza_faks.listeners.OnImageClickListener;
import com.example.vuki.drustvena_mreza_faks.models.HomeFeedOneModel;
import com.example.vuki.drustvena_mreza_faks.models.HomeFeedResponse;
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
public class HomeFragment extends Fragment implements OnImageClickListener {
    private static String TAG;
    Context context;

    public static HomeFragment newInstance(int pageId) {
        return new HomeFragment();
    }


    private HomeRecyclerViewAdapter adapter;
    @Bind(R.id.core_home_empty_view)
    TextView emptyList;

    @Bind(R.id.core_home_recyclerView)
    RecyclerView recyclerView;


    @Bind(R.id.bigger_picture)
    ImageView biggerPicture;


    @Override
    public void onResume() {
        super.onResume();

        Call<HomeFeedResponse> timelineResponseCall = ApiManager.getInstance().getService().getHomeFeed();
        timelineResponseCall.enqueue(new Callback<HomeFeedResponse>() {
            @Override
            public void onResponse(Response<HomeFeedResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getHomeFeedOneModel() != null) {
                        populateRecycler(response.body().getHomeFeedOneModel());
                    } else {
                        NotesHelpers.toastMessage(context, getResources().getString(R.string.error_something_is_wrong));
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                NotesHelpers.toastMessage(context, getResources().getString(R.string.error_something_is_wrong));

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_core_home, container, false);
        ButterKnife.bind(this, v);
        context = getContext();
        TAG = getClass().getSimpleName();

        Call<HomeFeedResponse> timelineResponseCall = ApiManager.getInstance().getService().getHomeFeed();
        timelineResponseCall.enqueue(new Callback<HomeFeedResponse>() {
            @Override
            public void onResponse(Response<HomeFeedResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getHomeFeedOneModel() != null) {
                        populateRecycler(response.body().getHomeFeedOneModel());
                    } else {
                        NotesHelpers.toastMessage(context, getResources().getString(R.string.error_something_is_wrong));
                    }
                } else {
                    RetrofitHelper.checkCode(response.code(), context);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                NotesHelpers.toastMessage(context, getResources().getString(R.string.error_something_is_wrong));
            }
        });

        return v;
    }


    private void populateRecycler(List<HomeFeedOneModel> posts) {
        if (posts.size() == 0) {
            emptyList.setVisibility(View.VISIBLE);
        } else {
            emptyList.setVisibility(View.GONE);
        }

        recyclerView.setVisibility(View.VISIBLE);
        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(posts, getContext(),(OnImageClickListener)this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));


        biggerPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biggerPicture.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void OnImageClick(ImageView imageView) {
        biggerPicture.setImageDrawable(imageView.getDrawable());
        biggerPicture.setVisibility(View.VISIBLE);
        biggerPicture.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent_85_percent_black));
    }
}
