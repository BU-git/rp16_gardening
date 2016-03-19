package com.example.intratuin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CheckBox;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//import com.facebook.CallbackManager;
//import com.facebook.FacebookSdk;
//import com.facebook.login.widget.LoginButton;

public class LoginActivity extends ActionBarActivity implements OnClickListener {

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

    URL login;

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
            login = new URL("http","192.168.1.23",8080,"/customer/login");
        } catch (MalformedURLException e){
            tvResult.setText("Wrong URL format!");
        }
        cbRemember.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bLogin:
                boolean formatCorrect=formatErrorManaging();
                if(formatCorrect){
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

    class RequestResponse extends AsyncTask<Credentials, Void, String>{
        @Override
        protected String doInBackground(Credentials... credentials) {
            try {
                HttpURLConnection con = (HttpURLConnection) login.openConnection();
                con.setRequestMethod("POST");
                con.setConnectTimeout(1000);
                con.setReadTimeout(1000);
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestProperty("Accept", "text/plain");

                JSONObject json = credentials[0].toJSON();
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(json.toString());
                wr.flush();

                int HttpResult = con.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                    String response = br.readLine();
                    br.close();

                    return response;
                }
                return "HttpError "+HttpResult;
            } catch (IOException e) {
                return "IO Exception";
            }
        }
        @Override
        protected void onPostExecute(String res){
            tvResult.setText(res);
        }
    }
}

