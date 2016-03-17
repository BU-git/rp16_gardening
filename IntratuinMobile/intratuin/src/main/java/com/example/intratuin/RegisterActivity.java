package com.example.intratuin;

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

        rbMale.setOnClickListener(this);
        rbFemale.setOnClickListener(this);
        bSignUp.setOnClickListener(this);
        bBirthday.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.rbMale:

                break;

            case R.id.rbFemale:

                break;

            case R.id.bSignUp:

                break;

            case R.id.bBirthday:
                DialogFragment dateDialog = new DatePickerFragment();
                dateDialog.show(getSupportFragmentManager(), "Intratuin");
                break;
        }
    }
}
