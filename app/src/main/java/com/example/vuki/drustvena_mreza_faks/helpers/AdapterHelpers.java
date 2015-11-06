package com.example.vuki.drustvena_mreza_faks.helpers;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Vuki on 6.11.2015..
 */
public class AdapterHelpers {

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
