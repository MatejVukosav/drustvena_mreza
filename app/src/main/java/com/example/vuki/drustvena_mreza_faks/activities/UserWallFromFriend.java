package com.example.vuki.drustvena_mreza_faks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.adapters.UserWallAdapter;
import com.example.vuki.drustvena_mreza_faks.helpers.BundleKeys;
import com.example.vuki.drustvena_mreza_faks.mock.MockUsers;
import com.example.vuki.drustvena_mreza_faks.models.Post;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Vuki on 4.11.2015..
 */
public class UserWallFromFriend extends AppCompatActivity{

    @Bind(R.id.user_wall_from_friend_recycler_view)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wall);

        Bundle b=getIntent().getExtras();
        if(b!=null){
            int userId=b.getInt(BundleKeys.FRIEND_USER_ID);
            getApiCall(userId);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Friend wall");
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

    private void getApiCall(int userId){

        //TODO dohvatit sadrzaj od frenda

    }

    private void populateRecyclerView(List<Post> posts) {
        UserWallAdapter adapter = new UserWallAdapter(ApiManager.getInstance().getUser(), MockUsers.getFivePosts(15).getPosts(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

}
