package com.example.vuki.drustvena_mreza_faks.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.vuki.drustvena_mreza_faks.activities.LoginActivity;

/**
 * Created by Vuki on 4.1.2016..
 */
public class RetrofitHelper {
    static int FORBIDDEN=403;

    public static void checkCode(int statusCode, Context context){
            if(statusCode==FORBIDDEN){
                getOnLoginPage(context);
            }
    }


    private static void getOnLoginPage(Context context){
        Intent intent=new Intent(context, LoginActivity.class);
        NotesHelpers.toastMessage(context,"Session timeout");
        context.startActivity(intent);
        ((Activity)context).finish();
    }

}
