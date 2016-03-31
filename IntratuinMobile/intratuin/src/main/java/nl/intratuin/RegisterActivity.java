package nl.intratuin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.net.URI;
import java.security.SignatureException;
import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.intratuin.dto.Customer;
import nl.intratuin.handlers.DatePickerFragment;
import nl.intratuin.handlers.ErrorFragment;
import nl.intratuin.net.*;

import static nl.intratuin.settings.Settings.sha1;

public class RegisterActivity extends AppCompatActivity implements OnClickListener {

    EditText etFirstName;
    EditText etTussen;
    EditText etLastName;
    EditText etEmail;
    EditText etCity;
    EditText etStreet;
    EditText etHouse;
    EditText etPostcode;
    EditText etBirthday;
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
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        etFirstName = (EditText)findViewById(R.id.etFirstName);
        etTussen = (EditText)findViewById(R.id.etTussen);
        etLastName = (EditText)findViewById(R.id.etLastName);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etCity = (EditText)findViewById(R.id.etCity);
        etStreet = (EditText)findViewById(R.id.etStreet);
        etHouse = (EditText)findViewById(R.id.etHouse);
        etPostcode = (EditText)findViewById(R.id.etPostcode);
        etBirthday = (EditText)findViewById(R.id.etBirthday);
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

        setListeners();

        registerUri=new UriConstructor(getSupportFragmentManager()).makeFullURI("/customer/add");
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.etBirthday:
                etBirthday.setError(null);
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
                if(registerUri!=null && dataValidation()){//&& Data validation passed
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
                    cust.setBirthday(parseDate(etBirthday.getText().toString()));
                    cust.setPhoneNumber(etPhone.getText().toString());
                    if(rbMale.isChecked())
                        cust.setGender(1);
                    else
                        cust.setGender(0);
                    try {
                        cust.setPassword(sha1(etPassword.getText().toString(), cust.getEmail()));
                        new RequestResponse<Customer>(registerUri, 3,
                                getSupportFragmentManager()).execute(cust);
                    } catch(SignatureException e){
                        ErrorFragment ef= ErrorFragment.newError("Password encryption error!");
                        ef.show(getSupportFragmentManager(), "Intratuin");
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

        etCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && etCity.getText().length()==0){
                    etCity.setError("City can not be blank");
                }
            }
        });
        etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                etCity.setError(null);
            }
        });

        etStreet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && etStreet.getText().length() == 0) {
                    etStreet.setError("Street can not be blank");
                }
            }
        });
        etStreet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                etStreet.setError(null);
            }
        });

        etHouse.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && etHouse.getText().length() == 0) {
                    etHouse.setError("House number can not be blank");
                }
            }
        });
        etHouse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                etHouse.setError(null);
            }
        });

        etPostcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && etPostcode.getText().length() == 0) {
                    etPostcode.setError("Postcode can not be blank");
                }
            }
        });
        etPostcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                etPostcode.setError(null);
            }
        });

        etBirthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && etBirthday.getText().length() == 0) {
                    etBirthday.setError("Birthday field can not be blank");
                }
            }
        });

        etBirthday.setOnClickListener(this);
        etBirthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etBirthday.performClick();
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

        if(etCity.getText().length()==0){
            etCity.setError("City can not be blank");
            res=false;
        }

        if(etStreet.getText().length()==0){
            etStreet.setError("Street can not be blank");
            res=false;
        }

        if(etHouse.getText().length()==0){
            etHouse.setError("House can not be blank");
            res=false;
        }

        if(etPostcode.getText().length()==0){
            etPostcode.setError("Postcode can not be blank");
            res=false;
        }

        if(etBirthday.getText().length()==0){
            etBirthday.setError("Birthday can not be blank");
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
