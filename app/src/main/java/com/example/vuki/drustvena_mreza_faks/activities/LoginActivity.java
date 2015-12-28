package com.example.vuki.drustvena_mreza_faks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.helpers.InternetConnection;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.models.LoginRequest;
import com.example.vuki.drustvena_mreza_faks.models.LoginResponse;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {


    //login text for enter email
    @Bind(R.id.login_username)
    EditText mLoginUsername;
    //login text for enter password
    @Bind(R.id.login_password)
    EditText mLoginPassword;



    private String TAG = getClass().getSimpleName();
    ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        InternetConnection internetConnection = new InternetConnection(this);
        internetConnection.setInternetConnectionCallback(new InternetConnection.OnCheckInternetConnection() {
            @Override
            public void OnInternetCheck(boolean hasInternetConnection) {
                if (hasInternetConnection) {
                    init();
                } else {
                    finish();
                }
            }
        });
        internetConnection.checkInternetConnection();

    }

    private void init() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Bubbles");
        }

    }


    @OnClick(R.id.login_btn)
    public void onLoginClick() {

        String username = mLoginUsername.getText().toString();
        String password = mLoginPassword.getText().toString();
        LoginRequest loginRequest = new LoginRequest(username, password);
        Call<LoginResponse> loginResponseCall = ApiManager.getInstance().getService().postSignIn(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getUser() != null) {
                        ApiManager.getInstance().setUser(response.body().getUser());

                        loginSuccesfull();

                    } else {
                        try {
                            String error = "";
                            for (String e : response.body().getErrors()) {
                                error = error + " " + e;
                            }
                            NotesHelpers.toastMessage(getApplicationContext(), error);
                        } catch (NullPointerException e) {
                            NotesHelpers.toastMessage(getApplicationContext(), getResources().getString(R.string.error_something_went_wrong));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                NotesHelpers.toastMessage(getApplicationContext(), getResources().getString(R.string.error_something_is_wrong));
            }
        });

    }

    private void loginSuccesfull() {
        Intent intent = new Intent(this, CoreActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.login_register_btn)
    public void onRegisterClick(){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }


  /*  @OnClick(R.id.login_forgot_password)
    public void onForgottenpasswordClick() {
        Intent intent = new Intent(LoginActivity.this, ForgottenPasswordActivity.class);
        startActivity(intent);
    }*/


}
