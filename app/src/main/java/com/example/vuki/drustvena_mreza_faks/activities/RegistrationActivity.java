package com.example.vuki.drustvena_mreza_faks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.helpers.InternetConnection;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.models.RegisterRequest;
import com.example.vuki.drustvena_mreza_faks.models.RegisterResponse;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RegistrationActivity extends BaseActivity {

    @Bind(R.id.register_username)
    EditText mUsername;
    @Bind(R.id.register_email)
    EditText mEmail;
    @Bind(R.id.register_password)
    EditText mRegisterPassword;
    @Bind(R.id.register_address)
    EditText mAdress;
    @Bind(R.id.register_first_name)
    EditText mFirstName;
    @Bind(R.id.register_middle_name)
    EditText mMiddleName;
    @Bind(R.id.register_last_name)
    EditText mLastName;
    @Bind(R.id.register_city)
    EditText mCity;
    @Bind(R.id.register_country)
    EditText mCountry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
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

    private void init() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Registration");
        }
    }


    @OnClick(R.id.register_finish_btn)
    public void onRegisterClick() {

        String username = mUsername.getText().toString();
        String email = mEmail.getText().toString();
        String password = mRegisterPassword.getText().toString();
        String firstName = mFirstName.getText().toString();
        String middleName = mMiddleName.getText().toString();
        String lastName = mLastName.getText().toString();
        String address = mAdress.getText().toString();
        String city = mCity.getText().toString();
        String country = mCountry.getText().toString();


        RegisterRequest registerRequest = new RegisterRequest(username, password, email, firstName, middleName, lastName, address, city, country);

        Call<RegisterResponse> registerResponseCall = ApiManager.getInstance().getService().getSignUp(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Response<RegisterResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getUser() != null) {
                        ApiManager.getInstance().setUser(response.body().getUser());
                        registerSuccesfull();
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

    private void registerSuccesfull() {
        NotesHelpers.toastMessage(getApplicationContext(), "You have successfully registrate.Please check your email and follow further steps.");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


/*
    private void init() {

        try {
            emailTextInput.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (!ValidationHelpers.checkEmail(s.toString())) {
                        emailTextInput.setError("Email mora sadržavati znak @");
                        emailValidation = false;
                    } else {
                        emailTextInput.setError(null);
                        emailValidation = true;
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {
                    inputValidation = emailValidation && passwordValidation;
                }
            });
            passwordTextInput.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!ValidationHelpers.checkPassword(s.toString())) {
                        passwordTextInput.setError("Password min. " + ValidationHelpers.MIN_PASSWORD_LENGTH + " znamenki!");
                        passwordValidation = false;
                    } else {
                        passwordTextInput.setError(null);
                        passwordValidation = true;
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {
                    inputValidation = emailValidation && passwordValidation;
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }*/

}

