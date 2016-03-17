package com.intra.bionic.intratuinapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class RegisterActivity extends Activity implements OnClickListener {

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
    DatePicker Birthday;

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.etFirstName:

                break;

            case R.id.etLastName:

                break;

            case R.id.etMailAddress:

                break;

            case R.id.etPassword:

                break;

            case R.id.etRePassword:

                break;

            case R.id.rbMale:

                break;

            case R.id.rbFemale:

                break;

            case R.id.bSignUp:

                break;

            case R.id.bBirthday:

                break;
        }
    }
}
