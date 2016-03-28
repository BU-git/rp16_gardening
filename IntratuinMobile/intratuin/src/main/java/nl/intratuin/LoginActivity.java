package nl.intratuin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.fabric.sdk.android.Fabric;
import nl.intratuin.dto.Credentials;
import nl.intratuin.dto.TransferAccessToken;
import nl.intratuin.handlers.ErrorFragment;
import nl.intratuin.net.*;
import nl.intratuin.settings.Settings;


public class LoginActivity extends AppCompatActivity implements OnClickListener {
    CallbackManager callbackManager;

    TextView tvInfo;
    LoginButton lbFacebook;
    TwitterLoginButton bTwitter;
    EditText etEmailAddress;
    EditText etPassword;
    Button bLogin;
    Button bRegister;
    Button bForgot;
    CheckBox cbRemember;
    CheckBox cbShow;

    ImageView ivIntratuin;

    URI loginUri=null;
    URI twitterLoginUri=null;
    URI facebookLoginUri = null;

    public static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15})";
    Pattern pattern;
    Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = Settings.getTwitterConfig();
        Fabric.with(this, new Twitter(authConfig));
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        bTwitter = (TwitterLoginButton) findViewById(R.id.bTwitter);
        lbFacebook = (LoginButton) findViewById(R.id.bLoginFacebook);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        etEmailAddress = (EditText)findViewById(R.id.etEmailAddress);
        etEmailAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pattern = Pattern.compile(EMAIL_PATTERN);
                matcher = pattern.matcher(etEmailAddress.getText().toString());
                if(!hasFocus && !matcher.matches()){
                    etEmailAddress.setError("Email must be like email");
                }
            }
        });

        etPassword = (EditText)findViewById(R.id.etPassword);
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pattern = Pattern.compile(PASSWORD_PATTERN);
                matcher = pattern.matcher(etPassword.getText().toString());
                if(!hasFocus && !matcher.matches()){
                    etPassword.setError("Password has to be 6-15 chars, at least 1 small letter, 1 cap. letter and 1 number");
                }
            }
        });


        bLogin = (Button) findViewById(R.id.bLogin);
        bRegister = (Button) findViewById(R.id.bRegister);
        bForgot = (Button) findViewById(R.id.bForgot);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        cbShow = (CheckBox) findViewById(R.id.cbShow);
        ivIntratuin = (ImageView) findViewById(R.id.ivIntratuin);

        bTwitter.setOnClickListener(this);
        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);
        bForgot.setOnClickListener(this);
        cbShow.setOnClickListener(this);
        ivIntratuin.setOnClickListener(this);

        bTwitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                twitterLoginUri=new UriConstructor(getSupportFragmentManager()).makeFullURI("/customer/loginTwitter");
                if(twitterLoginUri!=null){
                    final Credentials credentials=new Credentials();
                    TwitterAuthClient authClient = new TwitterAuthClient();
                    authClient.requestEmail(session, new Callback<String>() {
                        @Override
                        public void success(Result<String> result) {
                            try {
                                String email = result.data;
                                credentials.setEmail(email);
                                credentials.setPassword(Settings.getEncryptedTwitterKey(email));

                                new RequestResponse<Credentials>(twitterLoginUri, 3,
                                        getSupportFragmentManager()).execute(credentials);
                            } catch(SignatureException e){
                                ErrorFragment ef= ErrorFragment.newError("Encryption error!");
                                ef.show(getSupportFragmentManager(), "Intratuin");
                            }
                        }

                        @Override
                        public void failure(TwitterException exception) {
                            ErrorFragment ef = ErrorFragment.newError("Can't get email from Twitter!");
                            ef.show(getSupportFragmentManager(), "Intratuin");
                        }
                    });
                }
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

        lbFacebook.setReadPermissions(Arrays.asList("email", "user_birthday", "user_location"));
        lbFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                facebookLoginUri = new UriConstructor(getSupportFragmentManager()).makeFullURI("/customer/loginFacebook");
                TransferAccessToken accessToken = new TransferAccessToken(loginResult.getAccessToken().getToken());

                new RequestResponse<TransferAccessToken>(facebookLoginUri, 3, getSupportFragmentManager()).execute(accessToken);
            }

            @Override
            public void onCancel() {
                tvInfo.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException error) {
                tvInfo.setText("Login attempt failed.");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        callbackManager.onActivityResult(requestCode, resultCode, data);
        bTwitter.onActivityResult(requestCode, resultCode, data);
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bLogin:
                loginUri=new UriConstructor(getSupportFragmentManager()).makeFullURI("/customer/login");
                //DATA VALIDATION MUST BE HERE!
                //boolean formatCorrect=formatErrorManaging();
                if(loginUri!=null){//&& Data validation passed
                    Credentials crd=new Credentials();
                    crd.setEmail(etEmailAddress.getText().toString());
                    crd.setPassword(etPassword.getText().toString());

                    new RequestResponse<Credentials>(loginUri, 3,
                            getSupportFragmentManager()).execute(crd);
                }
                break;

            case R.id.bRegister:
                Intent registerIntent = new Intent(this, RegisterActivity.class);
                startActivity(registerIntent);
                break;

            case R.id.bForgot:
                Intent recoverIntent = new Intent(this, RecoverActivity.class);
                startActivity(recoverIntent);
                break;

            case R.id.cbShow:
                if(cbShow.isChecked())
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etPassword.setSelection(etPassword.length());
                break;

            case R.id.ivIntratuin:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.intratuin.nl/"));
                startActivity(browserIntent);
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

