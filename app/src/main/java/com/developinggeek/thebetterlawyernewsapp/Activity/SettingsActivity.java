package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.developinggeek.thebetterlawyernewsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

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

                   userName.setText(name);
                   userProf.setText(prof);
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

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

}
