package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vuki on 24.12.2015..
 */
public class BubblesResponse implements Serializable {

    @SerializedName("bubbles")
    Bubble bubble;
}
