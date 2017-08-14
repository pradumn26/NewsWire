package com.developinggeek.thebetterlawyernewsapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.developinggeek.thebetterlawyernewsapp.Fragments.BriefsFragment;
import com.developinggeek.thebetterlawyernewsapp.Fragments.GovernmentNewsFragment;
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

            case 2 : BriefsFragment briefsFragment = new BriefsFragment();
                     return briefsFragment;

            case 1 : GovernmentNewsFragment governmentNewsFragment = new GovernmentNewsFragment();
                     return governmentNewsFragment;

            default : return null;
        }

    }

    @Override
    public int getCount()
    {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch(position)
        {
            case 0 : return "Recent";

            case 1 : return "Government";

            case 2 : return "Briefs";
        }

        return super.getPageTitle(position);
    }

}
