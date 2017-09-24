package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.developinggeek.thebetterlawyernewsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;


public class SettingsActivity extends AppCompatActivity
{

    private RelativeLayout online;
    private LinearLayout offline;
    private Toolbar mToolbar;
    private Button btnLogin;
    private CircleImageView userImg;
    private TextView userName , userProf;
    private FirebaseAuth mAuth;
    private DatabaseReference mUsersDatabse;
    private StorageReference mStorage;
    private LinearLayout logout , favorite , dpChange;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        online = (RelativeLayout)findViewById(R.id.setting_online_layout);
        offline = (LinearLayout)findViewById(R.id.settings_offline_layout);
        mToolbar = (Toolbar)findViewById(R.id.settings_toolbar);
        btnLogin = (Button)findViewById(R.id.settings_btn_login);
        userImg = (CircleImageView)findViewById(R.id.settings_user_img);
        userName = (TextView)findViewById(R.id.setting_user_name);
        userProf = (TextView)findViewById(R.id.setting_user_profession);
        dpChange = (LinearLayout)findViewById(R.id.setting_dp_change);
        logout = (LinearLayout)findViewById(R.id.setting_logout);
        favorite = (LinearLayout)findViewById(R.id.setting_favourite);

        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("User Settings");

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,FavoriteActivity.class));
            }
        });

        if(mAuth.getCurrentUser() == null)
        {
            online.setVisibility(View.GONE);
            logout.setVisibility(View.GONE);
            favorite.setVisibility(View.GONE);
            dpChange.setVisibility(View.GONE);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Intent loginIntent = new Intent(SettingsActivity.this , LoginActivity.class);
                    startActivity(loginIntent);
                }
            });
        }
        else
        {
           offline.setVisibility(View.GONE);

           String uid = mAuth.getCurrentUser().getUid();

           mUsersDatabse = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

           mUsersDatabse.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot)
               {
                   String name = dataSnapshot.child("name").getValue().toString();
                   String prof = dataSnapshot.child("profession").getValue().toString();
                   String imgUrl = dataSnapshot.child("image").getValue().toString();

                   if(!imgUrl.equals("default")) {
                       Picasso.with(SettingsActivity.this).load(imgUrl).into(userImg);
                   }

                   userName.setText(name);
                   userProf.setText(prof);
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });

            dpChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Intent galleryIntent = new Intent();
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent , 1);
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();

                    Intent newIntent = new Intent(SettingsActivity.this , SettingsActivity.class);
                    newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(newIntent);
                }
            });

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            Uri mImageUri = data.getData();

            final File thumbFile = new File(mImageUri.getPath());

            String userId = mAuth.getCurrentUser().getUid();

            StorageReference fileStorage = mStorage.child("profile images").child(userId + ".jpg");

            fileStorage.putFile(mImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
                {
                    if (task.isSuccessful()) {
                        String downloadUrl = task.getResult().getDownloadUrl().toString();

                        DatabaseReference imageDB = mUsersDatabse.child("image");
                        imageDB.setValue(downloadUrl);
                    }
                }
            });
        }

        }
}
