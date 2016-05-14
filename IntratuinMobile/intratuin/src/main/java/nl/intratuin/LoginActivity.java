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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.fabric.sdk.android.Fabric;
import nl.intratuin.manager.AuthManager;
import nl.intratuin.handlers.CacheCustomerCredentials;
import nl.intratuin.net.RequestResponse;
import nl.intratuin.net.UriConstructor;
import nl.intratuin.settings.Mainscreen;
import nl.intratuin.settings.Settings;

/**
 * The class {@code LoginActivity} is used to provide logic on Login Activity
 * Activity, where user can log in, go to "restore password" activity or go to "register activity"
 *
 * @see AppCompatActivity
 * @see OnClickListener
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {
    /**
     * The constant FACEBOOK_PERMISSIONS is used for permission to get some info from FB account
     */
    public static final List<String> FACEBOOK_PERMISSIONS = Arrays.asList("email");
    /**
     * The constant ACCESS_TOKEN is used for accessing to new activities with info about user
     */
    public static final String ACCESS_TOKEN = "accessToken";

    /**
     * The constant EMAIL_PATTERN is used for validation user email
     */
    public static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    /**
     * The constant PASSWORD_PATTERN is used for validation user password
     */
    public static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15})";

    private CallbackManager callbackManager;
    private LoginButton lbFacebook;
    private TwitterLoginButton bTwitterHidden;
    private Button bTwitter;
    private Button bLogin;
    private Button bRegister;
    private Button bForgot;
    private EditText etEmailAddress;
    private EditText etPassword;
    private CheckBox cbRemember;
    private CheckBox cbShow;

    private ImageView ivIntratuin;
    private URI loginUri = null;
    private URI twitterLoginUri = null;
    private URI facebookLoginUri = null;

    private String loginByCache;
    private Pattern pattern;
    private Matcher matcher;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * Provide logic when activity created. Mapping field, callbacks to social networks.
     *
     * @param savedInstanceState
     */
    //break this method for few less
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CacheCustomerCredentials.cache(this); //Check cache

        TwitterAuthConfig authConfig = Settings.getTwitterConfig(this);
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
                twitterLoginUri = new UriConstructor(LoginActivity.this).makeURI("twitterLogin");
                if (twitterLoginUri != null) {
                    String accessTokenTwitter = session.getAuthToken().token;
                    String secretAccessTokenTwitter = session.getAuthToken().secret;
                    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                    map.add("twitter_token", accessTokenTwitter);
                    map.add("twitter_secret", secretAccessTokenTwitter);
                    try {
                        JSONObject response;
                        AsyncTask<MultiValueMap<String, String>, Void, String> jsonRespond =
                                new RequestResponse<MultiValueMap<String, String>, String>(twitterLoginUri, 3,
                                        String.class, App.getShowManager(), LoginActivity.this).execute(map);
                        response = new JSONObject(jsonRespond.get());
                        if (response != null && response.has("token_type") && response.getString("token_type").equals("bearer")) {
                            App.getAuthManager().loginAndCache(AuthManager.PREF_TWITTER_ACCESS_TOKEN, accessTokenTwitter);
                            App.getAuthManager().loginAndCache(AuthManager.PREF_TWITTER_SECRET_ACCESS_TOKEN, secretAccessTokenTwitter);
                            String accessKey = response.getString("access_token");

                            if (Settings.getMainscreen(LoginActivity.this) == Mainscreen.WEB)
                                startActivity(new Intent(LoginActivity.this, WebActivity.class).putExtra(ACCESS_TOKEN, accessKey));
                            else
                                startActivity(new Intent(LoginActivity.this, SearchActivity.class).putExtra(ACCESS_TOKEN, accessKey));
                            finish();
                        } else {
                            String errorStr;
                            if (response == null)
                                errorStr = "Error! Null response!";
                            else
                                errorStr = "Error " + response.getString("code") + ": " + response.getString("error") + ": " + response.getString("error_description");
                            App.getShowManager().showMessage(errorStr, LoginActivity.this);
                        }
                    } catch (InterruptedException | ExecutionException | JSONException e) {
                        App.getShowManager().showMessage("Encryption error!", LoginActivity.this);
                    }
                }
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

        lbFacebook.setReadPermissions(FACEBOOK_PERMISSIONS);
        lbFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                String accessToken = loginResult.getAccessToken().getToken();
                facebookLoginUri = new UriConstructor(LoginActivity.this).makeURI("facebookLogin");
                if (facebookLoginUri != null) {
                    MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
                    map.add("facebook_token", loginResult.getAccessToken().getToken());

                    AsyncTask<MultiValueMap<String, String>, Void, String> jsonRespond =
                            new RequestResponse<MultiValueMap<String, String>, String>(facebookLoginUri, 3,
                                    String.class, App.getShowManager(), LoginActivity.this).execute(map);
                    JSONObject response;
                    try {
                        response = new JSONObject(jsonRespond.get());
                        if (response != null && response.has("token_type") && response.getString("token_type").equals("bearer")) {
                            App.getAuthManager().loginAndCache(AuthManager.PREF_FACEBOOK, accessToken);
                            String accessKey = response.getString("access_token");

                            if (Settings.getMainscreen(LoginActivity.this) == Mainscreen.WEB)
                                startActivity(new Intent(LoginActivity.this, WebActivity.class).putExtra(ACCESS_TOKEN, accessKey));
                            else
                                startActivity(new Intent(LoginActivity.this, SearchActivity.class).putExtra(ACCESS_TOKEN, accessKey));
                            finish();
                        } else {
                            String errorStr;
                            if (response == null)
                                errorStr = "Error! Null response!";
                            else
                                errorStr = "Error " + response.getString("code") + ": " + response.getString("error") + ": " + response.getString("error_description");
                            App.getShowManager().showMessage(errorStr, LoginActivity.this);
                        }
                    } catch (InterruptedException | ExecutionException | JSONException e) {
                        App.getShowManager().showMessage("Error: " + e.getMessage(), LoginActivity.this);
                    }
                }
            }

            @Override
            public void onCancel() {
                App.getShowManager().showMessage("Login attempt canceled.", LoginActivity.this);
            }

            @Override
            public void onError(FacebookException error) {
                App.getShowManager().showMessage("Login attempt failed. " + error.getMessage(), LoginActivity.this);
            }
        });
    }


    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @see android.support.v4.app.FragmentActivity
     */
    //i don't understand what's going on here
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        callbackManager.onActivityResult(requestCode, resultCode, data);
        bTwitterHidden.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Provided logic by clicking different buttons
     *
     * @param view current View
     */
    @Override
    //break this method for few less
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bLogin:
                JSONObject response;
                try {
                    loginUri = new UriConstructor(LoginActivity.this).makeURI("login");
                    if (loginUri != null && dataValidation()) {
                        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
                        map.add("grant_type", "password");
                        map.add("client_id", etEmailAddress.getText().toString());
                        map.add("client_secret", etPassword.getText().toString());
                        map.add("username", etEmailAddress.getText().toString());
                        map.add("password", etPassword.getText().toString());


                        AsyncTask<MultiValueMap<String, String>, Void, String> jsonRespond =
                                new RequestResponse<MultiValueMap<String, String>, String>(loginUri, 3,
                                        String.class, App.getShowManager(), this).execute(map);
                        if (jsonRespond == null) {
                            App.getShowManager().showMessage("Error! No response.", LoginActivity.this);
                        }
                        response = new JSONObject(jsonRespond.get());
                        if (response != null && response.has("token_type") && response.getString("token_type").equals("bearer")) {
                            String accessKey = response.getString("access_token");
                            if (cbRemember.isChecked()) {
                                App.getAuthManager().loginAndCache(AuthManager.PREF_USERNAME, etEmailAddress.getText().toString());
                                App.getAuthManager().loginAndCache(AuthManager.PREF_PASSWORD, etPassword.getText().toString());
                            }

                            if (Settings.getMainscreen(LoginActivity.this) == Mainscreen.WEB)
                                startActivity(new Intent(LoginActivity.this, WebActivity.class).putExtra(ACCESS_TOKEN, accessKey));
                            else
                                startActivity(new Intent(LoginActivity.this, SearchActivity.class).putExtra(ACCESS_TOKEN, accessKey));
                            finish();
                        } else {
                            String errorStr;

                            if (response == null)
                                errorStr = "Error! Null response!";
                            else
                                errorStr = "Error " + response.getString("code") + ": " + response.getString("error") + ": " + response.getString("error_description");

                            App.getShowManager().showMessage(errorStr, LoginActivity.this);
                        }
                    }
                } catch (InterruptedException | ExecutionException | JSONException e) {
                    App.getShowManager().showMessage("Error!!! " + e.getMessage(), LoginActivity.this);
                }
                break;

            case R.id.bRegister:
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                break;

            case R.id.bForgot:
                Intent recoverIntent = new Intent(LoginActivity.this, RecoverActivity.class);
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

    /**
     * Shows email validation error
     */
    private void showEmailError() {
        if (etEmailAddress.getText().length() == 0)
            etEmailAddress.setError("Email can't be blank!");
        else etEmailAddress.setError("Wrong email format!");
    }


    /**
     * Shows password validation error
     */
    private void showPassError() {
        if (etPassword.getText().length() == 0)
            etPassword.setError("Password can't be blank!");
        else etPassword.setError("Password has to be 6-15 chars, at least 1 small letter, " +
                "1 cap. letter and 1 number");
    }

    /**
     * Validation email and password
     *
     * @return result of data validation(true if all OK)
     */
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
}