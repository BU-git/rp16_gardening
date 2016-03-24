package nl.intratuin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CheckBox;

import nl.intratuin.dto.Credentials;
import nl.intratuin.dto.Message;
import nl.intratuin.settings.Settings;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

//import com.facebook.CallbackManager;
//import com.facebook.FacebookSdk;
//import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity implements OnClickListener {

    Button bFacebook;
    Button bTwitter;
    EditText etEmailAddress;
    EditText etPassword;
    Button bLogin;
    Button bRegister;
    Button bForgot;
    CheckBox cbRemember;
    CheckBox cbShow;
    TextView tvResult;

    URI login=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //callbackManager = CallbackManager.Factory.create();

        bFacebook = (Button) findViewById(R.id.bFacebook);
        bTwitter = (Button) findViewById(R.id.bTwitter);
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

        try {
            String localUrl=Settings.usingDeployed()?"/Intratuin/customer/login":"/customer/login";
            int port=Settings.usingDeployed()?8888:8080;
            login = new URL("http", Settings.getHost(),port,localUrl).toURI();
        } catch (MalformedURLException e){
            tvResult.setText("Wrong URL format!");
        } catch (URISyntaxException e){
            tvResult.setText("Wrong URI format!");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bFacebook:
                break;
            case R.id.bTwitter:
                break;
            case R.id.bLogin:
                tvResult.setText("");
                //DATA VALIDATION MUST BE HERE!
                //boolean formatCorrect=formatErrorManaging();
                if(login!=null){//&& Data validation passed
                    Credentials crd=new Credentials();
                    crd.setEmail(etEmailAddress.getText().toString());
                    crd.setPassword(etPassword.getText().toString());

                    new RequestResponse().execute(crd);
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

    class RequestResponse extends AsyncTask<Credentials, Void, Message>{
        @Override
        protected Message doInBackground(Credentials... credentials) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                SimpleClientHttpRequestFactory rf =
                        (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
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

