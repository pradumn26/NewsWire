package com.developinggeek.thebetterlawyernewsapp.Activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.developinggeek.thebetterlawyernewsapp.R;

public class LoginActivity extends AppCompatActivity
{

    TextInputLayout edt_email , edt_pass;
    Button btn_login , btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button)findViewById(R.id.login_btn_login);
    }
}
