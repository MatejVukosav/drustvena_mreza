package com.example.vuki.drustvena_mreza_faks.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.helpers.InternetConnection;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.helpers.PrefsHelper;
import com.example.vuki.drustvena_mreza_faks.models.LoginRequest;
import com.example.vuki.drustvena_mreza_faks.models.LoginResponse;
import com.example.vuki.drustvena_mreza_faks.models.User;
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

    PrefsHelper prefsHelper;
    boolean mLoginSave = false;


    private String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
        prefsHelper = new PrefsHelper(this);

        String json = prefsHelper.getString(PrefsHelper.LOGGED_IN_USER_APPUSER_DATA, "");
        String password = prefsHelper.getString(PrefsHelper.LOGGED_IN_USER_APPUSER_DATA_PASSWORD, "");
        mLoginSave = prefsHelper.getBoolean(PrefsHelper.REMEMBER_USER_LOGIN_SKIP, false);

        if (!json.isEmpty() && json != null) {
            User user = ApiManager.getGson().fromJson(json, User.class);
            if (user != null) {
                ApiManager.getInstance().setUser(user);

                mLoginUsername.setText(user.getUsername()); //SET LAST LOGIN MAIL
                mLoginPassword.setText(password);

                if (mLoginSave) {
                    if (password != null & !password.isEmpty()) {
                        mLoginPassword.setText(password);
                    }
                }
            }

        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Bubbles");
        }

    }


    @OnClick(R.id.login_btn)
    public void onLoginClick() {

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        String username = mLoginUsername.getText().toString();
        final String password = mLoginPassword.getText().toString();
        LoginRequest loginRequest = new LoginRequest(username, password);
        Call<LoginResponse> loginResponseCall = ApiManager.getInstance().getService().postSignIn(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getUser() != null) {
                        ApiManager.getInstance().setUser(response.body().getUser());

                        loginSuccesfull(ApiManager.getInstance().getUser(), password);

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

                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();


                } else {
                    NotesHelpers.toastMessage(getApplicationContext(), getResources().getString(R.string.error_something_is_wrong));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                NotesHelpers.toastMessage(getApplicationContext(), getResources().getString(R.string.error_something_went_wrong));
                t.printStackTrace();
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();


            }
        });

    }

    private void loginSuccesfull(User user, String password) {


        String json = ApiManager.getGson().toJson(user);
        prefsHelper.putString(PrefsHelper.LOGGED_IN_USER_APPUSER_DATA, json);
        prefsHelper.putString(PrefsHelper.LOGGED_IN_USER_APPUSER_DATA_PASSWORD, password);
        prefsHelper.putBoolean(PrefsHelper.REMEMBER_USER_LOGIN_SKIP, true);

        Intent intent = new Intent(this, CoreActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.login_register_btn)
    public void onRegisterClick() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }


  /*  @OnClick(R.id.login_forgot_password)
    public void onForgottenpasswordClick() {
        Intent intent = new Intent(LoginActivity.this, ForgottenPasswordActivity.class);
        startActivity(intent);
    }*/

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


}
