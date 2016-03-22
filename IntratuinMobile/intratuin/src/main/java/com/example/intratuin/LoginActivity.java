package com.example.intratuin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CheckBox;
import com.example.intratuin.dto.Credentials;
import com.example.intratuin.dto.Message;
import com.example.intratuin.settings.Settings;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

//import com.facebook.CallbackManager;
//import com.facebook.FacebookSdk;
//import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity implements OnClickListener {

    TextView tvEmailAddress;
    TextView tvPassword;
    TextView tvRegisterLink;
    EditText etEmailAddress;
    TextView tvEmailError;
    EditText etPassword;
    TextView tvPasswordError;
    Button bLogin;
    CheckBox cbRemember;
    TextView tvResult;

    URI login=null;

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
        bLogin = (Button) findViewById(R.id.bLogin);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        tvResult = (TextView)findViewById(R.id.tvResult);
        // bLoginTwitter.setOnClickListener(this);
        //bLoginFacebook.setOnClickListener(this);
        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);

        try {
            login = new URL("http", Settings.getHost(),8080,"/customer/login").toURI();
        } catch (MalformedURLException e){
            tvResult.setText("Wrong URL format!");
        } catch (URISyntaxException e){
            tvResult.setText("Wrong URI format!");
        }
        cbRemember.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bLogin:
                tvResult.setText("");
                boolean formatCorrect=formatErrorManaging();
                if(formatCorrect && login!=null){
                    Credentials crd=new Credentials();
                    crd.setEmail(etEmailAddress.getText().toString());
                    crd.setPassword(etPassword.getText().toString());

                    new RequestResponse().execute(crd);
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

            case R.id.cbRemember:

                break;
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
        if(etPassword.getText().length()==0)
            passwordErrorText="You have to enter password!";
        else if(etPassword.getText().length()<6 || etPassword.getText().length()>15)
            passwordErrorText="Password must be from 6 to 15 characters!";
        //else if(!etPassword.getText().toString().matches("d") ||
        //        !etPassword.getText().toString().matches("[a-z]") ||
        //        !etPassword.getText().toString().matches("[A-Z]]"))
        //    passwordErrorText="Password must contain digit, small and big letters!";

        return passwordErrorText;
    }

    class RequestResponse extends AsyncTask<Credentials, Void, Message>{
        @Override
        protected Message doInBackground(Credentials... credentials) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpComponentsClientHttpRequestFactory rf =
                        (HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory();
                rf.setReadTimeout(2000);
                rf.setConnectTimeout(2000);
                Message jsonObject = restTemplate.postForObject(login, credentials[0], Message.class);
                return jsonObject;
            } catch (Exception e) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(Message msg){
            tvResult.setText(msg==null?"Request error!":msg.getMessage());
        }
    }
}

