package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.developinggeek.thebetterlawyernewsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity
{

    private TextInputLayout edt_email , edt_pass ,edt_name;
    private Button btn_reg;
    private ProgressDialog regProgress;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private DatabaseReference mUsersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mToolbar = (Toolbar)findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");

        edt_email = (TextInputLayout)findViewById(R.id.register_email);
        edt_pass = (TextInputLayout)findViewById(R.id.register_password);
        btn_reg = (Button)findViewById(R.id.register_btn_register);
        edt_name = (TextInputLayout)findViewById(R.id.register_name);

        regProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        mUsersDatabase = FirebaseDatabase.getInstance().getReference();

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String name = edt_name.getEditText().getText().toString();
                String email = edt_email.getEditText().getText().toString();
                String password = edt_pass.getEditText().getText().toString();

                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
                {
                    regProgress.setTitle("Registering User");
                    regProgress.setMessage("Please wait while your account is created");
                    regProgress.setCanceledOnTouchOutside(false);
                    regProgress.show();
                    register_user(name ,email ,password);
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Enter All The Text Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void register_user(final String name, String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email ,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
              if(task.isSuccessful())
              {
                  FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                  String uid = currentUser.getUid();
                  String tokenId = FirebaseInstanceId.getInstance().getToken();

                  mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                  HashMap<String,String> userMap = new HashMap<>();
                  userMap.put("name",name);
                  userMap.put("image","default");
                  userMap.put("device_token",tokenId);

                  mUsersDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task)
                      {
                          if (task.isSuccessful())
                          {
                              regProgress.dismiss();
                              Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                              mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                              startActivity(mainIntent);
                              finish();
                          }

                      }
                  });
              }
              else
              {
                  regProgress.hide();
                  Toast.makeText(RegisterActivity.this, "could not create account", Toast.LENGTH_SHORT).show();
              }

            }
        });
    }

}
