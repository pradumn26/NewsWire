package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.TransitionInflater;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.developinggeek.thebetterlawyernewsapp.Adapter.CategoriesRecyclerViewAdapter;
import com.developinggeek.thebetterlawyernewsapp.Adapter.ReadNewsAuthorListViewAdapter;
import com.developinggeek.thebetterlawyernewsapp.Model.AuthorLink;
import com.developinggeek.thebetterlawyernewsapp.Model.Categories;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.developinggeek.thebetterlawyernewsapp.Rest.AppConstants;
import com.squareup.picasso.Picasso;
import com.thefinestartist.finestwebview.FinestWebView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

public class ReadRecentNewsActivity extends AppCompatActivity {

    CardView cardView;
    ImageView photoImageView;
    TextView headlineTextView;
    HtmlTextView storyTextView;
    ListView authorListView;
    ReadNewsAuthorListViewAdapter readNewsAuthorListViewAdapter;
    RecyclerView categoryRecyclerView;
    CategoriesRecyclerViewAdapter categoriesRecyclerViewAdapter;
    ArrayList<Categories> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_news_photo_transition));

        setContentView(R.layout.activity_read_recent_news);

        cardView = (CardView) findViewById(R.id.read_news_activity_image_cardview);
        photoImageView = (ImageView) findViewById(R.id.read_news_activity_image_view);
        headlineTextView = (TextView) findViewById(R.id.read_news_activity_headline_textview);
        storyTextView = (HtmlTextView) findViewById(R.id.read_news_activity_content_textview);
        categories= (ArrayList<Categories>) getIntent().getSerializableExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_CATEGORY_LIST);
        categoryRecyclerView= (RecyclerView) findViewById(R.id.category_recyclerView_in_readRecentNews_activity);

        authorListView = (ListView) findViewById(R.id.read_news_activity_author_listview);
        AuthorLink authorLink = new AuthorLink();
        authorLink.setName(getIntent().getStringExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_AUTHOR_NAME));
        authorLink.setDescription(getIntent().getStringExtra(AppConstants.READ_RECENT_NEWS_ACTVITY_AUTHOR_DESCRIPTION));
        ArrayList<AuthorLink> authorLinkArrayList = new ArrayList<>();
        authorLinkArrayList.add(authorLink);
        readNewsAuthorListViewAdapter = new ReadNewsAuthorListViewAdapter(this, authorLinkArrayList);
        authorListView.setAdapter(readNewsAuthorListViewAdapter);

        Picasso.with(ReadRecentNewsActivity.this).load(getIntent().getStringExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_PHOTO)).into(photoImageView);
        headlineTextView.setText(getIntent().getStringExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_HEADLINE));
        storyTextView.setHtml(getIntent().getStringExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_CONTENT));
        authorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new FinestWebView.Builder(ReadRecentNewsActivity.this).show(getIntent().getStringExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_AUTHOR_URL));
            }
        });
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(ReadRecentNewsActivity.this,2));
        categoriesRecyclerViewAdapter= new CategoriesRecyclerViewAdapter(ReadRecentNewsActivity.this,categories);
        categoryRecyclerView.setAdapter(categoriesRecyclerViewAdapter);
    }
}
