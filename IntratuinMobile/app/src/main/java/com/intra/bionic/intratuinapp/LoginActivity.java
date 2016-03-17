package com.intra.bionic.intratuinapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener {

    TextView tvEmailAddress;
    TextView tvPassword;
    TextView tvRegisterLink;
    EditText etEmailAddress;
    EditText etPassword;
    Button bLogin;
    Button bLoginTwitter;
    Button bLoginFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvEmailAddress = (TextView)findViewById(R.id.tvEmailAddress);
        tvPassword = (TextView)findViewById(R.id.tvPassword);
        etEmailAddress = (EditText)findViewById(R.id.etEmailAddress);
        etPassword = (EditText)findViewById(R.id.etPassword);
        bLoginTwitter = (Button)findViewById(R.id.bLoginTwitter);
        bLoginFacebook = (Button)findViewById(R.id.bLoginFacebook);
        bLogin = (Button)findViewById(R.id.bLogin);
        tvRegisterLink = (TextView)findViewById(R.id.tvRegisterLink);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bLogin:

                break;

            case R.id.tvRegisterLink:
                Intent registerIntent = new Intent(this, RegisterActivity.class);
                startActivity(registerIntent);
                break;

            case R.id.tvEmailAddress:

                break;

            case R.id.tvPassword:

                break;

            case R.id.etEmailAddress:

                break;

            case R.id.etPassword:

                break;

            case R.id.bLoginTwitter:

                break;

            case R.id.bLoginFacebook:

                break;
        }
    }
}

