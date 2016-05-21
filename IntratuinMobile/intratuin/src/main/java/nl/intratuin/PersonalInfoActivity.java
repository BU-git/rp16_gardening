package nl.intratuin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;

import nl.intratuin.dto.Customer;
import nl.intratuin.handlers.DatePickerFragment;
import nl.intratuin.net.RequestResponse;
import nl.intratuin.settings.Settings;

public class PersonalInfoActivity extends ToolBarActivity implements View.OnClickListener {
    private EditText firstName;
    private TextView tvFirstName;
    private TextView tvLastName;
    private EditText lastName;
    private EditText houseNumber;
    private TextView tvHouseNumber;
    private EditText streetName;
    private TextView tvStreetName;
    private EditText city;
    private TextView tvCity;
    private TextView tvPostcode;
    private EditText postcode;
    private TextView tvBirthday;
    private EditText birthday;
    private TextView tvPhone;
    private EditText phone;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private Button bSave;

    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.edit_personal_info);
        super.onCreate(savedInstanceState);

        final Bundle extra = getIntent().getExtras();
        if (extra != null) {
            customer = extra.getParcelable(ToolBarActivity.CUSTOMER);
        }

        init();
        fillEditText();
        setListeners();
    }

    private void init() {
        tvFirstName = (TextView) findViewById(R.id.tvFirstName);
        firstName = (EditText) findViewById(R.id.firstName);
        tvLastName = (TextView) findViewById(R.id.tvLastName);
        lastName = (EditText) findViewById(R.id.lastName);
        rbMale = (RadioButton) findViewById(R.id.rbMale);
        rbFemale = (RadioButton) findViewById(R.id.rbFemale);
        tvBirthday = (TextView) findViewById(R.id.tvBirthday);
        birthday = (EditText) findViewById(R.id.birthday);
        tvPhone = (TextView) findViewById(R.id.tvPhoneNumber);
        phone = (EditText) findViewById(R.id.phoneNumber);
        tvHouseNumber = (TextView) findViewById(R.id.tvHouseNumber);
        houseNumber = (EditText) findViewById(R.id.houseNumber);
        tvStreetName = (TextView) findViewById(R.id.tvStreetName);
        streetName = (EditText) findViewById(R.id.streetName);
        tvCity = (TextView) findViewById(R.id.tvCity);
        city = (EditText) findViewById(R.id.city);
        tvPostcode = (TextView) findViewById(R.id.tvPostcode);
        postcode = (EditText) findViewById(R.id.postcode);
        bSave = (Button) findViewById(R.id.bSave);
    }

    private void fillEditText() {
        firstName.setText(customer.getFirstName());
        lastName.setText(customer.getLastName());
        if (customer.getGender() == 1)
            rbMale.setChecked(true);
        else
            rbFemale.setChecked(true);

        java.sql.Date customerBirthday = customer.getBirthday();
        if (customerBirthday != null) {
            java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("dd.MM.yyyy");
            birthday.setText(simpleDateFormat.format(customerBirthday));
        }

        String customerPhoneNumber = customer.getPhoneNumber();
        if (customerPhoneNumber != null)
            phone.setText(customerPhoneNumber);

        String customerHouseNumber = customer.getHouseNumber();
        if (customerHouseNumber != null)
            houseNumber.setText(customerHouseNumber);

        String customerStreetName = customer.getStreetName();
        if (customerStreetName != null)
            streetName.setText(customerStreetName);

        String customerCity = customer.getCity();
        if (customerCity != null)
            city.setText(customerCity);

        String customerPostcode = customer.getPostalCode();
        if (customerPostcode != null)
            postcode.setText(customerPostcode);
    }

    private void setListeners() {
        firstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && firstName.getText().length() == 0) {
                    firstName.setError("First name can not be blank");
                }
            }
        });
        lastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && lastName.getText().length() == 0) {
                    lastName.setError("Last name can not be blank");
                }
            }
        });
        birthday.setOnClickListener(this);
        birthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    birthday.performClick();
                }
            }
        });

        bSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bSave:
                changedCustomer();
                URI updateURI = null;
                try {
                    updateURI = Settings.getUriConfig().getCustomerPersonal();

                    AsyncTask<Customer, Void, Boolean> jsonUpdateRespond =
                            new RequestResponse<Customer, Boolean>(updateURI, 3,
                                    Boolean.class, App.getShowManager(), this).execute(customer);
                    if (jsonUpdateRespond != null) {
                        if (jsonUpdateRespond.get()) {
                            Toast.makeText(PersonalInfoActivity.this, "you personal data is saved", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(PersonalInfoActivity.this, SearchActivity.class));
                        }
                        else
                            App.getShowManager().showMessage("Sorry, error saving, try again", this);
                    } else
                        App.getShowManager().showMessage("Error! Null response!", this);
                } catch (InterruptedException | ExecutionException e) {
                    App.getShowManager().showMessage("Exception!" + e.getMessage(), this);
                }
                break;

            case R.id.birthday:
                DialogFragment dateDialog = new DatePickerFragment();
                dateDialog.show(getSupportFragmentManager(), "Intratuin");
                break;
        }

    }

    public static java.sql.Date parseDate(String strDate) {
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("dd.MM.yyyy");
        try {
            return new java.sql.Date(simpleDateFormat.parse(strDate).getTime());
        } catch (ParseException e) {
            throw new RuntimeException("ParseDate Error!!! ", e);
        }
    }

    private void changedCustomer() {
        customer.setFirstName(firstName.getText().toString());
        customer.setLastName(lastName.getText().toString());
        customer.setPhoneNumber(phone.getText().toString());
        customer.setCity(city.getText().toString());
        customer.setStreetName(streetName.getText().toString());
        customer.setHouseNumber(houseNumber.getText().toString());
        customer.setPostalCode(postcode.getText().toString());

        java.sql.Date changedBirthday = parseDate(birthday.getText().toString());
        customer.setBirthday(changedBirthday);

        if (rbMale.isChecked())
            customer.setGender(1);
        else
            customer.setGender(0);
    }

}
