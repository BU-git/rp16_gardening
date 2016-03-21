package com.example.intratuin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.intratuin.dto.Message;
import com.example.intratuin.handlers.DatePickerFragment;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Date;
import com.example.intratuin.dto.Customer;
import com.example.intratuin.settings.Settings;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RegisterActivity extends ActionBarActivity implements OnClickListener {

    TextView tvFN;
    TextView tvLN;
    TextView tvMailAddress;
    TextView tvPassword;
    TextView tvRePassword;
    TextView tvBirthday;
    EditText etFirstName;
    EditText etLastName;
    EditText etMailAddress;
    EditText etPassword;
    EditText etRePassword;
    RadioButton rbMale;
    RadioButton rbFemale;
    Button bSignUp;
    Button bBirthday;
    TextView tvError;
    TextView tvResult;

    URI register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvFN = (TextView)findViewById(R.id.tvFN);
        tvLN = (TextView)findViewById(R.id.tvLN);
        tvMailAddress = (TextView)findViewById(R.id.tvMailAddress);
        tvPassword = (TextView)findViewById(R.id.tvPassword);
        tvRePassword = (TextView)findViewById(R.id.tvRePassword);
        tvBirthday = (TextView)findViewById(R.id.tvBirthday);
        etFirstName = (EditText)findViewById(R.id.etFirstName);
        etLastName = (EditText)findViewById(R.id.etLastName);
        etMailAddress = (EditText)findViewById(R.id.etMailAddress);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etRePassword = (EditText)findViewById(R.id.etRePassword);
        rbMale = (RadioButton)findViewById(R.id.rbMale);
        rbFemale = (RadioButton)findViewById(R.id.rbFemale);
        bSignUp = (Button)findViewById(R.id.bSignUp);
        bBirthday = (Button)findViewById(R.id.bBirthday);
        tvError = (TextView)findViewById(R.id.tvError);
        tvResult = (TextView)findViewById(R.id.tvResult);

        rbMale.setOnClickListener(this);
        rbFemale.setOnClickListener(this);
        bSignUp.setOnClickListener(this);
        bBirthday.setOnClickListener(this);

        try {
            register = new URL("http", Settings.getHost(),8080,"/customer/add").toURI();
        } catch (MalformedURLException e){
            tvResult.setText("Wrong URL format!");
        } catch (URISyntaxException e){
            tvResult.setText("Wrong URI format!");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.rbMale:

                break;

            case R.id.rbFemale:

                break;

            case R.id.bSignUp:
                boolean formatCorrect=formatErrorManaging();
                if(formatCorrect){
                    Customer cust=new Customer();
                    cust.setId(0);
                    cust.setFirstName(etFirstName.getText().toString());
                    cust.setLastName(etLastName.getText().toString());
                    cust.setEmail(etMailAddress.getText().toString());
                    cust.setPassword(etPassword.getText().toString());
                    cust.setBirthday(parseDate(tvBirthday.getText().toString()));

                    new RequestResponse().execute(cust);
                }
                break;

            case R.id.bBirthday:
                DialogFragment dateDialog = new DatePickerFragment();
                dateDialog.show(getSupportFragmentManager(), "Intratuin");
                break;
        }
    }
    private boolean formatErrorManaging(){
        tvError.setText("");
        String errorText=emptyFieldError();
        if(errorText!=""){
            tvError.setText(errorText);
            return false;
        }
        errorText=emailFormatError();
        if(errorText!=""){
            tvError.setText(errorText);
            return false;
        }
        errorText=passwordFormatError();
        if(errorText!=""){
            tvError.setText(errorText);
            return false;
        }
        errorText=rePasswordFormatError();
        if(errorText!=""){
            tvError.setText(errorText);
            return false;
        }
        errorText=sexError();
        if(errorText!=""){
            tvError.setText(errorText);
            return false;
        }
        return true;
    }
    private String emptyFieldError(){
        if(etFirstName.getText().length()==0)
            return "You have to enter first name!";
        if(etLastName.getText().length()==0)
            return "You have to enter last name!";
        return "";
    }
    private String emailFormatError(){
        String emailErrorText="";
        if(etMailAddress.getText().length()==0)
            emailErrorText="You have to enter email!";
        else if(etMailAddress.getText().toString().indexOf("@")<1 ||
                etMailAddress.getText().toString().indexOf("@")==etMailAddress.getText()
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
    private String rePasswordFormatError() {
        String passwordErrorText="";
        if(etRePassword.getText().length()==0)
            passwordErrorText="You have to re-enter password!";
        else if(!etPassword.getText().toString().equals(etRePassword.getText().toString()))
            passwordErrorText="Passwords in two fields does not match!";
        return passwordErrorText;
    }
    private String sexError() {
        if(rbFemale.isChecked()||rbMale.isChecked())
            return "";
        return "You have to select sex!";
    }
    private Date parseDate(String str){
        String[] s=str.split("/");
        return new Date(Integer.parseInt(s[2]),Integer.parseInt(s[0]),Integer.parseInt(s[1]));
    }

    class RequestResponse extends AsyncTask<Customer, Void, Message> {
        @Override
        protected Message doInBackground(Customer... customer) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Message jsonObject = restTemplate.postForObject(register, customer[0], Message.class);
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
