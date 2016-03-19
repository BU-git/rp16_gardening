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
import com.example.intratuin.handlers.DatePickerFragment;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;

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

    URL register;

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
            register = new URL("http","192.168.1.23",8080,"/customer/add");
        } catch (MalformedURLException e){
            tvResult.setText("Wrong URL format!");
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
                    cust.setBirthday(new Date(2000, 1, 1));//Pick correct date!

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

    class RequestResponse extends AsyncTask<Customer, Void, String> {
        @Override
        protected String doInBackground(Customer... customer) {
            try {
                HttpURLConnection con = (HttpURLConnection) register.openConnection();
                con.setRequestMethod("POST");
                con.setConnectTimeout(1000);
                con.setReadTimeout(1000);
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestProperty("Accept", "text/plain");

                JSONObject json = customer[0].toJSON();
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
