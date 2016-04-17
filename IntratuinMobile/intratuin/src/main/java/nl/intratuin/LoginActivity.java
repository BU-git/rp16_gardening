package nl.intratuin;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.net.URI;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.fabric.sdk.android.Fabric;
import nl.intratuin.dto.Credentials;
import nl.intratuin.dto.LoginAndCacheResult;
import nl.intratuin.dto.TransferAccessToken;
import nl.intratuin.handlers.AuthManager;
import nl.intratuin.handlers.CacheCustomerCredentials;
import nl.intratuin.handlers.ErrorFragment;
import nl.intratuin.net.RequestResponse;
import nl.intratuin.net.UriConstructor;
import nl.intratuin.settings.Settings;

import static nl.intratuin.settings.Settings.sha1;

import nl.intratuin.dto.TransferMessage;

public class LoginActivity extends AppCompatActivity implements OnClickListener {
    public static final List<String> PERMISSIONS = Arrays.asList("email");
    public static final String LOGIN_SUCCESS = "Login is successful";
    public static final String LOGIN_ERROR = "Sorry, your username and password are incorrect - please try again";

    CallbackManager callbackManager;

    LoginButton lbFacebook;
    TwitterLoginButton bTwitterHidden;
    Button bTwitter;
    Button bLogin;
    Button bRegister;
    Button bForgot;
    EditText etEmailAddress;
    EditText etPassword;
    CheckBox cbRemember;
    CheckBox cbShow;
    ImageView ivIntratuin;

    URI loginUri = null;
    URI twitterLoginUri = null;
    URI facebookLoginUri = null;
    String loginByCache;

