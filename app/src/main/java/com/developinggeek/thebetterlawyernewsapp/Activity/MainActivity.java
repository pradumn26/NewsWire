package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.developinggeek.thebetterlawyernewsapp.Adapter.MainPageAdapter;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiClient;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiInterface;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private Toolbar mToolbar;
    private ViewPager mPager;
    private MainPageAdapter mPageAdapter;
    private TabLayout mTabLayout;
    private MaterialSearchView searchView;
    private ApiInterface apiInterface;
    private ImageView logoImageView;
    private ListView navigationViewListView;
    private ArrayAdapter<String> listViewArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setSharedElementExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_news_photo_transition));

        setContentView(R.layout.drawer_layout_in_main_activity);

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("");

        mPager = (ViewPager) findViewById(R.id.main_pager);
        mPageAdapter = new MainPageAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPageAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mPager);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        checkConnection();

        searchView = (MaterialSearchView) findViewById(R.id.main_search_view);
        logoImageView = (ImageView) findViewById(R.id.logoImageView);

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                logoImageView.setVisibility(View.GONE);
            }

            @Override
            public void onSearchViewClosed() {
                logoImageView.setVisibility(View.VISIBLE);
            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                searchIntent.putExtra("category", query);
                startActivity(searchIntent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        navigationViewListView= (ListView) findViewById(R.id.listView_in_navigationView);
        ArrayList<String> navigationViewItems= new ArrayList<>();
        navigationViewItems.add("Home");
        navigationViewItems.add("Categories");
        navigationViewItems.add("Home");
        navigationViewItems.add("Categories");
        navigationViewItems.add("Home");
        navigationViewItems.add("Categories");
        navigationViewItems.add("Home");
        navigationViewItems.add("Categories");
        navigationViewItems.add("Home");
        navigationViewItems.add("Categories");
        listViewArrayAdapter= new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,navigationViewItems);
        navigationViewListView.setAdapter(listViewArrayAdapter);
    }


    public void checkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            Toast.makeText(MainActivity.this, "Connection Established", Toast.LENGTH_SHORT).show();
        } else {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        searchView.setMenuItem(searchItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.categories:
                startActivity(new Intent(MainActivity.this, AllCategories.class));
        }

        return super.onOptionsItemSelected(item);
    }


}