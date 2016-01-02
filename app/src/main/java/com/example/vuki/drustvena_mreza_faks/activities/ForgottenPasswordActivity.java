package com.example.vuki.drustvena_mreza_faks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Vuki on 4.11.2015..
 */
public class ForgottenPasswordActivity extends AppCompatActivity{

    private  String TAG=getClass().getSimpleName();

    @Bind(R.id.forgottenPassEmail)
    EditText enterTheMail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);
        ButterKnife.bind(this);


    }



    @OnClick(R.id.loginForgottenPassBtn)
    public void OnPasswordRepaired(){

        //TODO send repair mail with password

        if(enterTheMail.getText().toString().isEmpty()){
            NotesHelpers.logMessage(TAG, "login error");
            Snackbar.make(findViewById(R.id.forgottenPassRoot), "You must enter email to repair your password.", Snackbar.LENGTH_SHORT).show();
        }else{
            Intent coreIntent=new Intent(ForgottenPasswordActivity.this,CoreActivity.class);
            startActivity(coreIntent);
            finish();
        }
    }


}