    public static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15})";
    Pattern pattern;
    Matcher matcher;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CacheCustomerCredentials.cache(this); //Check cache

        getSupportActionBar().hide();
        TwitterAuthConfig authConfig = Settings.getTwitterConfig();
        Fabric.with(this, new Twitter(authConfig));
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        bTwitterHidden = (TwitterLoginButton) findViewById(R.id.bTwitterHidden);
        bTwitter = (Button) findViewById(R.id.bTwitter);
        lbFacebook = (LoginButton) findViewById(R.id.bLoginFacebook);

        etEmailAddress = (EditText) findViewById(R.id.etEmailAddress);
        etEmailAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pattern = Pattern.compile(EMAIL_PATTERN);
                matcher = pattern.matcher(etEmailAddress.getText().toString());
                if (!hasFocus && !matcher.matches()) {
                    showEmailError();
                }
            }
        });

        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pattern = Pattern.compile(PASSWORD_PATTERN);
                matcher = pattern.matcher(etPassword.getText().toString());
                if (!hasFocus && !matcher.matches()) {
                    showPassError();
                }
            }
        });

        bLogin = (Button) findViewById(R.id.bLogin);
        bRegister = (Button) findViewById(R.id.bRegister);
        bForgot = (Button) findViewById(R.id.bForgot);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        cbShow = (CheckBox) findViewById(R.id.cbShow);
        ivIntratuin = (ImageView) findViewById(R.id.ivIntratuin);

        Typeface fontTwitter = Typeface.createFromAsset(getAssets(), "fonts/GothamNarrow.ttf");
        bTwitter.setTypeface(fontTwitter, Typeface.BOLD);

        bTwitter.setOnClickListener(this);
        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);
        bForgot.setOnClickListener(this);
        cbShow.setOnClickListener(this);
        ivIntratuin.setOnClickListener(this);
        ivIntratuin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ivIntratuin.performClick();
                }
            }
        });

        bTwitter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bTwitterHidden.performClick();
            }
        });
        bTwitterHidden.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                twitterLoginUri = new UriConstructor(getSupportFragmentManager()).makeFullURI("/customer/loginTwitter");
                if (twitterLoginUri != null) {
                    final Credentials credentials = new Credentials();
                    TwitterAuthClient authClient = new TwitterAuthClient();
                    authClient.requestEmail(session, new Callback<String>() {
                        @Override
                        public void success(Result<String> result) {
                            try {
                                String email = result.data;
                                credentials.setEmail(email);
                                credentials.setPassword(Settings.getEncryptedTwitterKey(email));

                                AsyncTask<Credentials, Void, TransferMessage> jsonRespond =
                                        new RequestResponse<Credentials, TransferMessage>(twitterLoginUri, 3,
                                                TransferMessage.class, getSupportFragmentManager()).execute(credentials);
                                TransferMessage respondMessage = jsonRespond.get();
                                if (respondMessage.getMessage().equals(LOGIN_SUCCESS)) {
                                    startActivity(new Intent(LoginActivity.this, SearchActivity.class));
                                }
                            } catch (SignatureException | InterruptedException | ExecutionException e) {
                                ErrorFragment ef = ErrorFragment.newError("Encryption error!");
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

        lbFacebook.setReadPermissions(PERMISSIONS);
        lbFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                facebookLoginUri = new UriConstructor(getSupportFragmentManager()).makeFullURI("/customer/loginFacebook");
                TransferAccessToken accessToken = new TransferAccessToken(loginResult.getAccessToken().getToken());

                AsyncTask<TransferAccessToken, Void, TransferMessage> jsonRespond =
                        new RequestResponse<TransferAccessToken, TransferMessage>(facebookLoginUri, 3,
                                TransferMessage.class, getSupportFragmentManager()).execute(accessToken);
                TransferMessage respondMessage = null;
                try {
                    respondMessage = jsonRespond.get();
                } catch (InterruptedException | ExecutionException e) {
                    ErrorFragment ef = ErrorFragment.newError("Error!");
                    ef.show(getSupportFragmentManager(), "Intratuin");
                }
                if (respondMessage.getMessage().equals(LOGIN_SUCCESS)) {
                    App.getAuthManager().loginAndCache(AuthManager.PREF_FACEBOOK, accessToken.getAccessToken());
                    Toast.makeText(LoginActivity.this, App.getAuthManager().getAccessTokenFacebook(), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, SearchActivity.class));
                }
            }

            @Override
            public void onCancel() {
                ErrorFragment ef = ErrorFragment.newError("Login attempt canceled.");
                ef.show(getSupportFragmentManager(), "Intratuin");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("API FAILED!!!!: ", error.getMessage() + "  " + error);
                ErrorFragment ef = ErrorFragment.newError("Login attempt failed.");
                ef.show(getSupportFragmentManager(), "Intratuin");
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        callbackManager.onActivityResult(requestCode, resultCode, data);
        bTwitterHidden.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bLogin:
                LoginAndCacheResult respondToLogin = new LoginAndCacheResult();
                Credentials crd = new Credentials();
                loginUri = new UriConstructor(getSupportFragmentManager()).makeFullURI("/customer/login");

                if (loginUri != null && dataValidation()) {//&& Data validation passed
                    crd.setEmail(etEmailAddress.getText().toString());
                    crd.setFlagToCache(cbRemember.isChecked());
                    try {
                        crd.setPassword(sha1(etPassword.getText().toString(), crd.getEmail()));

                        AsyncTask<Credentials, Void, LoginAndCacheResult> jsonRespond =
                                new RequestResponse<Credentials, LoginAndCacheResult>(loginUri, 3,
                                        LoginAndCacheResult.class, getSupportFragmentManager()).execute(crd);
                        respondToLogin = jsonRespond.get();
                        if (respondToLogin != null) {
                            if (respondToLogin.getAccessKey() != null) {
                                App.getAuthManager().loginAndCache(AuthManager.PREF_CREDENTIALS, respondToLogin.getAccessKey());
                                Toast.makeText(this, respondToLogin.getMessage() + ", your key: " + App.getAuthManager().getAccessKeyCredentials(), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(this, SearchActivity.class));
                            } else {
                                if (respondToLogin.getMessage().equals(LOGIN_SUCCESS))
                                    startActivity(new Intent(this, SearchActivity.class));
                                else {
                                    ErrorFragment.newError(LOGIN_ERROR)
                                            .show(getSupportFragmentManager(), "Intratuin");
                                }}
                        } else {
                            ErrorFragment ef = ErrorFragment.newError("Request error!");
                            ef.show(getSupportFragmentManager(), "Intratuin");
                        }
                    } catch (SignatureException | InterruptedException | ExecutionException e) {
                        Log.e("Error", e.getMessage() + ": ", e);
                        ErrorFragment ef = ErrorFragment.newError("Password encryption error!");
                        ef.show(getSupportFragmentManager(), "Intratuin");
                    }
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
                if (cbShow.isChecked())
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etPassword.setSelection(etPassword.length());
                break;

            case R.id.ivIntratuin:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.intratuin.nl/"));
                startActivity(browserIntent);
                break;
        }

    }


    private void showEmailError() {
        if (etEmailAddress.getText().length() == 0)
            etEmailAddress.setError("Email can't be blank!");
        else etEmailAddress.setError("Wrong email format!");
    }

    private void showPassError() {
        if (etPassword.getText().length() == 0)
            etPassword.setError("Password can't be blank!");
        else etPassword.setError("Password has to be 6-15 chars, at least 1 small letter, " +
                "1 cap. letter and 1 number");
    }

    private boolean dataValidation() {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(etEmailAddress.getText().toString());
        boolean emailError = !matcher.matches();
        if (emailError) {
            showEmailError();
        }

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(etPassword.getText().toString());
        boolean passError = !matcher.matches();
        if (passError) {
            showPassError();
        }

        boolean validationError = emailError || passError;
        return !validationError;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://nl.intratuin/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://nl.intratuin/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}