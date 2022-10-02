package com.example.vuki.drustvena_mreza_faks.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.adapters.UserWallAdapter;
import com.example.vuki.drustvena_mreza_faks.helpers.BundleKeys;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.models.AboutUserResponse;
import com.example.vuki.drustvena_mreza_faks.models.Post;
import com.example.vuki.drustvena_mreza_faks.models.TimelineResponse;
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
public class UserWallFromFriend extends AppCompatActivity {

    @Bind(R.id.user_wall_from_friend_recycler_view)
    RecyclerView recyclerView;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wall);
        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            int userId = b.getInt(BundleKeys.FRIEND_USER_ID);
            getPostsApiCall(userId);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Friend wall");
        }

        context=this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void getPostsApiCall(final int userId) {

        //TODO dohvatit sadrzaj od frenda

        Call<TimelineResponse> timelineResponseCall = ApiManager.getInstance().getService().getUserContent(userId);
        timelineResponseCall.enqueue(new Callback<TimelineResponse>() {
            @Override
            public void onResponse(Response<TimelineResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getPosts() != null) {
                        getAboutApiCall(userId,response.body().getPosts());
                    } else {
                        NotesHelpers.toastMessage(context, "Error " + "Response body is empty");
                    }
                } else {
                    NotesHelpers.toastMessage(context, "Response is not success");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                NotesHelpers.toastMessage(context, "Failure " + t.getMessage());
                t.printStackTrace();
            }
        });

    }

    private void getAboutApiCall(final int userId, final List<Post> posts) {
        //GET USER ABOUT

        Call<AboutUserResponse> aboutUserResponseCall=ApiManager.getInstance().getService().getUserAbout(userId);
        aboutUserResponseCall.enqueue(new Callback<AboutUserResponse>() {
            @Override
            public void onResponse(Response<AboutUserResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getUser() != null) {
                        User user = response.body().getUser();
                        populateRecyclerView(user, posts);
                    } else {
                        NotesHelpers.toastMessage(context, "Error " + "Response body is empty");
                    }
                } else {
                    NotesHelpers.toastMessage(context, "Response is not success");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                NotesHelpers.toastMessage(context, "Failure " + t.getMessage());
                t.printStackTrace();
            }
        });

    }

    private void populateRecyclerView(User user,List<Post> posts) {
        UserWallAdapter adapter = new UserWallAdapter(user,posts, this,false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

}
