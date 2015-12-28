package com.example.vuki.drustvena_mreza_faks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.adapters.CommentsAdapter;
import com.example.vuki.drustvena_mreza_faks.models.Comment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShowComments extends AppCompatActivity {

    @Bind(R.id.empty_list)
    TextView emptyList;

    @Bind(R.id.comment_list_recycler_view)
    RecyclerView recyclerView;

    List<Comment> mCommentList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_comments);
        ButterKnife.bind(this);

        init();

    }

    private void init() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Comments");
        }

        getApiCall();
    }

    private void getApiCall() {

        populateRecycler(mCommentList);
    }

    private void populateRecycler(List<Comment> commentList) {
        if (commentList.size() == 0) {
            emptyList.setVisibility(View.VISIBLE);
        } else {
            emptyList.setVisibility(View.GONE);
        }
        recyclerView.setVisibility(View.VISIBLE);
        CommentsAdapter adapter = new CommentsAdapter(this, commentList);
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
