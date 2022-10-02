package com.example.vuki.drustvena_mreza_faks.helpers;

import android.content.Context;
import android.text.format.DateUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;
import com.example.vuki.drustvena_mreza_faks.network.deserializers.DateDeserializers;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Vuki on 6.11.2015..
 */
public class AdapterHelpers {
    private static int PLACEHOLDER=R.drawable.com_facebook_profile_picture_blank_portrait;
    private static int ERROR=R.drawable.ic_error_black_24dp;
    private static int LOADING=R.drawable.loading;

    public static void setImageWithGlide(Context context, String url, ImageView imageView) {
        String urlModified = ApiManager.BASE_URL + url + "?size=medium";
        getUniversalImageLoader(urlModified, imageView);
    }

    public static void setCircleImage(Context context, String url, CircleImageView circleImageView) {
        String urlModified = ApiManager.BASE_URL + url;
        getUniversalImageLoader(urlModified, circleImageView);
    }

    private static void getUniversalImageLoader(String url, ImageView imageView) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(LOADING)
                .showImageForEmptyUri(PLACEHOLDER)
                .showImageOnFail(ERROR)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(url, imageView, options);
    }

    private static void getGlideLoader(String url, ImageView imageView, Context context) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.com_facebook_profile_picture_blank_portrait)
                .into(imageView);
    }


    public static String setTime(String time) {
        long now = System.currentTimeMillis();
        long timeLong = getTimeInMilliseconds(time);
        //long millisStart = Calendar.getInstance().getTimeInMillis();
        final CharSequence relativeTimeSpan = DateUtils.getRelativeTimeSpanString(timeLong, now, DateUtils.SECOND_IN_MILLIS);
        return relativeTimeSpan.toString();
    }

    public static long getTimeInMilliseconds(String time) {
        Date date = new Date();

        try {
            date = DateDeserializers.formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);


        return calendar.getTimeInMillis();

    }

/*
    *//**
     * One second (in milliseconds)
     *//*
    private static final int _A_SECOND = 1000;
    *//**
     * One minute (in milliseconds)
     *//*
    private static final int _A_MINUTE = 60 * _A_SECOND;
    *//**
     * One hour (in milliseconds)
     *//*
    private static final int _AN_HOUR = 60 * _A_MINUTE;
    *//**
     * One day (in milliseconds)
     *//*
    private static final int _A_DAY = 24 * _AN_HOUR;

    public static String getTimeAgo(long time, Context context) {
        if (time < 1000000000000L)
            // if timestamp given in seconds, convert to millis
            time *= 1000;

        final long now = getCurrentTime(context);
        if (time > now || time <= 0) return "";


        final Resources res = context.getResources();
        final long time_difference = now - time;
        if (time_difference < _A_MINUTE)
            return res.getString(R.string.just_now);
        else if (time_difference < 50 * _A_MINUTE)
            return res.getString(R.string.time_ago,
                    res.getQuantityString(R.plurals.minutes, (int) time_difference / _A_MINUTE, time_difference / _A_MINUTE));
        else if (time_difference < 24 * _AN_HOUR)
            return res.getString(R.string.time_ago,
                    res.getQuantityString(R.plurals.hours, (int) time_difference / _AN_HOUR, time_difference / _AN_HOUR));
        else if (time_difference < 48 * _AN_HOUR)
            return res.getString(R.string.yesterday);
        else
            return res.getString(R.string.time_ago,
                    res.getQuantityString(R.plurals.days, (int) time_difference / _A_DAY, time_difference / _A_DAY));
    }*/


}
