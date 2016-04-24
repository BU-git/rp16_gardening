package nl.intratuin;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.sql.Date;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.intratuin.handlers.ErrorFragment;
import nl.intratuin.net.RequestResponse;
import nl.intratuin.net.UriConstructor;
import nl.intratuin.settings.Mainscreen;
import nl.intratuin.settings.Settings;

public class RegisterActivity extends AppCompatActivity implements OnClickListener {

    EditText etFirstName;
    EditText etTussen;
    EditText etLastName;
    EditText etEmail;
    EditText etPassword;
    CheckBox cbShowPassword;
    EditText etRePassword;
    CheckBox cbShowRePassword;
    RadioButton rbMale;
    RadioButton rbFemale;
    Button bCancel;
    Button bSignUp;
    ImageView ivIntratuin;

    Pattern pattern;
    Matcher matcher;

    URI registerUri=null;
    URI loginUri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        etFirstName = (EditText)findViewById(R.id.etFirstName);
        etTussen = (EditText)findViewById(R.id.etTussen);
        etLastName = (EditText)findViewById(R.id.etLastName);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        cbShowPassword = (CheckBox) findViewById(R.id.cbShowPassword);
        etRePassword = (EditText)findViewById(R.id.etRePassword);
        cbShowRePassword = (CheckBox) findViewById(R.id.cbShowRePassword);
        rbMale = (RadioButton)findViewById(R.id.rbMale);
        rbFemale = (RadioButton)findViewById(R.id.rbFemale);
        bCancel = (Button)findViewById(R.id.bCancel);
        bSignUp = (Button)findViewById(R.id.bSignUp);
        ivIntratuin = (ImageView) findViewById(R.id.ivIntratuin);

        setListeners();

        registerUri=new UriConstructor(RegisterActivity.this, getSupportFragmentManager()).makeURI("registration");
        loginUri=new UriConstructor(RegisterActivity.this, getSupportFragmentManager()).makeURI("login");
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.cbShowPassword:
                if(cbShowPassword.isChecked())
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etPassword.setSelection(etPassword.length());
                break;

            case R.id.cbShowRePassword:
                if(cbShowRePassword.isChecked())
                    etRePassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else etRePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etRePassword.setSelection(etRePassword.length());
                break;

            case R.id.bCancel:
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                break;

            case R.id.bSignUp:
                if(registerUri!=null && dataValidation()){//&& Data validation passed
                    MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
                    map.add("client_id", etEmail.getText().toString());
                    map.add("client_name", etFirstName.getText().toString());
                    map.add("client_tussen", etTussen.getText().toString());
                    map.add("client_famname", etLastName.getText().toString());
                    if(rbMale.isChecked())
                        map.add("client_gender", "1");
                    else
                        map.add("client_gender", "0");
                    map.add("client_secret", etPassword.getText().toString());

                    AsyncTask<MultiValueMap<String, String>, Void, String> jsonRespond =
                            new RequestResponse<MultiValueMap<String, String>, String>(registerUri, 3,
                                    String.class, getSupportFragmentManager(), this).execute(map);
                    if(jsonRespond==null){
                        ErrorFragment ef = ErrorFragment.newError("Error! No response.");
                        ef.show(getSupportFragmentManager(), "Intratuin");
                    }
                    try {
                        JSONObject response = new JSONObject(jsonRespond.get());
                        if (response != null && response.has("id")) {
                            map = new LinkedMultiValueMap<String, String>();
                            map.add("grant_type", "password");
                            map.add("client_id", etEmail.getText().toString());
                            map.add("client_secret", etPassword.getText().toString());
                            map.add("username", etEmail.getText().toString());
                            map.add("password", etPassword.getText().toString());

                            jsonRespond = new RequestResponse<MultiValueMap<String, String>, String>(loginUri, 3,
                                            String.class, getSupportFragmentManager(), this).execute(map);
                            if(jsonRespond==null){
                                ErrorFragment ef = ErrorFragment.newError("Error! No response.");
                                ef.show(getSupportFragmentManager(), "Intratuin");
                            }
                            response = new JSONObject(jsonRespond.get());
                            if (response!=null && response.has("token_type") && response.getString("token_type").equals("bearer")) {
                                //TODO: save access token, pass it to next activity, and remove toast!
                                Toast.makeText(RegisterActivity.this, response.getString("access_token"), Toast.LENGTH_LONG).show();
                                if(Settings.getMainscreen(RegisterActivity.this)== Mainscreen.WEB)
                                    startActivity(new Intent(RegisterActivity.this, WebActivity.class));
                                else startActivity(new Intent(RegisterActivity.this, SearchActivity.class));
                            } else {
                                String errorStr;
                                if(response==null)
                                    errorStr="Error! Null response!";
                                else errorStr="Error "+response.getString("code")+": "+response.getString("error")+": "+response.getString("error_description");
                                ErrorFragment ef = ErrorFragment.newError(errorStr);
                                ef.show(getSupportFragmentManager(), "Intratuin");
                            }
                        } else {
                            String errorStr;
                            if (response == null)
                                errorStr = "Error! Null response!";
                            else
                                errorStr = "Error " + response.getString("code") + ": " + response.getString("error") + ": " + response.getString("error_description");
                            ErrorFragment ef = ErrorFragment.newError(errorStr);
                            ef.show(getSupportFragmentManager(), "Intratuin");
                        }
                    } catch (InterruptedException | ExecutionException | JSONException e){
                        ErrorFragment ef = ErrorFragment.newError("Error!");
                        ef.show(RegisterActivity.this.getSupportFragmentManager(), "Intratuin");
                    }
                }
                break;

            case R.id.ivIntratuin:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.intratuin.nl/"));
                startActivity(browserIntent);
                break;
        }
    }

    public static Date parseDate(String str){
        String[] s=str.split("/");
        return new Date(Integer.parseInt(s[2])-1900,Integer.parseInt(s[0])-1,Integer.parseInt(s[1]));
    }

    private void setListeners(){
        etFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && etFirstName.getText().length()==0){
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
                if(!hasFocus && !etPassword.getText().toString().equals(etRePassword.getText().toString())){
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
    private void showEmailError(){
        if(etEmail.getText().length()==0)
            etEmail.setError("Email can't be blank!");
        else etEmail.setError("Wrong email format!");
    }
    private void showPassError(){
        if(etPassword.getText().length()==0)
            etPassword.setError("Password can't be blank!");
        else etPassword.setError("Password has to be 6-15 chars, at least 1 small letter, " +
                "1 cap. letter and 1 number");
    }
    private boolean dataValidation(){
        boolean res=true;
        if(etFirstName.getText().length()==0){
            etFirstName.setError("First name can not be blank");
            res=false;
        }

        if(etLastName.getText().length()==0){
            etLastName.setError("Last name can not be blank");
            res=false;
        }

        pattern = Pattern.compile(LoginActivity.EMAIL_PATTERN);
        matcher = pattern.matcher(etEmail.getText().toString());
        if(!matcher.matches()){
            showEmailError();
            res=false;
        }


        pattern = Pattern.compile(LoginActivity.PASSWORD_PATTERN);
        matcher = pattern.matcher(etPassword.getText().toString());
        if(!matcher.matches()){
            showPassError();
            res=false;
        }

        if(!etPassword.getText().toString().equals(etRePassword.getText().toString())){
            etRePassword.setError("Your passwords are mismatches");
            res=false;
        }

        return res;
    }
}
