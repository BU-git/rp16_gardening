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
    TextView tvEmailError;
    EditText etPassword;
    TextView tvPasswordError;
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
        tvEmailError = (TextView)findViewById(R.id.tvEmailError);
        etPassword = (EditText)findViewById(R.id.etPassword);
        tvPasswordError = (TextView)findViewById(R.id.tvPasswordError);
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
                boolean formatCorrect=formatErrorManaging();
                if(formatCorrect){
                    
                }
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

    private boolean formatErrorManaging(){
        //Checks formats, return true if everything is correct. Shows error and returns false if not
        String emailErrorText=emailFormatError();

        if(emailErrorText!=""){
            tvEmailError.setVisibility(View.VISIBLE);
            tvEmailError.setText(emailErrorText);
        }
        else{
            tvEmailError.setVisibility(View.GONE);
            tvEmailError.setText("");
        }

        String passwordErrorText=passwordFormatError();

        if(passwordErrorText!=""){
            tvPasswordError.setVisibility(View.VISIBLE);
            tvPasswordError.setText(passwordErrorText);
        }
        else{
            tvPasswordError.setVisibility(View.GONE);
            tvPasswordError.setText("");
        }

        if(emailErrorText=="" && passwordErrorText=="")
            return true;
        return false;
    }
    private String emailFormatError(){
        String emailErrorText="";
        if(etEmailAddress.getText().length()==0)
            emailErrorText="You have to enter email!";
        else if(etEmailAddress.getText().toString().indexOf("@")<1 ||
                etEmailAddress.getText().toString().indexOf("@")==etEmailAddress.getText()
                        .length()-1)
            emailErrorText="Wrong email format!";
        return emailErrorText;
    }
    private String passwordFormatError(){
        String passwordErrorText="";
        if(etEmailAddress.getText().length()==0)
            passwordErrorText="You have to enter password!";
        if(etPassword.getText().length()<6 || etPassword.getText().length()>15)
            passwordErrorText="Password must be from 6 to 15 characters!";
        if(!etPassword.getText().toString().matches("d") ||
                !etPassword.getText().toString().matches("[a-z]") ||
                !etPassword.getText().toString().matches("[A-Z]]"))
            passwordErrorText="Password must contain digit, small and big letters!";

        return passwordErrorText;
    }
}

