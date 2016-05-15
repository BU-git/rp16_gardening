package nl.intratuin;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.sql.Date;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.intratuin.net.RequestResponse;
import nl.intratuin.net.UriConstructor;
import nl.intratuin.settings.Mainscreen;
import nl.intratuin.settings.Settings;

/**
 * The class {@code RegisterActivity} is used to provide logic on Register Activity
 * Activity, where user can register himself in app
 *
 * @see AppCompatActivity
 * @see OnClickListener
 */
public class RegisterActivity extends AppCompatActivity implements OnClickListener {

    private EditText etFirstName;
    private EditText etTussen;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPassword;
    private CheckBox cbShowPassword;
    private EditText etRePassword;
    private CheckBox cbShowRePassword;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private Button bCancel;
    private Button bSignUp;
    private ImageView ivIntratuin;

    private Pattern pattern;
    private Matcher matcher;

    private URI registerUri = null;
    private URI loginUri = null;

    /**
     * Provide logic when activity created. Mapping field, set URIs.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etTussen = (EditText) findViewById(R.id.etTussen);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        cbShowPassword = (CheckBox) findViewById(R.id.cbShowPassword);
        etRePassword = (EditText) findViewById(R.id.etRePassword);
        cbShowRePassword = (CheckBox) findViewById(R.id.cbShowRePassword);
        rbMale = (RadioButton) findViewById(R.id.rbMale);
        rbFemale = (RadioButton) findViewById(R.id.rbFemale);
        bCancel = (Button) findViewById(R.id.bCancel);
        bSignUp = (Button) findViewById(R.id.bSignUp);
        ivIntratuin = (ImageView) findViewById(R.id.ivIntratuin);

        setListeners();

        registerUri = new UriConstructor(RegisterActivity.this).makeURI("registration");
        loginUri = new UriConstructor(RegisterActivity.this).makeURI("login");
    }

    /**
     * Provide logic for clicking buttons/checkboxes.
     * Sign up when enter all data needed to register.
     *
     * @param view
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.cbShowPassword:
                if (cbShowPassword.isChecked())
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etPassword.setSelection(etPassword.length());
                break;

            case R.id.cbShowRePassword:
                if (cbShowRePassword.isChecked())
                    etRePassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else
                    etRePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etRePassword.setSelection(etRePassword.length());
                break;

            case R.id.bCancel:
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                break;

            case R.id.bSignUp:
                if (registerUri != null && dataValidation()) {//&& Data validation passed
                    JSONObject jsonObject = new JSONObject();
                    MultiValueMap<String, String> map;
                    String fullName = etFirstName.getText().toString() + " ";
                    if (etTussen.getText().length() > 0)
                        fullName += etTussen.getText().toString() + " ";
                    fullName += etLastName.getText().toString();
                    try {
                    jsonObject.put("name", fullName);
                    jsonObject.put("email", etEmail.getText().toString());
                    jsonObject.put("password", etPassword.getText().toString());

                    AsyncTask<String, Void, String> jsonRespond =
                            new RequestResponse<String, String>(registerUri, 3,
                                    String.class, App.getShowManager(), this).execute(jsonObject.toString());
                    if (jsonRespond == null) {
                        App.getShowManager().showMessage("Error! No response.", RegisterActivity.this);
                    }

                        JSONObject response = new JSONObject(jsonRespond.get());
                        if (response != null && response.has("id")) {
                            map = new LinkedMultiValueMap<>();
                            map.add("grant_type", "password");
                            map.add("client_id", etEmail.getText().toString());
                            map.add("client_secret", etPassword.getText().toString());
                            map.add("username", etEmail.getText().toString());
                            map.add("password", etPassword.getText().toString());

                            AsyncTask<MultiValueMap<String, String>, Void, String> jsonLoginRespond =
                                    new RequestResponse<MultiValueMap<String, String>, String>(loginUri, 3,
                                    String.class, App.getShowManager(), this).execute(map);
                            if (jsonLoginRespond == null) {
                                App.getShowManager().showMessage("Error! No response.", RegisterActivity.this);
                            }
                            response = new JSONObject(jsonLoginRespond.get());
                            if (response != null && response.has("token_type") && response.getString("token_type").equals("bearer")) {

                                if (Settings.getMainscreen(RegisterActivity.this) == Mainscreen.WEB)
                                    startActivity(new Intent(RegisterActivity.this, WebActivity.class).putExtra(LoginActivity.ACCESS_TOKEN, response.getString("access_token")));
                                else
                                    startActivity(new Intent(RegisterActivity.this, SearchActivity.class).putExtra(LoginActivity.ACCESS_TOKEN, response.getString("access_token")));
                                finishAffinity();
                            } else {
                                String errorStr;
                                if (response == null)
                                    errorStr = "Error! Null response!";
                                else
                                    errorStr = "Error " + response.getString("code") + ": " + response.getString("error") + ": " + response.getString("error_description");
                                App.getShowManager().showMessage(errorStr, RegisterActivity.this);
                            }
                        } else {
                            String errorStr;
                            if (response == null)
                                errorStr = "Error! Null response!";
                            else
                                errorStr = "Error " + response.getString("code") + ": " + response.getString("error") + ": " + response.getString("error_description");
                            App.getShowManager().showMessage(errorStr, RegisterActivity.this);
                        }
                    } catch (InterruptedException | ExecutionException | JSONException e) {
                        App.getShowManager().showMessage("Error! " + e.getMessage(), RegisterActivity.this);
                    }
                }
                break;

            case R.id.ivIntratuin:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.intratuin.nl/"));
                startActivity(browserIntent);
                break;
        }
    }

    /**
     * Parse date.
     *
     * @param str the str
     * @return the date
     */
    //wtf?))
    public static Date parseDate(String str) {
        String[] s = str.split("/");
        return new Date(Integer.parseInt(s[2]) - 1900, Integer.parseInt(s[0]) - 1, Integer.parseInt(s[1]));
    }

