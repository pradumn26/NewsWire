package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.developinggeek.thebetterlawyernewsapp.Model.PostRequest;
import com.developinggeek.thebetterlawyernewsapp.Model.Post_Response;
import com.developinggeek.thebetterlawyernewsapp.R;
import com.developinggeek.thebetterlawyernewsapp.Rest.ApiInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterActivity extends AppCompatActivity
{

    private TextInputLayout edt_email , edt_pass ,edt_name ,edt_phone , edt_city , edt_confirm;
    private Button btn_reg;
    private ProgressDialog regProgress;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private DatabaseReference mUsersDatabase;
    private Spinner mSpinner;
    private ArrayAdapter<CharSequence> profession_list;
    private String prof;
    private boolean profSelect = false;

    ApiInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mToolbar = (Toolbar)findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create New Account");

        edt_email = (TextInputLayout)findViewById(R.id.register_email);
        edt_pass = (TextInputLayout)findViewById(R.id.register_password);
        btn_reg = (Button)findViewById(R.id.register_btn_register);
        edt_name = (TextInputLayout)findViewById(R.id.register_name);
        edt_phone = (TextInputLayout)findViewById(R.id.register_number);
        edt_city = (TextInputLayout)findViewById(R.id.register_city);
        edt_confirm = (TextInputLayout)findViewById(R.id.register_confirm_pass);
        mSpinner = (Spinner)findViewById(R.id.register_spinner);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apis.thebetterlawyer.com/TheBetterLawyer/rest/userProfile/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service=retrofit.create(ApiInterface.class);

        profession_list = ArrayAdapter.createFromResource(this , R.array.profession_names , R.layout.spinner_item);
        profession_list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(profession_list);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                profSelect = true;
                prof = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(RegisterActivity.this, prof + "is selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}

        });

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
                String confirmPass = edt_confirm.getEditText().getText().toString();
                String city = edt_city.getEditText().getText().toString();
                String phone = edt_phone.getEditText().getText().toString();

                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)
                        &&!TextUtils.isEmpty(confirmPass) && !TextUtils.isEmpty(city) &&!TextUtils.isEmpty(phone)
                        &&profSelect )
                {
                    if(password.equals(confirmPass))
                    {
                        regProgress.setTitle("Registering User");
                        regProgress.setMessage("Please wait while your account is created");
                        regProgress.setCanceledOnTouchOutside(false);
                        regProgress.show();
                        register_user(name ,email ,password ,city ,phone ,prof);
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Confirm Password doesnot matches with the above password", Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Enter All The Text Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void register_user(final String name, final String email, final String password , final String city , final String phone , final String prof)
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
                  userMap.put("phone_number",phone);
                  userMap.put("city" , city);
                  userMap.put("profession" , prof);
                  userMap.put("email" , email);
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

                  PostRequest postRequest=new PostRequest();


                  postRequest.setCity(city);
                  postRequest.setEmailId(email);
                  postRequest.setMobileNumber(phone);
                  postRequest.setUseName(name);
                  postRequest.setPassword(password);
                  postRequest.setVerified("true");
                  postRequest.setType("save");
                  postRequest.setProfileType("003");
                  postRequest.setPublished("1");
                  postRequest.setLoginType("self");
                  Log.i("register","1");

                  Call<Post_Response> post_responseCall=service.getPostResponse(postRequest);
                  post_responseCall.enqueue(new Callback<Post_Response>() {
                      @Override
                      public void onResponse(Call<Post_Response> call, Response<Post_Response> response) {
                          int statusCode=response.code();

                          Post_Response post_response=response.body();
                          Log.i("register",post_response.getMobileNumber());
                          Log.i("register",post_response.getId());
                          Log.i("register",post_response.getError());
                          Log.i("register",post_response.getEmailId());

                          Log.i("register","2");


                          Log.i("code",statusCode+"");
                      }

                      @Override
                      public void onFailure(Call<Post_Response> call, Throwable t) {
                          Log.i("code1",t.getMessage());

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
