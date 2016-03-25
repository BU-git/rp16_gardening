package nl.intratuin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.net.URI;

import io.fabric.sdk.android.Fabric;
import nl.intratuin.dto.Credentials;
import nl.intratuin.dto.TwitterLogin;
import nl.intratuin.net.*;
import nl.intratuin.settings.Settings;

//import com.facebook.CallbackManager;
//import com.facebook.FacebookSdk;
//import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity implements OnClickListener {

    Button bFacebook;
    TwitterLoginButton bTwitter;
    EditText etEmailAddress;
    EditText etPassword;
    Button bLogin;
    Button bRegister;
    Button bForgot;
    CheckBox cbRemember;
    CheckBox cbShow;
    TextView tvResult;

    URI loginUri=null;
    URI twitterLoginUri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = Settings.getTwitterConfig();
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //callbackManager = CallbackManager.Factory.create();

        bFacebook = (Button) findViewById(R.id.bFacebook);
        bTwitter = (TwitterLoginButton) findViewById(R.id.bTwitter);
        etEmailAddress = (EditText)findViewById(R.id.etEmailAddress);
        etPassword = (EditText)findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        bRegister = (Button) findViewById(R.id.bRegister);
        bForgot = (Button) findViewById(R.id.bForgot);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        cbShow = (CheckBox) findViewById(R.id.cbShow);
        tvResult = (TextView)findViewById(R.id.tvResult);

        bFacebook.setOnClickListener(this);
        bTwitter.setOnClickListener(this);
        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);
        bForgot.setOnClickListener(this);
        cbShow.setOnClickListener(this);

        bTwitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                twitterLoginUri=new UriConstructor(tvResult).makeFullURI("/customer/loginTwitter");
                if(twitterLoginUri!=null){
                    TwitterLogin twitterLogin=new TwitterLogin();
                    twitterLogin.setEmail("alice1@com");//TODO: get email from Twitter API when app will be whitelisted
                    twitterLogin.setKey(Settings.getTwitterKey());

                    new RequestResponse<TwitterLogin>(twitterLoginUri, 3, tvResult).execute(twitterLogin);
                }
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        bTwitter.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bFacebook:
                break;
            case R.id.bLogin:
                tvResult.setText("");
                loginUri=new UriConstructor(tvResult).makeFullURI("/customer/login");
                //DATA VALIDATION MUST BE HERE!
                //boolean formatCorrect=formatErrorManaging();
                if(loginUri!=null){//&& Data validation passed
                    Credentials crd=new Credentials();
                    crd.setEmail(etEmailAddress.getText().toString());
                    crd.setPassword(etPassword.getText().toString());

                    new RequestResponse<Credentials>(loginUri, 3, tvResult).execute(crd);
                }
                break;
            case R.id.bRegister:
                Intent registerIntent = new Intent(this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.bForgot:
                break;
            case R.id.cbShow:
                if(cbShow.isChecked())
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etPassword.setSelection(etPassword.length());
                break;
        }
    }

    /*private boolean formatErrorManaging(){
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
    }*/
    /*private String emailFormatError(){
        String emailErrorText="";
        if(etEmailAddress.getText().length()==0)
            emailErrorText="You have to enter email!";
        else if(etEmailAddress.getText().toString().indexOf("@")<1 ||
                etEmailAddress.getText().toString().indexOf("@")==etEmailAddress.getText()
                        .length()-1)
            emailErrorText="Wrong email format!";
        return emailErrorText;
    }*/
    /*private String passwordFormatError(){
        String passwordErrorText="";
        if(etPassword.getText().length()==0)
            passwordErrorText="You have to enter password!";
        else if(etPassword.getText().length()<6)
            passwordErrorText="Password must be from 6 to 15 characters!";
        //else if(!etPassword.getText().toString().matches("d") ||       //Contains digit
        //        !etPassword.getText().toString().matches("[a-z]") ||   //Contains small letter
        //        !etPassword.getText().toString().matches("[A-Z]]"))    //Contains cap letter
        //    passwordErrorText="Password must contain digit, small and big letters!";//Those regexp not tested yet

        return passwordErrorText;
    }*/
}

