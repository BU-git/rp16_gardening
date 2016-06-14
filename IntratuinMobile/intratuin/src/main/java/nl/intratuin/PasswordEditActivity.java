package nl.intratuin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.intratuin.dto.Customer;
import nl.intratuin.dto.DTOEmail;
import nl.intratuin.dto.DTOPassword;
import nl.intratuin.net.RequestResponse;

public class PasswordEditActivity extends ToolBarActivity implements View.OnClickListener {

    private EditText currentPassword;
    private TextView tvCurrentPassword;
    private TextView tvNewPassword;
    private EditText newPassword;
    private TextView tvRepeatNewPassword;
    private EditText repeatNewPassword;
    private Button savePassword;

    private Customer customer;
    private String customerPassword;

    private Pattern pattern;
    private Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.edit_password);
        super.onCreate(savedInstanceState);

        final Bundle extra = getIntent().getExtras();
        if (extra != null) {
            customer = extra.getParcelable(ToolBarActivity.CUSTOMER);
            customerPassword = customer.getPassword();
        }

        initComponents();
    }

    private void initComponents() {
        currentPassword = (EditText) findViewById(R.id.currentPassword);
        tvCurrentPassword = (TextView) findViewById(R.id.tvCurrentPassword);
        newPassword = (EditText) findViewById(R.id.newPassword);
        newPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pattern = Pattern.compile(LoginActivity.PASSWORD_PATTERN);
                matcher = pattern.matcher(newPassword.getText().toString());
                if (!hasFocus && !matcher.matches()) {
                    showPassError();
                }
            }
        });

        tvNewPassword = (TextView) findViewById(R.id.tvNewPassword);
        repeatNewPassword = (EditText) findViewById(R.id.repeatNewPassword);
        tvRepeatNewPassword = (TextView) findViewById(R.id.tvRepeatNewPassword);

        savePassword = (Button) findViewById(R.id.savePassword);
        savePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (dataValidation()) {
            DTOPassword dtoPassword = new DTOPassword();
            try {
                dtoPassword.setPassword(newPassword.getText().toString());
                dtoPassword.setId(customer.getId());

                URI updateURI = new URI(BuildConfig.API_HOME + "customer/update/password");
                AsyncTask<DTOPassword, Void, Boolean> jsonUpdateRespond =
                        new RequestResponse<DTOPassword, Boolean>(updateURI, 3,
                                Boolean.class, App.getShowManager(), this).execute(dtoPassword);
                if (jsonUpdateRespond != null) {
                    if (jsonUpdateRespond.get()) {
                        Toast.makeText(this, "your password has been changed", Toast.LENGTH_LONG).show();
                        customer.setPassword(newPassword.getText().toString());
                        Intent profilePageIntent = new Intent(this, ProfileActivity.class);
                        profilePageIntent.putExtra(CUSTOMER, customer);
                        startActivity(profilePageIntent);
                    } else
                        App.getShowManager().showMessage("Sorry, error saving, try again", this);
                } else
                    App.getShowManager().showMessage("Error! Null response!", this);
            } catch (InterruptedException | ExecutionException | URISyntaxException e) {
                App.getShowManager().showMessage("Exception!" + e.getMessage(), this);
            }
        }
    }

    private void showPassError() {
        if (newPassword.getText().length() == 0)
            newPassword.setError("Password can't be blank!");
        else newPassword.setError("Password has to be 6-15 chars, at least 1 small letter, " +
                "1 cap. letter and 1 number");
    }

    private boolean dataValidation() {
        pattern = Pattern.compile(LoginActivity.PASSWORD_PATTERN);
        matcher = pattern.matcher(newPassword.getText().toString());
        if (!matcher.matches()) {
            showPassError();
            return false;
        }

        if (!newPassword.getText().toString().equals(repeatNewPassword.getText().toString())) {
            repeatNewPassword.setError("Your new passwords are mismatches");
            return false;
        }

        if ((customerPassword == null || customerPassword.isEmpty() )&& currentPassword.getText().toString().isEmpty()) {
            return true;
        }

        if (!currentPassword.getText().toString().equals(customerPassword)) {
            currentPassword.setError("wrong current password");
            return false;
        }
        return true;
    }
}
