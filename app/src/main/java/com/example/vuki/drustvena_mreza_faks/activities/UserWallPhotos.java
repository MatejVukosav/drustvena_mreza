package com.example.vuki.drustvena_mreza_faks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.adapters.ShowGaleryAdapter;
import com.example.vuki.drustvena_mreza_faks.models.User;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserWallPhotos extends AppCompatActivity {

    @Bind(R.id.user_wall_photos_recycler_view)
    RecyclerView recyclerView;

 /*   @Bind(R.id.empty_list)
    TextView emptyList;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wall_photos);
        ButterKnife.bind(this);

        getApiCall();
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

    private void getApiCall() {
        //GET USER PHOTOS
    }

    private void populateRecyclerView(List<User> users) {
   /*     if (users.size() == 0) {
            emptyList.setVisibility(View.VISIBLE);
        } else {
            emptyList.setVisibility(View.INVISIBLE);
        }*/

        ShowGaleryAdapter adapter = new ShowGaleryAdapter(this, users);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


}
