package nl.intratuin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.net.URI;
import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.intratuin.dto.Customer;
import nl.intratuin.handlers.DatePickerFragment;
import nl.intratuin.net.*;

public class RegisterActivity extends AppCompatActivity implements OnClickListener, View.OnFocusChangeListener {

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
    ImageView ivIntratuin;

    Pattern pattern;
    Matcher matcher;

    URI registerUri=null;

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
        ivIntratuin = (ImageView) findViewById(R.id.ivIntratuin);

        bBirthday.setOnClickListener(this);
        cbShowPassword.setOnClickListener(this);
        cbShowRePassword.setOnClickListener(this);
        bCancel.setOnClickListener(this);
        bSignUp.setOnClickListener(this);
        ivIntratuin.setOnClickListener(this);

        etFirstName.setOnFocusChangeListener(this);
        etLastName.setOnFocusChangeListener(this);
        etEmail.setOnFocusChangeListener(this);
        etPassword.setOnFocusChangeListener(this);
        etRePassword.setOnFocusChangeListener(this);
        etCity.setOnFocusChangeListener(this);
        etStreet.setOnFocusChangeListener(this);
        etHouse.setOnFocusChangeListener(this);
        etPostcode.setOnFocusChangeListener(this);
        tvBirthday.setOnFocusChangeListener(this);


        registerUri=new UriConstructor(getSupportFragmentManager()).makeFullURI("/customer/add");
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
                //DATA VALIDATION MUST BE HERE!
                //boolean formatCorrect=formatErrorManaging();
                if(registerUri!=null){//&& Data validation passed
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
                    else
                        cust.setGender(0);

                    new RequestResponse<Customer>(registerUri, 3,
                            getSupportFragmentManager()).execute(cust);
                }
                break;

            case R.id.ivIntratuin:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.intratuin.nl/"));
                startActivity(browserIntent);
                break;
        }
    }

    private Date parseDate(String str){
        String[] s=str.split("/");
        return new Date(Integer.parseInt(s[2])-1900,Integer.parseInt(s[0])-1,Integer.parseInt(s[1]));
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.etEmail:
                pattern = Pattern.compile(LoginActivity.EMAIL_PATTERN);
                matcher = pattern.matcher(etEmail.getText().toString());
                if(!hasFocus && !matcher.matches()){
                    etEmail.setError("Email must be like email");
                }
                break;
            case R.id.etPassword:
                pattern = Pattern.compile(LoginActivity.PASSWORD_PATTERN);
                matcher = pattern.matcher(etPassword.getText().toString());
                if(!hasFocus && !matcher.matches()){
                    etPassword.setError("Password has to be 6-15 chars, at least 1 small letter, 1 cap. letter and 1 number");
                }
                break;
            case R.id.etRePassword:
                if(!hasFocus && !etPassword.getText().toString().equals(etRePassword.getText().toString())){
                    etRePassword.setError("Your passwords are mismatches");
                }
                break;
            case R.id.etFirstName://TODO:implement logic with gender and birthday
                if (!hasFocus){
                    etFirstName.setError("First name can not be blank");
                }
                break;
            case R.id.etLastName:
                if (!hasFocus){
                    etLastName.setError("Last name can not be blank");
                }
                break;
            case R.id.etCity:
                if (!hasFocus){
                    etCity.setError("City can not be blank");
                }
                break;
            case R.id.etStreet:
                if (!hasFocus){
                    etStreet.setError("Street can not be blank");
                }
                break;
            case R.id.etHouse:
                if (!hasFocus){
                    etHouse.setError("House number can not be blank");
                }
                break;
            case R.id.etPostcode:
                if (!hasFocus){
                    etPostcode.setError("Postcode can not be blank");
                }
                break;
            case R.id.tvBirthday:
                if (!hasFocus){
                    tvBirthday.setError("Birthday field  can not be blank");
                }
                break;
            default:
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
}
