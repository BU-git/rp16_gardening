package com.intra.bionic.intratuinapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

    Button bLogin;
    TextView tvRegisterLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bLogin = (Button)findViewById(R.id.bLogin);
        tvRegisterLink = (TextView)findViewById(R.id.tvRegisterLink);

        bLogin.setOnClickListener((OnClickListener) this);
        tvRegisterLink.setOnClickListener((OnClickListener) this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bLogin:
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.tvRegisterLink:
                Intent registerIntent = new Intent(this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
        }
    }
}
