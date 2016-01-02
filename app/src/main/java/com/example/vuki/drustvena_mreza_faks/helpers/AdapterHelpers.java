package com.example.vuki.drustvena_mreza_faks.helpers;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Vuki on 6.11.2015..
 */
public class AdapterHelpers {

    public static String profilePic="https://www.bhdani.ba/portal/arhiva-67-281/235/miso1.jpg";
    public static String userName="Mišo Kovač";

    public static void setImage(Context context,String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .into(imageView);
    }

    public static void setImage(Context context,int url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .into(imageView);
    }


}
