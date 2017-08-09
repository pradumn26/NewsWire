package com.developinggeek.thebetterlawyernewsapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.developinggeek.thebetterlawyernewsapp.Fragments.BlankFragment;
import com.developinggeek.thebetterlawyernewsapp.Fragments.RecentFragment;


public class MainPageAdapter extends FragmentPagerAdapter
{
    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch(position)
        {
            case 0 : RecentFragment recentFragment = new RecentFragment();
                     return recentFragment;

            case 1 : BlankFragment blankFragment = new BlankFragment();
                     return blankFragment;

            default : return null;
        }

    }

    @Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch(position)
        {
            case 0 : return "Recent";

            case 1 : return "blank";
        }

        return super.getPageTitle(position);
    }

}
