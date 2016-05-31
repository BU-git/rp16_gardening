package nl.intratuin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

import nl.intratuin.dto.Customer;

public class ProfileActivity extends ToolBarActivity implements View.OnClickListener {
    public static String credentials;
    private LinearLayout layout;
    private ImageView ivProfile;
    private TextView tvCustomerName;
    private TextView customerName;
    private TextView customerEmail;
    private TextView gender;
    private TextView tvEmail;
    private Button bEdit;
    private Button bFingerprint;

    private Customer customer;
    private AlertDialog.Builder builder;
    private String[] editItems = {"Personal information", "Email", "Password"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
        super.onCreate(savedInstanceState);

        initComponents();
        initiatePopupWindow();

        final Bundle extra = getIntent().getExtras();
        if (extra != null) {
            customer = extra.getParcelable(ToolBarActivity.CUSTOMER);
            credentials = customer.getEmail() + ":" + customer.getPassword();
        }
        try {
            fillActivity(customer);
        } catch (ParseException ex) {
            Log.e("Parse Error! ", ex.getMessage());
        }
    }

    private void initComponents() {
        layout = (LinearLayout) findViewById(R.id.layout);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        tvCustomerName = (TextView) findViewById(R.id.tvCustomerName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        customerName = (TextView) findViewById(R.id.name);
        customerEmail = (TextView) findViewById(R.id.email);
        gender = (TextView) findViewById(R.id.gender);

        bEdit = (Button) findViewById(R.id.bEdit);
        bFingerprint = (Button) findViewById(R.id.bFingerprint);

        bEdit.setOnClickListener(this);
        bFingerprint.setOnClickListener(this);
    }

    private void fillActivity(Customer customer) throws ParseException {
        String fullName = customer.getFirstName() + " " + customer.getLastName();
        String email = customer.getEmail();

        tvCustomerName.setText(fullName);
        tvEmail.setText("email: " + email);

        customerName.setText("Name:  " + fullName);
        customerEmail.setText("Email:  " + email);

        java.sql.Date birthdayFromAPI = customer.getBirthday();
        if (birthdayFromAPI != null) {
            TextView birthday = new TextView(this);
            birthday.setPadding(0, 20, 0, 0);
            birthday.setTextSize(15);
            birthday.setTextColor(Color.parseColor("#BFD83F"));
            java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("dd.MM.yyyy");
            birthday.setText("Birthday:  " + simpleDateFormat.format(birthdayFromAPI));
            layout.addView(birthday);
        }

        String customerGender = customer.getGender() == 0 ? "female" : "male";
        gender.setText("Gender:  " + customerGender);

        String customerPhoneNumber = customer.getPhoneNumber();
        if (customerPhoneNumber != null && !customerPhoneNumber.isEmpty()) {
            TextView phoneNumber = new TextView(this);
            phoneNumber.setPadding(0, 20, 0, 0);
            phoneNumber.setTextSize(15);
            phoneNumber.setTextColor(Color.parseColor("#BFD83F"));
            phoneNumber.setText("Phone number:  " + customerPhoneNumber);
            layout.addView(phoneNumber);
        }

        String customerAddress = "";
        String houseNumber = customer.getHouseNumber();
        if (houseNumber != null && !houseNumber.isEmpty())
            customerAddress += houseNumber + "  ";

        String streetName = customer.getStreetName();
        if (streetName != null && !streetName.isEmpty())
            customerAddress += streetName + "  ";

        String city = customer.getCity();
        if (city != null && !city.isEmpty())
            customerAddress += city + "  ";

        String postalCode = customer.getPostalCode();
        if (postalCode != null && !postalCode.isEmpty())
            customerAddress += postalCode + "  ";

        if (!customerAddress.isEmpty()) {
            TextView address = new TextView(this);
            address.setPadding(0, 20, 0, 0);
            address.setTextSize(15);
            address.setTextColor(Color.parseColor("#BFD83F"));
            address.setText("Address:  " + customerAddress);
            layout.addView(address);
        }
    }

    private void initiatePopupWindow() {
        builder = new AlertDialog.Builder(this);
        builder.setItems(editItems, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int position) {
                switch (position) {
                    case 0:
                        Intent personalIntent = new Intent(ProfileActivity.this, PersonalInfoActivity.class);
                        personalIntent.putExtra(CUSTOMER, customer);
                        startActivity(personalIntent);
                        break;
                    case 1:
                        Intent emailEditIntent = new Intent(ProfileActivity.this, EmailEditActivity.class);
                        emailEditIntent.putExtra(CUSTOMER, customer);
                        startActivity(emailEditIntent);
                        break;
                    case 2:
                        Intent passwordEditIntent = new Intent(ProfileActivity.this, PasswordEditActivity.class);
                        passwordEditIntent.putExtra(CUSTOMER, customer);
                        startActivity(passwordEditIntent);
                        break;
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bEdit:
                //show PopupWindow();
                builder.create().show();
                break;
            case R.id.bFingerprint:
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                    Toast.makeText(this, "Device doesn't support fingerprint authentication",
                            Toast.LENGTH_LONG).show();
                else
                    startActivity(new Intent(this, FingerprintActivity.class));
                break;
        }
    }
}