    /**
     * Setting listeners on field and buttons
     */
    private void setListeners() {
        etFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && etFirstName.getText().length() == 0) {
                    etFirstName.setError("First name can not be blank");
                }
            }
        });
        etFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                etFirstName.setError(null);
            }
        });

        etLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && etLastName.getText().length() == 0) {
                    etLastName.setError("Last name can not be blank");
                }
            }
        });
        etLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                etLastName.setError(null);
            }
        });

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pattern = Pattern.compile(LoginActivity.EMAIL_PATTERN);
                matcher = pattern.matcher(etEmail.getText().toString());
                if (!hasFocus && !matcher.matches()) {
                    showEmailError();
                }
            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pattern = Pattern.compile(LoginActivity.PASSWORD_PATTERN);
                matcher = pattern.matcher(etPassword.getText().toString());
                if (!hasFocus && !matcher.matches()) {
                    showPassError();
                }
            }
        });

        etRePassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !etPassword.getText().toString().equals(etRePassword.getText().toString())) {
                    etRePassword.setError("Your passwords are mismatches");
                }
            }
        });

        cbShowPassword.setOnClickListener(this);
        cbShowRePassword.setOnClickListener(this);
        bCancel.setOnClickListener(this);
        bSignUp.setOnClickListener(this);
        ivIntratuin.setOnClickListener(this);
        ivIntratuin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ivIntratuin.performClick();
                }
            }
        });
    }

    /**
     * Shows password validation error
     */
    //code duplication
    private void showPassError() {
        if (etPassword.getText().length() == 0)
            etPassword.setError("Password can't be blank!");
        else etPassword.setError("Password has to be 6-15 chars, at least 1 small letter, " +
                "1 cap. letter and 1 number");
    }

    /**
     * Shows email validation error
     */
    //code duplication
    private void showEmailError() {
        if (etEmail.getText().length() == 0)
            etEmail.setError("Email can't be blank!");
        else etEmail.setError("Wrong email format!");
    }

    /**
     * Validation email and password
     *
     * @return result of data validation(true if all OK)
     */
    //code duplication
    private boolean dataValidation() {
        boolean res = true;
        if (etFirstName.getText().length() == 0) {
            etFirstName.setError("First name can not be blank");
            res = false;
        }

        if (etLastName.getText().length() == 0) {
            etLastName.setError("Last name can not be blank");
            res = false;
        }

        pattern = Pattern.compile(LoginActivity.EMAIL_PATTERN);
        matcher = pattern.matcher(etEmail.getText().toString());
        if (!matcher.matches()) {
            showEmailError();
            res = false;
        }


        pattern = Pattern.compile(LoginActivity.PASSWORD_PATTERN);
        matcher = pattern.matcher(etPassword.getText().toString());
        if (!matcher.matches()) {
            showPassError();
            res = false;
        }

        if (!etPassword.getText().toString().equals(etRePassword.getText().toString())) {
            etRePassword.setError("Your passwords are mismatches");
            res = false;
        }

        return res;
    }
}
