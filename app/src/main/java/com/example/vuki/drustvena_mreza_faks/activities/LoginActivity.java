package com.example.vuki.drustvena_mreza_faks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.SocialNetworkApplication;
import com.example.vuki.drustvena_mreza_faks.helpers.InternetConnection;
import com.example.vuki.drustvena_mreza_faks.helpers.MvpFactory;
import com.example.vuki.drustvena_mreza_faks.mvp.presenters.LoginPresenter;
import com.example.vuki.drustvena_mreza_faks.mvp.views.LoginView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {


    //login text for enter email
    @Bind(R.id.login_email)
    EditText mLoginEmail;
    //login text for enter password
    @Bind(R.id.login_password)
    EditText mLoginPassword;

    //register button for user registration
    @Bind(R.id.login_register_btn)
    Button mRegisterBtn;

    @Bind(R.id.email_text_input)
    TextInputLayout emailTxtInput;
    @Bind(R.id.password_text_input)
    TextInputLayout passwordTxtInput;


    private String TAG = getClass().getSimpleName();


    private LoginPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        mLoginPassword.setTransformationMethod(new PasswordTransformationMethod());
        presenter = MvpFactory.getPresenter(this);
    }


    @OnClick(R.id.login_btn)
    public void onLoginClick() {
        if (SocialNetworkApplication.DEBUG) {
            Intent intent = new Intent(this, CoreActivity.class);
            startActivity(intent);
        } else {
            presenter.login(mLoginEmail.getText().toString(), mLoginPassword.getText().toString());
        }
    }

    @OnClick(R.id.login_register_btn)
    public void onRegisterClick(){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.login_forgot_password)
    public void onForgottenpasswordClick(){
        Intent intent = new Intent(LoginActivity.this, ForgottenPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    protected void showMessage(String message) {
        super.showMessage(getString(R.string.login_failed));
    }


    @Override
    public void onLoginSucess() {
        Intent intent = new Intent(this, CoreActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailed() {
        showMessage(getString(R.string.login_failed));
    }

    @Override
    public void showProgress() {
        showLoader();
    }

    @Override
    public void hideProgress() {
        hideLoader();
    }

    @Override
    public void showError(@StringRes int error) {
        showMessage(getString(error));
    }
}
