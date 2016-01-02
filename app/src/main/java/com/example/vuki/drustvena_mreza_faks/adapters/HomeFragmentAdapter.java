package com.example.vuki.drustvena_mreza_faks.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.vuki.drustvena_mreza_faks.fragments.HomeFragment;
import com.example.vuki.drustvena_mreza_faks.fragments.InboxFragment;
import com.example.vuki.drustvena_mreza_faks.fragments.SearchUsersFragment;
import com.example.vuki.drustvena_mreza_faks.fragments.UserWallFragment;


/**
 * Created by vuki on 14.10.15..
 */
public class HomeFragmentAdapter extends FragmentPagerAdapter {
    int numOfTabs;
    CharSequence Titles[];


    public HomeFragmentAdapter(FragmentManager fm, CharSequence Titles[], int numOfTabs) {
        super(fm);
        this.numOfTabs=numOfTabs;
        this.Titles=Titles;
    }

    @Override
    public Fragment getItem(int position) {
        //NotesHelpers.logMessage("vv", "Pozicija fragmenta: " + position);
        switch (position) {
            case 0:
                return HomeFragment.newInstance(0);
            case 1:
                return InboxFragment.newInstance(1);
            case 2:
                return SearchUsersFragment.newInstance(2);
            case 3:
                return UserWallFragment.newInstance(3);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }





}
