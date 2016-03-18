package com.example.intratuin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CheckBox;

//import com.facebook.CallbackManager;
//import com.facebook.FacebookSdk;
//import com.facebook.login.widget.LoginButton;

public class LoginActivity extends ActionBarActivity implements OnClickListener {

    TextView tvEmailAddress;
    TextView tvPassword;
    TextView tvRegisterLink;
    EditText etEmailAddress;
    EditText etPassword;
    Button bLogin;
    CheckBox cbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //callbackManager = CallbackManager.Factory.create();

        tvEmailAddress = (TextView) findViewById(R.id.tvEmailAddress);
        tvPassword = (TextView) findViewById(R.id.tvPassword);
        etEmailAddress = (EditText) findViewById(R.id.etEmailAddress);
        etPassword = (EditText) findViewById(R.id.etPassword);
        //bLoginTwitter = (Button)findViewById(R.id.bLoginTwitter);
        //bLoginFacebook = (LoginButton)findViewById(R.id.bLoginFacebook);
        bLogin = (Button) findViewById(R.id.bLogin);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        // bLoginTwitter.setOnClickListener(this);
        //bLoginFacebook.setOnClickListener(this);
        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);
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

            //case R.id.bLoginTwitter:

            //    break;

            //case R.id.bLoginFacebook:

            //    break;

            case R.id.cbRemember:

                break;
        }
    }
}

