package nl.intratuin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.intratuin.dto.Customer;
import nl.intratuin.dto.DTOEmail;
import nl.intratuin.net.RequestResponse;

public class EmailEditActivity extends ToolBarActivity implements View.OnClickListener {
    private EditText currentEmail;
    private TextView tvCurrentEmail;
    private TextView tvNewEmail;
    private EditText newEmail;
    private Button saveEmail;

    private Customer customer;
    private String customerEmail;

    private Pattern pattern;
    private Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.edit_email);
        super.onCreate(savedInstanceState);

        final Bundle extra = getIntent().getExtras();
        if (extra != null) {
            customer = extra.getParcelable(ToolBarActivity.CUSTOMER);
            customerEmail = customer.getEmail();
        }

        initComponents();
    }

    private void initComponents() {
        currentEmail = (EditText) findViewById(R.id.currentEmail);
        currentEmail.setText(customerEmail);
        newEmail = (EditText) findViewById(R.id.newEmail);
        newEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pattern = Pattern.compile(LoginActivity.EMAIL_PATTERN);
                matcher = pattern.matcher(newEmail.getText().toString());
                if (!hasFocus && !matcher.matches()) {
                    showEmailError(newEmail);
                }
            }
        });

        tvCurrentEmail = (TextView) findViewById(R.id.tvCurrentEmail);
        tvNewEmail = (TextView) findViewById(R.id.tvNewEmail);
        saveEmail = (Button) findViewById(R.id.saveEmail);

        saveEmail.setOnClickListener(this);
    }

    private void showEmailError(EditText email) {
        if (email.getText().length() == 0)
            email.setError("Email can't be blank!");
        else email.setError("Wrong email format!");
    }

    private boolean dataValidation() {
        pattern = Pattern.compile(LoginActivity.EMAIL_PATTERN);
        matcher = pattern.matcher(newEmail.getText().toString());
        boolean emailError = !matcher.matches();
        if (emailError) {
            showEmailError(newEmail);
            return false;
        }
        if (!currentEmail.getText().toString().equals(customerEmail)) {
            currentEmail.setError("wrong current email");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (dataValidation()) {
            DTOEmail dtoEmail = new DTOEmail();
            try {
                dtoEmail.setEmail(newEmail.getText().toString());
                dtoEmail.setId(customer.getId());

                URI updateURI = new URI(BuildConfig.API_HOME + "customer/update/email");

                AsyncTask<DTOEmail, Void, String> jsonUpdateRespond =
                        new RequestResponse<DTOEmail, String>(updateURI, 3,
                                String.class, App.getShowManager(), this).execute(dtoEmail);
                if (jsonUpdateRespond != null) {
                    JSONObject response = new JSONObject(jsonUpdateRespond.get());
                    if (response != null && response.has("success")) {
                        Toast.makeText(EmailEditActivity.this, response.getString("success"), Toast.LENGTH_LONG).show();
                        Intent profilePageIntent = new Intent(this, ProfileActivity.class);
                        customer.setEmail(newEmail.getText().toString());
                        profilePageIntent.putExtra(CUSTOMER, customer);
                        startActivity(profilePageIntent);
                    } else {
                        String errorStr;
                        if (response == null)
                            errorStr = "Error! Null response!";
                        else
                            errorStr = "Error " + response.getString("code") + ": " + response.getString("error") + ": " + response.getString("error_description");
                        App.getShowManager().showMessage(errorStr, this);
                    }
                }
            } catch (InterruptedException | ExecutionException | URISyntaxException | JSONException e) {
                App.getShowManager().showMessage("Exception!" + e.getMessage(), this);
            }
        }
    }
}
