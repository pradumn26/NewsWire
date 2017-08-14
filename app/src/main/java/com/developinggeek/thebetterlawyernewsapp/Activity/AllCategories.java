package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.developinggeek.thebetterlawyernewsapp.Adapter.CategoriesAdapter;
import com.developinggeek.thebetterlawyernewsapp.Model.Category;
import com.developinggeek.thebetterlawyernewsapp.R;

import java.util.ArrayList;
import java.util.List;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class AllCategories extends AppCompatActivity {
    FeatureCoverFlow coverFlow;
    CategoriesAdapter categoryAdapter;
    List<Category> categoryList = new ArrayList<>();
    TextSwitcher mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);
        initData();
        mTitle = (TextSwitcher) findViewById(R.id.title);
        mTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater inflater = LayoutInflater.from(AllCategories.this);
                TextView txt = (TextView) inflater.inflate(R.layout.layout_title, null);
                return txt;
            }
        });
        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);

        mTitle.setInAnimation(in);
        mTitle.setOutAnimation(out);

        categoryAdapter = new CategoriesAdapter(categoryList, this);
        coverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
        coverFlow.setAdapter(categoryAdapter);

        coverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                mTitle.setText(categoryList.get(position).getName());
            }

            @Override
            public void onScrolling() {

            }
        });


    }

    private void initData() {
        categoryList.add(new Category("Environment", "https://upload.wikimedia.org/wikipedia/commons/3/36/Hopetoun_falls.jpg"));
        categoryList.add(new Category("Politics", "https://betanews.com/wp-content/uploads/2014/05/politician-292x300.jpg"));
        categoryList.add(new Category("Movies", "https://www.mbatious.com/uploads/files/1497349879912-movies.jpg"));
        categoryList.add(new Category("World", "https://pbs.twimg.com/profile_images/587949417577066499/3uCD4xxY.jpg"));
        categoryList.add(new Category("Technology", "http://www.robertwmills.com/wp-content/uploads/2016/03/healthcare-technology-8-04-2015.jpg"));
    }

}