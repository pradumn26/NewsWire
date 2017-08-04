package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.developinggeek.thebetterlawyernewsapp.Adapter.MainPageAdapter;
import com.developinggeek.thebetterlawyernewsapp.Model.Posts;
import com.developinggeek.thebetterlawyernewsapp.Model.PostsResponse;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiClient;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiInterface;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{

    private ActionBar actionBar;
    private Toolbar mToolbar;
    private ViewPager mPager;
    private MainPageAdapter mPageAdapter;
    private TabLayout mTabLayout;
    private MaterialSearchView searchView;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mToolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();

        collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        dynamicToolbarColor();

        toolbarTextAppearance();

        mPager = (ViewPager)findViewById(R.id.main_pager);
        mPageAdapter = new MainPageAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPageAdapter);

        mTabLayout = (TabLayout)findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mPager);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();

                switch (index)
                {
                    case 0 : actionBar.setTitle("Recent");
                        break;

                    case 1 : actionBar.setTitle("Blank");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        searchView = (MaterialSearchView)findViewById(R.id.main_search_view);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                Toast.makeText(MainActivity.this, "search results", Toast.LENGTH_LONG).show();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void toolbarTextAppearance() {

        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);

    }

    private void dynamicToolbarColor() {
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.default_thumb);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(0));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(1));

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        int index = mPager.getCurrentItem();

        switch (index)
        {
            case 0 : actionBar.setTitle("Recent");
                break;

            case 1 : actionBar.setTitle("Blank");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu , menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        searchView.setMenuItem(searchItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
