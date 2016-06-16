package nl.intratuin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.intratuin.dto.Customer;
import nl.intratuin.manager.AuthManager;
import nl.intratuin.net.RequestResponse;
import nl.intratuin.settings.Settings;

public class OrderDataActivity extends ToolBarActivity implements View.OnClickListener{
    private EditText firstName;
    private TextView tvFirstName;
    private TextView tvLastName;
    private EditText lastName;
    private TextView tvPhone;
    private EditText phone;
    private EditText etEmail;
    private TextView tvEmail;
    private Button bToConfirm;

    private Customer customer;

    private Pattern pattern;
    private Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.order_data);
        super.onCreate(savedInstanceState);

        customer = AuthManager.getCustomer(this);

        init();
        fillEditText();
    }

    private void init() {
        tvFirstName = (TextView) findViewById(R.id.tvFirstName);
        firstName = (EditText) findViewById(R.id.firstName);
        tvLastName = (TextView) findViewById(R.id.tvLastName);
        lastName = (EditText) findViewById(R.id.lastName);
        tvPhone = (TextView) findViewById(R.id.tvPhoneNumber);
        phone = (EditText) findViewById(R.id.phoneNumber);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        etEmail = (EditText) findViewById(R.id.etEmail);
        bToConfirm = (Button) findViewById(R.id.bToConfirm);

        bToConfirm.setOnClickListener(this);
    }

    private void fillEditText() {
        firstName.setText(customer.getFirstName());
        lastName.setText(customer.getLastName());
        etEmail.setText(customer.getEmail());

        if(customer.getPhoneNumber() != null)
            phone.setText(customer.getPhoneNumber());
    }

    private void showEmailError() {
        if (etEmail.getText().length() == 0)
            etEmail.setError("Email can't be blank!");
        else etEmail.setError("Wrong email format!");
    }

    private boolean dataValidation() {
        pattern = Pattern.compile(LoginActivity.EMAIL_PATTERN);
        matcher = pattern.matcher(etEmail.getText().toString());
        boolean emailError = !matcher.matches();
        if (emailError) {
            showEmailError();
            return false;
        }

        if (firstName.getText().length() == 0) {
           firstName.setError("First name can not be blank");
            return false;
        }

        if (lastName.getText().length() == 0) {
            lastName.setError("Last name can not be blank");
            return false;
        }

        if (phone.getText().length() == 0) {
            phone.setError("Phone number can not be blank");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (dataValidation()) {
            changedCustomer();
            URI updateURI = null;
            try {
                updateURI = Settings.getUriConfig().getCustomerPersonal();

                AsyncTask<Customer, Void, Boolean> jsonUpdateRespond =
                        new RequestResponse<Customer, Boolean>(updateURI, 3,
                                Boolean.class, App.getShowManager(), this).execute(customer);
                if (jsonUpdateRespond != null) {
                    if (jsonUpdateRespond.get()) {
                        startActivity(new Intent(OrderDataActivity.this, OrderConfirmActivity.class));
                    } else
                        App.getShowManager().showMessage("Sorry, error saving, try again", this);
                } else
                    App.getShowManager().showMessage("Error! Null response!", this);
            } catch (InterruptedException | ExecutionException e) {
                App.getShowManager().showMessage("Exception!" + e.getMessage(), this);
            }
        }
    }

    private void changedCustomer() {
        customer.setFirstName(firstName.getText().toString());
        customer.setLastName(lastName.getText().toString());
        customer.setPhoneNumber(phone.getText().toString());
        customer.setEmail(etEmail.getText().toString());
    }
}
