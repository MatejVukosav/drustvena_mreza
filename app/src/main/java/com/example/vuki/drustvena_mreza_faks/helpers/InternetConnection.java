package com.example.vuki.drustvena_mreza_faks.helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import com.example.vuki.drustvena_mreza_faks.R;

/**
 * Created by Vuki on 4.11.2015..
 */
public class InternetConnection {
    static Context context;
    private static InternetConnection instance = new InternetConnection();

    public static InternetConnection getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return instance;

    }

    public InternetConnection() {}


    public InternetConnection(Context context) {
        this.context = context;
    }

    public OnCheckInternetConnection internetConnectionCallback;

    public void setInternetConnectionCallback(OnCheckInternetConnection internetConnectionCallback) {
        this.internetConnectionCallback = internetConnectionCallback;
    }

    public interface OnCheckInternetConnection {
        void OnInternetCheck(boolean hasInternetConnection);
    }

    ConnectivityManager connectivityManager;
    Resources r;

    public void checkInternetConnection() {
        boolean isConnected;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();

        //if not connected
        if (!isConnected) {
            r = context.getResources();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(r.getString(R.string.check_internet_connection))
                    .setMessage(r.getString(R.string.unable_to_connect))
                    .setPositiveButton(r.getString(R.string.internet_positive_button), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            checkInternetConnection();
                        }
                    })
                    .setNegativeButton(r.getString(R.string.internet_negative_button), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            internetConnectionCallback.OnInternetCheck(false);
                        }
                    })
                    .create();
            builder.show();
        } else {
            if (internetConnectionCallback != null) {
                internetConnectionCallback.OnInternetCheck(true);
            }
        }

    }


}


