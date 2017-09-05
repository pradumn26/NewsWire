package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.developinggeek.thebetterlawyernewsapp.Model.Favorite;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.sufficientlysecure.htmltextview.HtmlTextView;

public class FavoriteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Favorite").child(mAuth.getCurrentUser().getUid());
        recyclerView=(RecyclerView) findViewById(R.id.favorite_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this));
        recyclerView.setHasFixedSize(true);



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Favorite,FavoriteViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Favorite, FavoriteViewHolder>(
                Favorite.class,
                R.layout.favorite_single_layout,
                FavoriteViewHolder.class,
                firebaseDatabase
        ) {
            @Override
            protected void populateViewHolder(FavoriteViewHolder viewHolder, Favorite model, int position) {

                viewHolder.headline.setText(model.getHeadline());

                viewHolder.content.setText(Html.fromHtml(Html.fromHtml(model.getContent()).toString()));
                viewHolder.content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(((TextView)view).getMaxLines() == 6)
                            ((TextView)view).setMaxLines(Integer.MAX_VALUE);
                        else
                            ((TextView)view).setMaxLines(6);
                    }
                });
                String encodedString=model.getBitmap();
                try {
                    byte [] encodeByte=Base64.decode(encodedString, Base64.DEFAULT);
                    Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                    viewHolder.imageView.setImageBitmap(bitmap);
                } catch(Exception e) {
                    e.getMessage();
                }

            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView headline;
        TextView content;

        public FavoriteViewHolder(View itemView) {
            super(itemView);

            imageView=(ImageView) itemView.findViewById(R.id.brief_img);
            headline=(TextView) itemView.findViewById(R.id.brief_title);
            content=(TextView) itemView.findViewById(R.id.brief_content);
        }
    }
}
