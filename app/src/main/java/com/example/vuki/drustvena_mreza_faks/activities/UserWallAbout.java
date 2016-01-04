package com.example.vuki.drustvena_mreza_faks.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.helpers.BundleKeys;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.models.AboutUserResponse;
import com.example.vuki.drustvena_mreza_faks.models.User;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UserWallAbout extends AppCompatActivity {

    @Bind(R.id.about_username)
    TextView username;
    @Bind(R.id.about_first_name)
    TextView firstName;
    @Bind(R.id.about_middle_name)
    TextView middlename;
    @Bind(R.id.about_last_name)
    TextView lastName;
    @Bind(R.id.about_age)
    TextView age;
    @Bind(R.id.about_country)
    TextView country;
    @Bind(R.id.about_city)
    TextView city;

    @Bind(R.id.about_relationship_status)
    TextView relationshipStatus;
    @Bind(R.id.about_email)
    TextView email;

    int mUserId;
    static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wall_about);
        ButterKnife.bind(this);
        context = this;

        Bundle b = getIntent().getExtras();
        if (b != null) {
            mUserId = b.getInt(BundleKeys.USER_ABOUT);
        } else {
            mUserId = ApiManager.getInstance().getUser().getUserId();
        }

        getApiCall(mUserId);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("About");
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
        //GET USER ABOUT


        Call<AboutUserResponse> aboutUserResponseCall = ApiManager.getInstance().getService().getUserAbout(userId);
        aboutUserResponseCall.enqueue(new Callback<AboutUserResponse>() {
            @Override
            public void onResponse(Response<AboutUserResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getUser() != null) {
                        fillData(response.body().getUser());
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

        fillData(ApiManager.getInstance().getUser());
    }

    private void fillData(User user) {
        if (user.getUsername() != null) {
            username.setText(user.getUsername());
        }
        if (user.getFirstName() != null) {
            firstName.setText(user.getFirstName());
        }
        if (user.getMiddleName() != null) {
            middlename.setText(user.getMiddleName());
        }
        if (user.getLastName() != null) {
            lastName.setText(user.getLastName());
        }

        relationshipStatus.setText("" + user.getRelationshipStatusId());

        if (user.getEmail() != null) {
            email.setText(user.getEmail());
        }

        age.setText("no data");

        if (user.getCountryId() != null) {
            country.setText(user.getCountryId());
        }
        if (user.getCity() != null) {
            city.setText(user.getCity());
        }

    }

}
