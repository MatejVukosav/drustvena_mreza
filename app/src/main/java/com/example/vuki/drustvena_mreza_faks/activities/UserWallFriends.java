package com.example.vuki.drustvena_mreza_faks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.adapters.SearchUserAdapter;
import com.example.vuki.drustvena_mreza_faks.models.User;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserWallFriends extends AppCompatActivity {

    @Bind(R.id.user_wall_friends_recycler_view)
    RecyclerView recyclerView;

/*    @Bind(R.id.empty_list)
    TextView emptyList;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wall_friends);
        ButterKnife.bind(this);

        getApiCall();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Friends");
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

    private void getApiCall() {
        //GET USER FRIENDS
    }


    private void populateRecyclerView(List<User> users) {
     /*   if (users.size() == 0) {
            emptyList.setVisibility(View.VISIBLE);
        } else {
            emptyList.setVisibility(View.INVISIBLE);
        }*/

        SearchUserAdapter adapter = new SearchUserAdapter(this, users, true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


}
