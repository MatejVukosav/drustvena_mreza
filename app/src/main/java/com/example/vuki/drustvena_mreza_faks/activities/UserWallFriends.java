package com.example.vuki.drustvena_mreza_faks.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.adapters.FriendsAdapter;
import com.example.vuki.drustvena_mreza_faks.helpers.BundleKeys;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.models.ContactRawInfo;
import com.example.vuki.drustvena_mreza_faks.models.ContactsResponse;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UserWallFriends extends AppCompatActivity {

    @Bind(R.id.empty_list)
    TextView emptyList;

    @Bind(R.id.user_wall_friends_recycler_view)
    RecyclerView recyclerView;

    static Context context;
    int mUserId;

/*    @Bind(R.id.empty_list)
    TextView emptyList;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wall_friends);
        ButterKnife.bind(this);
        context = this;

        Bundle b=getIntent().getExtras();
        if(b!=null){
            mUserId=b.getInt(BundleKeys.FRIEND_USER_ID);
        }else{
            mUserId=ApiManager.getInstance().getUser().getUserId();
        }

        getApiCall(mUserId);

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

    private void getApiCall(int userId) {
        //GET USER FRIENDS
        Call<ContactsResponse> contactsResponseCall = ApiManager.getInstance().getService().getFriends(userId);
        contactsResponseCall.enqueue(new Callback<ContactsResponse>() {
            @Override
            public void onResponse(Response<ContactsResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getContactRawInfoList() != null) {
                        populateRecyclerView(response.body().getContactRawInfoList());

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


    private void populateRecyclerView(List<ContactRawInfo> contactRawInfoList) {
       if (contactRawInfoList.size() == 0) {
            emptyList.setVisibility(View.VISIBLE);
        } else {
            emptyList.setVisibility(View.INVISIBLE);
        }

        FriendsAdapter adapter = new FriendsAdapter(this, contactRawInfoList, true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


}
