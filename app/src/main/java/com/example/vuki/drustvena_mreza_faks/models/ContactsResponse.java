package com.example.vuki.drustvena_mreza_faks.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vuki on 2.1.2016..
 */
public class ContactsResponse implements Serializable {

    @SerializedName("contacts")
    List<ContactRawInfo> contactRawInfoList;

    public List<ContactRawInfo> getContactRawInfoList() {
        return contactRawInfoList;
    }
}
