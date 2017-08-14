package com.developinggeek.thebetterlawyernewsapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.developinggeek.thebetterlawyernewsapp.Fragments.BriefsFragment;
import com.developinggeek.thebetterlawyernewsapp.Fragments.GovernmentNewsFragment;
import com.developinggeek.thebetterlawyernewsapp.Fragments.HighCourtFragment;
import com.developinggeek.thebetterlawyernewsapp.Fragments.InternationalFragment;
import com.developinggeek.thebetterlawyernewsapp.Fragments.RecentFragment;
import com.developinggeek.thebetterlawyernewsapp.Fragments.SupremeCourt;


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

            case 1 : GovernmentNewsFragment governmentNewsFragment = new GovernmentNewsFragment();
                     return governmentNewsFragment;

            case 2 : InternationalFragment internationalFragment = new InternationalFragment();
                     return  internationalFragment;

            case 3 : SupremeCourt supremeCourt = new SupremeCourt();
                     return supremeCourt;

            case 4 : HighCourtFragment highCourtFragment = new HighCourtFragment();
                     return highCourtFragment;

            case 5 : BriefsFragment briefsFragment = new BriefsFragment();
                     return briefsFragment;

            default : return null;
        }

    }

    @Override
    public int getCount()
    {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch(position)
        {
            case 0 : return "Recent";

            case 1 : return "Government";

            case 2 : return "International";

            case 3 :return "Supreme Court";

            case 4 : return "High Court";

            case 5 : return "Briefs";
        }

        return super.getPageTitle(position);
    }

}
