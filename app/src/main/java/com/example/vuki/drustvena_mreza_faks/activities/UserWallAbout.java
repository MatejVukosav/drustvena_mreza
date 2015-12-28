package com.example.vuki.drustvena_mreza_faks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.models.User;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;

import butterknife.Bind;
import butterknife.ButterKnife;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wall_about);
        ButterKnife.bind(this);
        getApiCall();

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


    private void getApiCall() {
        //GET USER ABOUT

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

        age.setText("no data");

        if (user.getCountry() != null) {
            country.setText(user.getCountry());
        }
        if (user.getCity() != null) {
            city.setText(user.getCity());
        }

    }

}
