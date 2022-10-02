package com.example.vuki.drustvena_mreza_faks.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.adapters.CommentsAdapter;
import com.example.vuki.drustvena_mreza_faks.helpers.BundleKeys;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.models.Comment;
import com.example.vuki.drustvena_mreza_faks.models.CommentsResponse;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ShowComments extends AppCompatActivity {

    @Bind(R.id.empty_list)
    TextView emptyList;

    @Bind(R.id.comment_list_recycler_view)
    RecyclerView recyclerView;

    List<Comment> mCommentList = new ArrayList<>();
    static Context context;
    static int contentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_comments);
        ButterKnife.bind(this);

        init();

    }

    private void init() {
        context = this;

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Comments");
        }

        Bundle b = getIntent().getExtras();
        if (b != null) {
            contentId = b.getInt(BundleKeys.COMMENT);
            getApiCall(contentId);
        } else {
            NotesHelpers.toastMessage(context, "Comment error");
            finish();
        }
    }

    /*
    Get all comments and show them
     */
    private void getApiCall(int contentId) {

        //CommentRequest commentRequest=new CommentRequest();
        Call<CommentsResponse> commentsResponseCall = ApiManager.getInstance().getService().getComments(contentId);
        commentsResponseCall.enqueue(new Callback<CommentsResponse>() {
            @Override
            public void onResponse(Response<CommentsResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getComments() != null) {
                        mCommentList = response.body().getComments();
                        populateRecycler(mCommentList);
                    } else {
                        NotesHelpers.toastMessage(context, "Response body is null");
                    }
                } else {
                    NotesHelpers.toastMessage(context, "Response is not success");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                NotesHelpers.toastMessage(context, "Failure");

            }
        });

    }

    private void populateRecycler(List<Comment> commentList) {
        if (commentList.size() == 0) {
            emptyList.setVisibility(View.VISIBLE);
        } else {
            emptyList.setVisibility(View.GONE);
        }
        recyclerView.setVisibility(View.VISIBLE);
        CommentsAdapter adapter = new CommentsAdapter(this, commentList,contentId);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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


}
