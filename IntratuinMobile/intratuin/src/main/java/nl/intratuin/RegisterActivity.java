package nl.intratuin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import nl.intratuin.dto.Customer;
import nl.intratuin.dto.Message;
import nl.intratuin.handlers.DatePickerFragment;
import nl.intratuin.settings.Settings;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Date;

public class RegisterActivity extends AppCompatActivity implements OnClickListener {

    EditText etFirstName;
    EditText etTussen;
    EditText etLastName;
    EditText etEmail;
    EditText etCity;
    EditText etStreet;
    EditText etHouse;
    EditText etPostcode;
    TextView tvBirthday;
    Button bBirthday;
    EditText etPassword;
    CheckBox cbShowPassword;
    EditText etRePassword;
    CheckBox cbShowRePassword;
    EditText etPhone;
    RadioButton rbMale;
    RadioButton rbFemale;
    Button bCancel;
    Button bSignUp;
    TextView tvResult;

    URI register=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFirstName = (EditText)findViewById(R.id.etFirstName);
        etTussen = (EditText)findViewById(R.id.etTussen);
        etLastName = (EditText)findViewById(R.id.etLastName);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etCity = (EditText)findViewById(R.id.etCity);
        etStreet = (EditText)findViewById(R.id.etStreet);
        etHouse = (EditText)findViewById(R.id.etHouse);
        etPostcode = (EditText)findViewById(R.id.etPostcode);
        tvBirthday = (TextView)findViewById(R.id.tvBirthday);
        bBirthday = (Button)findViewById(R.id.bBirthday);
        etPassword = (EditText)findViewById(R.id.etPassword);
        cbShowPassword = (CheckBox) findViewById(R.id.cbShowPassword);
        etRePassword = (EditText)findViewById(R.id.etRePassword);
        cbShowRePassword = (CheckBox) findViewById(R.id.cbShowRePassword);
        etPhone = (EditText)findViewById(R.id.etPhone);
        rbMale = (RadioButton)findViewById(R.id.rbMale);
        rbFemale = (RadioButton)findViewById(R.id.rbFemale);
        bCancel = (Button)findViewById(R.id.bCancel);
        bSignUp = (Button)findViewById(R.id.bSignUp);
        tvResult = (TextView)findViewById(R.id.tvResult);

        bBirthday.setOnClickListener(this);
        cbShowPassword.setOnClickListener(this);
        cbShowRePassword.setOnClickListener(this);
        bCancel.setOnClickListener(this);
        bSignUp.setOnClickListener(this);

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

            case R.id.bBirthday:
                DialogFragment dateDialog = new DatePickerFragment();
                dateDialog.show(getSupportFragmentManager(), "Intratuin");
                break;
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
                tvResult.setText("");
                //DATA VALIDATION MUST BE HERE!
                //boolean formatCorrect=formatErrorManaging();
                if(register!=null){//&& Data validation passed
                    Customer cust=new Customer();
                    cust.setId(0);
                    cust.setFirstName(etFirstName.getText().toString());
                    cust.setTussen(etTussen.getText().toString());
                    cust.setLastName(etLastName.getText().toString());
                    cust.setEmail(etEmail.getText().toString());
                    cust.setCity(etCity.getText().toString());
                    cust.setStreetName(etStreet.getText().toString());
                    cust.setHouseNumber(etHouse.getText().toString());
                    cust.setPostalCode(etPostcode.getText().toString());
                    cust.setBirthday(parseDate(tvBirthday.getText().toString()));
                    cust.setPassword(etPassword.getText().toString());
                    cust.setPhoneNumber(etPhone.getText().toString());
                    if(rbMale.isChecked())
                        cust.setGender(1);
                    else cust.setGender(0);

                    new RequestResponse().execute(cust);
                }
                break;
        }
    }
    /*private boolean formatErrorManaging(){
        tvError.setText("");
        String errorText=emptyFieldError();
        if(errorText!=""){
            tvError.setText(errorText);
            return false;
        }
        errorText=longFieldError();
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
    }*/
    /*private String emptyFieldError(){
        if(etFirstName.getText().length()==0)
            return "You have to enter first name!";
        if(etLastName.getText().length()==0)
            return "You have to enter last name!";
        if(tvBirthday.getText().length()==0)
            return "You have to enter birth date!";
        return "";
    }*/
    /*private String longFieldError(){
        if(etFirstName.getText().length()>100)
            return "First name is too long!";
        if(etLastName.getText().length()>100)
            return "Last name is too long!";
        return "";
    }*/
    /*private String emailFormatError(){
        String emailErrorText="";
        if(etMailAddress.getText().length()==0)
            emailErrorText="You have to enter email!";
        else if(etMailAddress.getText().toString().indexOf("@")<1 ||
                etMailAddress.getText().toString().indexOf("@")==etMailAddress.getText()
                        .length()-1)
            emailErrorText="Wrong email format!";
        else if(etMailAddress.getText().toString().indexOf("@")!=
                etMailAddress.getText().toString().lastIndexOf("@"))
            emailErrorText="Wrong email format!";
        return emailErrorText;
    }*/
    /*private String passwordFormatError(){
        String passwordErrorText="";
        if(etPassword.getText().length()==0)
            passwordErrorText="You have to enter password!";
        else if(etPassword.getText().length()<6 || etPassword.getText().length()>15)
            passwordErrorText="Password must be from 6 to 15 characters!";
        //else if(!etPassword.getText().toString().matches("d") ||       //Contains digit
        //        !etPassword.getText().toString().matches("[a-z]") ||   //Contains small letter
        //        !etPassword.getText().toString().matches("[A-Z]]"))    //Contains cap letter
        //    passwordErrorText="Password must contain digit, small and big letters!";//Those regexp not tested yet

        return passwordErrorText;
    }*/
    /*private String rePasswordFormatError() {
        String passwordErrorText="";
        if(etRePassword.getText().length()==0)
            passwordErrorText="You have to re-enter password!";
        else if(!etPassword.getText().toString().equals(etRePassword.getText().toString()))
            passwordErrorText="Passwords in two fields does not match!";
        return passwordErrorText;
    }*/
    /*private String sexError() {
        if(rbFemale.isChecked()||rbMale.isChecked())
            return "";
        return "You have to select sex!";
    }*/
    private Date parseDate(String str){
        String[] s=str.split("/");
        return new Date(Integer.parseInt(s[2])-1900,Integer.parseInt(s[0])-1,Integer.parseInt(s[1]));
    }

    class RequestResponse extends AsyncTask<Customer, Void, Message> {
        @Override
        protected Message doInBackground(Customer... customer) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                SimpleClientHttpRequestFactory rf =
                        (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
                rf.setReadTimeout(2000);
                rf.setConnectTimeout(2000);
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
