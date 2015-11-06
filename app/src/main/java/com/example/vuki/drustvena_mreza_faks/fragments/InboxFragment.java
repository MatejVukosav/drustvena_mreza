package com.example.vuki.drustvena_mreza_faks.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vuki.drustvena_mreza_faks.R;

/**
 * Created by Vuki on 4.11.2015..
 */
public class InboxFragment extends Fragment {

    public static InboxFragment newInstance(){
        return new InboxFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_core_inbox,container,false);
        return v;
    }

}
