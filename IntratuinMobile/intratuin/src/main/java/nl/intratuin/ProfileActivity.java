package nl.intratuin;

import android.content.DialogInterface;
import android.graphics.Color;
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
import nl.intratuin.settings.ToolBarActivity;

public class ProfileActivity extends ToolBarActivity implements View.OnClickListener {
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
    private String[] editItems = {"Personal information", "Email", "Password"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
        super.onCreate(savedInstanceState);

        init();

        final Bundle extra = getIntent().getExtras();
        if (extra != null) {
            customer = extra.getParcelable(ToolBarActivity.CUSTOMER);
        }
        try {
            fillActivity(customer);
        } catch (ParseException ex) {
            Log.e("Parse Error!!!! ", ex.getMessage());
        }
        bEdit.setOnClickListener(this);
        bFingerprint.setOnClickListener(this);
    }

    private void init() {
        layout = (LinearLayout) findViewById(R.id.layout);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        tvCustomerName = (TextView) findViewById(R.id.tvCustomerName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        customerName = (TextView) findViewById(R.id.name);
        customerEmail = (TextView) findViewById(R.id.email);
        gender = (TextView) findViewById(R.id.gender);

        bEdit = (Button) findViewById(R.id.bEdit);
        bFingerprint = (Button) findViewById(R.id.bFingerprint);
    }

    private void fillActivity(Customer customer) throws ParseException {
        String fullName = customer.getLastName() + " " + customer.getFirstName();
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
        if (customerPhoneNumber != null) {
            TextView phoneNumber = new TextView(this);
            phoneNumber.setPadding(0, 20, 0, 0);
            phoneNumber.setTextSize(15);
            phoneNumber.setTextColor(Color.parseColor("#BFD83F"));
            phoneNumber.setText("Phone number:  " + customerPhoneNumber);
            layout.addView(phoneNumber);
        }

        String customerAddress = "";
        String houseNumber = customer.getHouseNumber();
        if (houseNumber != null)
            customerAddress += houseNumber + "  ";

        String streetName = customer.getStreetName();
        if (streetName != null)
            customerAddress += streetName + "  ";

        String city = customer.getCity();
        if (city != null)
            customerAddress += city + "  ";

        String postalCode = customer.getPostalCode();
        if (postalCode != null)
            customerAddress += postalCode + "  ";

        if (!customerAddress.equals("")) {
            TextView address = new TextView(this);
            address.setPadding(0, 20, 0, 0);
            address.setTextSize(15);
            address.setTextColor(Color.parseColor("#BFD83F"));
            address.setText("Address:  " + customerAddress);
            layout.addView(address);
        }
    }

    private void initiatePopupWindow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(editItems, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int position) {
                String item = editItems[position];
                Toast.makeText(ProfileActivity.this, item, Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bEdit:
                initiatePopupWindow();
                break;
            case R.id.bFingerprint:
                Toast.makeText(ProfileActivity.this, "register fingerprint", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
