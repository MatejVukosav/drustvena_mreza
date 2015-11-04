package com.example.vuki.drustvena_mreza_faks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.SocialNetworkApplication;
import com.example.vuki.drustvena_mreza_faks.helpers.InternetConnection;
import com.example.vuki.drustvena_mreza_faks.helpers.MvpFactory;
import com.example.vuki.drustvena_mreza_faks.mvp.presenters.RegistrationPresenter;
import com.example.vuki.drustvena_mreza_faks.mvp.views.RegisterView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends BaseActivity implements RegisterView {
    @Bind(R.id.register_email)
    EditText mRegisterEmail;
    @Bind(R.id.register_password)
    EditText mRegisterPassword;

    @Bind(R.id.password_register_text_input)
    TextInputLayout passwordTextInput;
    @Bind(R.id.email_register_text_input)
    TextInputLayout emailTextInput;

    private boolean inputValidation;
    private boolean emailValidation;
    private boolean passwordValidation;

    private RegistrationPresenter presenter;

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

    private void init() {
        mRegisterPassword.setTransformationMethod(new PasswordTransformationMethod());
        presenter = MvpFactory.getPresenter(this);

    }


    @OnClick(R.id.register_btn)
    public void onRegisterClick() {
        if (SocialNetworkApplication.DEBUG) {
            Intent intent = new Intent(this, CoreActivity.class);
            startActivity(intent);
        } else {
            presenter.register(mRegisterEmail.getText().toString(), mRegisterPassword.getText().toString());
        }
    }


    @Override
    public void onRegisterSucess() {
        Intent intent = new Intent(this, CoreActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRegisterFailed() {
        showMessage(getString(R.string.registration_failed));
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
        showError(error);
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

