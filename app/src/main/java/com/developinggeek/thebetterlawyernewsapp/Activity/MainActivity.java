package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.developinggeek.thebetterlawyernewsapp.Adapter.CollapseNewsAdapter;
import com.developinggeek.thebetterlawyernewsapp.Adapter.MainPageAdapter;
import com.developinggeek.thebetterlawyernewsapp.Adapter.RecentNewsAdapter;
import com.developinggeek.thebetterlawyernewsapp.Model.Posts;
import com.developinggeek.thebetterlawyernewsapp.Model.PostsResponse;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiClient;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiInterface;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private CollapseNewsAdapter mAdapter;
    private ActionBar actionBar;
    private Toolbar mToolbar;
    private ViewPager mPager;
    private MainPageAdapter mPageAdapter;
    private TabLayout mTabLayout;
    private MaterialSearchView searchView;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    private ApiInterface apiInterface;
    private RecyclerView mNewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setSharedElementExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_news_photo_transition));

        setContentView(R.layout.drawer_layout_in_main_activity);

        mToolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("");

        AppBarLayout appBarLayout=(AppBarLayout) findViewById(R.id.main_app_bar);
        appBarLayout.addOnOffsetChangedListener(this);

        collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        dynamicToolbarColor();

        toolbarTextAppearance();

        mPager = (ViewPager)findViewById(R.id.main_pager);
        mPageAdapter = new MainPageAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPageAdapter);

        mTabLayout = (TabLayout)findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mPager);

        mNewsList = (RecyclerView)findViewById(R.id.main_collapse_news_list);
        mNewsList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        fetchPopularNews();

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

    private void fetchPopularNews() {
        Call<PostsResponse> call = apiInterface.getSearchResults("india");

        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response)
            {

                List<Posts> posts = response.body().getPosts();

                mAdapter = new CollapseNewsAdapter(posts , getApplicationContext());

                mNewsList.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {

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
            public void onGenerated(Palette palette)
            {

                Palette.Swatch vibrant =
                        palette.getVibrantSwatch();



                if (vibrant != null) {
                    // If we have a vibrant color
                    // update the title TextView

                    collapsingToolbarLayout.setBackgroundColor(Color.parseColor("#3F51B5"));
                    collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(palette.getMutedColor(1)));
                    collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(palette.getMutedColor(0)));

                }
            }
        });
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        if (verticalOffset == 0)
        {
            LinearLayout linearLayout=(LinearLayout) findViewById(R.id.main_linear_layout);
            linearLayout.setVisibility(View.INVISIBLE);
            mTabLayout.setVisibility(View.INVISIBLE);
            Log.i("view","invisible");

            actionBar.setTitle("");
        }
        else
        {
            LinearLayout linearLayout=(LinearLayout) findViewById(R.id.main_linear_layout);
            linearLayout.setVisibility(View.VISIBLE);
            mTabLayout.setVisibility(View.VISIBLE);
            Log.i("view","visible");

            actionBar.setTitle("NewsWire App");
        }

    }
}
