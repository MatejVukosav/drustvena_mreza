package com.example.vuki.drustvena_mreza_faks.helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Vuki on 3.11.2015..
 */
public class NotesHelpers {

    public  static void logMessage(String TAG,String body){
        Log.d(TAG,body);
    }

    public  static void toastMessage(Context context,String body){
       Toast.makeText(context,body,Toast.LENGTH_SHORT).show();
    }

}
