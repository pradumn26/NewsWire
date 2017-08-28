package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.transition.TransitionInflater;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.developinggeek.thebetterlawyernewsapp.Adapter.CategoriesRecyclerViewAdapter;
import com.developinggeek.thebetterlawyernewsapp.Adapter.ReadNewsAuthorListViewAdapter;
import com.developinggeek.thebetterlawyernewsapp.Model.AuthorLink;
import com.developinggeek.thebetterlawyernewsapp.Model.Categories;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.developinggeek.thebetterlawyernewsapp.Rest.AppConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.thefinestartist.finestwebview.FinestWebView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadRecentNewsActivity extends AppCompatActivity {

    ImageView photoImageView;
    TextView headlineTextView;
    HtmlTextView storyTextView;
    ListView authorListView;
    ReadNewsAuthorListViewAdapter readNewsAuthorListViewAdapter;
    RecyclerView categoryRecyclerView;
    CategoriesRecyclerViewAdapter categoriesRecyclerViewAdapter;
    ArrayList<Categories> categories;
    Button btn_share ;
    ImageButton btn_share_whatsapp , btn_share_insta;
    View mView;

    DatabaseReference mRootRef;
    FirebaseAuth mAuth;
    LikeButton likeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_news_photo_transition));

        setContentView(R.layout.activity_read_recent_news);

        photoImageView = (ImageView) findViewById(R.id.read_news_activity_image_view);
        headlineTextView = (TextView) findViewById(R.id.read_news_activity_headline_textview);
        storyTextView = (HtmlTextView) findViewById(R.id.read_news_activity_content_textview);
        categories= (ArrayList<Categories>) getIntent().getSerializableExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_CATEGORY_LIST);
        categoryRecyclerView= (RecyclerView) findViewById(R.id.category_recyclerView_in_readRecentNews_activity);
        btn_share = (Button)findViewById(R.id.read_btn_share);
        btn_share_whatsapp = (ImageButton)findViewById(R.id.read_btn_share_whatsapp);
        mView = findViewById(R.id.read_news_parent);
        btn_share_insta = (ImageButton)findViewById(R.id.read_btn_share_insta);

        authorListView = (ListView) findViewById(R.id.read_news_activity_author_listview);
        AuthorLink authorLink = new AuthorLink();
        authorLink.setName(getIntent().getStringExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_AUTHOR_NAME));
        authorLink.setDescription(getIntent().getStringExtra(AppConstants.READ_RECENT_NEWS_ACTVITY_AUTHOR_DESCRIPTION));
        ArrayList<AuthorLink> authorLinkArrayList = new ArrayList<>();
        authorLinkArrayList.add(authorLink);
        readNewsAuthorListViewAdapter = new ReadNewsAuthorListViewAdapter(this, authorLinkArrayList);
        authorListView.setAdapter(readNewsAuthorListViewAdapter);

        mAuth=FirebaseAuth.getInstance();
        mRootRef= FirebaseDatabase.getInstance().getReference().child("Favorite");
        Log.i("appid1",getIntent().getStringExtra(AppConstants.APP_ID));
        if(mAuth.getCurrentUser()!=null)
        {
            String uid=mAuth.getCurrentUser().getUid();
            mRootRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(getIntent().getStringExtra(AppConstants.APP_ID)))
                    {
                        likeButton.setLiked(true);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        likeButton=(LikeButton) findViewById(R.id.star_button);
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

                if(mAuth.getCurrentUser()==null)
                {
                    Toast.makeText(ReadRecentNewsActivity.this, "You Need To Sign In", Toast.LENGTH_SHORT).show();
                    likeButton.setLiked(false);
                }
                else
                {
                    final String uid=mAuth.getCurrentUser().getUid();

                    final HashMap<String,String> hashMap=new HashMap<String, String>();
                    hashMap.put("Headline",getIntent().getStringExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_HEADLINE));
                    hashMap.put("Content",getIntent().getStringExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_CONTENT));
                    hashMap.put("Liked","true");
                    Picasso.with(ReadRecentNewsActivity.this).load(getIntent().getStringExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_PHOTO)).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            ByteArrayOutputStream baos=new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
                            byte [] b=baos.toByteArray();
                            String temp= Base64.encodeToString(b, Base64.DEFAULT);
                            hashMap.put("Bitmap",temp);
                            mRootRef.child(uid).child(getIntent().getStringExtra(AppConstants.APP_ID)).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    mRootRef.keepSynced(true);

                                }
                            });

                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });

                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                String uid=mAuth.getCurrentUser().getUid();
                mRootRef.child(uid).child(getIntent().getStringExtra(AppConstants.APP_ID)).removeValue();

            }
        });

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

        final String title = getIntent().getStringExtra(AppConstants.READ_RECENT_NEWS_ACTIVITY_HEADLINE).toString();

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


            }
        });

        btn_share_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Uri bmpUri = getLocalBitmapUri(photoImageView);

                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("image/*");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, title);
                whatsappIntent.putExtra(Intent.EXTRA_STREAM , bmpUri);
                startActivity(whatsappIntent);

                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp"));
                    startActivity(browserIntent);
                }
            }
        });

        btn_share_insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri bmpUri = getLocalBitmapUri(photoImageView);

                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("image/*");
                whatsappIntent.setPackage("com.instagram.android");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, title);
                whatsappIntent.putExtra(Intent.EXTRA_STREAM , bmpUri);

                try {
                   startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Instagram&hl=en"));
                    startActivity(browserIntent);
                }
            }
        });

    }

    private Uri getLocalBitmapUri(ImageView photoImageView)
    {
        Drawable drawable = photoImageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) photoImageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {

            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();

            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


}
