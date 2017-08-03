package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.developinggeek.thebetterlawyernewsapp.Adapter.MainPageAdapter;
import com.developinggeek.thebetterlawyernewsapp.R;

public class MainActivity extends AppCompatActivity
{

    private Toolbar mToolbar;
    private ViewPager mPager;
    private MainPageAdapter mPageAdapter;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("NewsWire");

        mPager = (ViewPager)findViewById(R.id.main_pager);
        mPageAdapter = new MainPageAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPageAdapter);

        mTabLayout = (TabLayout)findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mPager);

    }

}
