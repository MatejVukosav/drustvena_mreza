package com.example.vuki.drustvena_mreza_faks.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.adapters.ShowGaleryAdapter;
import com.example.vuki.drustvena_mreza_faks.helpers.BundleKeys;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.models.GalleryResponse;
import com.example.vuki.drustvena_mreza_faks.models.Post;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UserWallPhotos extends AppCompatActivity {

    @Bind(R.id.empty_list)
    TextView emptyList;

    @Bind(R.id.user_wall_photos_recycler_view)
    RecyclerView recyclerView;

 /*   @Bind(R.id.empty_list)
    TextView emptyList;*/

    int mUserId;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wall_photos);
        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            mUserId = b.getInt(BundleKeys.FRIEND_USER_ID);
        } else {
            mUserId = ApiManager.getInstance().getUser().getUserId();
        }
        context = this;

        getApiCall(mUserId);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Photos");
        }
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


    //GET USER PHOTOS
    private void getApiCall(int mUserId) {
        Call<GalleryResponse> galleryResponseCall = ApiManager.getInstance().getService().getGalleries(mUserId);
        galleryResponseCall.enqueue(new Callback<GalleryResponse>() {
            @Override
            public void onResponse(Response<GalleryResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getPosts() != null) {
                        populateRecyclerView(response.body().getPosts());
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

    private void populateRecyclerView(List<Post> postsWithPicture) {
       if (postsWithPicture.size() == 0) {
            emptyList.setVisibility(View.VISIBLE);
        } else {
            emptyList.setVisibility(View.INVISIBLE);
        }

        ShowGaleryAdapter adapter = new ShowGaleryAdapter(this, postsWithPicture);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


}
