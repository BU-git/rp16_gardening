package com.example.intratuin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//import com.facebook.CallbackManager;
//import com.facebook.FacebookSdk;
//import com.facebook.login.widget.LoginButton;

public class LoginActivity extends Activity implements OnClickListener {

    TextView tvEmailAddress;
    TextView tvPassword;
    TextView tvRegisterLink;
    EditText etEmailAddress;
    EditText etPassword;
    Button bLogin;
    //Button bLoginTwitter;
    //LoginButton bLoginFacebook;
   // CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //callbackManager = CallbackManager.Factory.create();

        tvEmailAddress = (TextView)findViewById(R.id.tvEmailAddress);
        tvPassword = (TextView)findViewById(R.id.tvPassword);
        etEmailAddress = (EditText)findViewById(R.id.etEmailAddress);
        etPassword = (EditText)findViewById(R.id.etPassword);
        //bLoginTwitter = (Button)findViewById(R.id.bLoginTwitter);
        //bLoginFacebook = (LoginButton)findViewById(R.id.bLoginFacebook);
        bLogin = (Button)findViewById(R.id.bLogin);
        tvRegisterLink = (TextView)findViewById(R.id.tvRegisterLink);

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
        }
    }
}

