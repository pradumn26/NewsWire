package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developinggeek.thebetterlawyernewsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity
{

    private LinearLayout online , offline;
    private Toolbar mToolbar;
    private Button btnLogin , btnLogout;
    private CircleImageView userImg;
    private TextView userName;
    private FirebaseAuth mAuth;
    private DatabaseReference mUsersDatabse;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        online = (LinearLayout)findViewById(R.id.setting_online_layout);
        offline = (LinearLayout)findViewById(R.id.settings_offline_layout);
        mToolbar = (Toolbar)findViewById(R.id.settings_toolbar);
        btnLogin = (Button)findViewById(R.id.settings_btn_login);
        userImg = (CircleImageView)findViewById(R.id.settings_user_img);
        userName = (TextView)findViewById(R.id.setting_user_name);
        btnLogout = (Button)findViewById(R.id.setting_btn_signout);

        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Login");

        if(mAuth.getCurrentUser() == null)
        {
            online.setVisibility(View.GONE);
            btnLogout.setVisibility(View.GONE);

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

                   userName.setText(name);
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });

            btnLogout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    FirebaseAuth.getInstance().signOut();

                    startActivity(new Intent(SettingsActivity.this , SettingsActivity.class));
                }
            });

        }

    }

}
