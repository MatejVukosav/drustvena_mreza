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
public class CoreFragmentAdapter extends FragmentPagerAdapter {
    int numOfTabs;
    CharSequence Titles[];


    public CoreFragmentAdapter(FragmentManager fm, CharSequence Titles[], int numOfTabs) {
        super(fm);
        this.numOfTabs=numOfTabs;
        this.Titles=Titles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return InboxFragment.newInstance();
            case 2:
                return SearchUsersFragment.newInstance();
            case 3:
                return UserWallFragment.newInstance();
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
