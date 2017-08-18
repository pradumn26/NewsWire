package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.developinggeek.thebetterlawyernewsapp.Adapter.MainPageAdapter;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiClient;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiInterface;
import com.developinggeek.thebetterlawyernewsapp.Rest.AppConstants;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity
{

    private ActionBar actionBar;
    private Toolbar mToolbar;
    private ViewPager mPager;
    private MainPageAdapter mPageAdapter;
    private TabLayout mTabLayout;
    private MaterialSearchView searchView;
    private ApiInterface apiInterface;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ImageView logoImageView;
    private NavigationView navigationView;

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

        mPager = (ViewPager)findViewById(R.id.main_pager);
        mPageAdapter = new MainPageAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPageAdapter);

        mTabLayout = (TabLayout)findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mPager);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        checkConnection();

        searchView = (MaterialSearchView)findViewById(R.id.main_search_view);


        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                Intent searchIntent = new Intent(MainActivity.this , SearchActivity.class);
                searchIntent.putExtra("category",query);
                startActivity(searchIntent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        navigationView = (NavigationView) findViewById(R.id.navigationView_in_mainActivity);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_in_mainActivity);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                switch (itemId) {
                    case R.id.Home:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.Recent:
                        startActivityById("14+2136");
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.Government:
                        startActivityById("2868+807");
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.International:
                        startActivityById("3216");
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.SupremeCourt:
                        startActivityById("34+74");
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.HighCourt:
                        startActivityById("46+7191+241+71+1800+98+188+741+344+4198+288+804+137+2200+1283+2317+3683+8823+5298+4556+1999+3077");
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });
    }

    private void startActivityById(String id) {
        Intent intent= new Intent(MainActivity.this,SingleCategory.class);
        intent.putExtra(AppConstants.SINGLE_CATEGORY_ID_STRING,id);
        startActivity(intent);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }




    public void checkConnection()
    {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            Toast.makeText(MainActivity.this, "Connection Established", Toast.LENGTH_SHORT).show();
        }
        else
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle(Html.fromHtml("<font color='#00AEA3'>No Internet</font>"));
            alert.setMessage(Html.fromHtml("<font color='#00AEA3'>Please check your Internet Connection</font>"));
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alert.show();

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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch(id)
        {
            case R.id.app_bar_setting : startActivity(new Intent(MainActivity.this , SettingsActivity.class));

        }

        return super.onOptionsItemSelected(item);
    }



}