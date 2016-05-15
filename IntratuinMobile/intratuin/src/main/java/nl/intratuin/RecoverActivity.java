package nl.intratuin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class {@code RecoverActivity} is used to provide logic on Recover Activity
 * Activity appear, when user choose "forgot password"
 *
 * @see AppCompatActivity
 * @see View.OnClickListener
 */
public class RecoverActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private URI recoverUri = null;

    private EditText etEmailAddress;
    private Button bRecover;
    private Button bRegister;
    private Button bBack;
    private ImageView ivIntratuin;

    private Pattern pattern;
    private Matcher matcher;

    /**
     * Provide logic when activity created. Mapping field.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);

        etEmailAddress = (EditText) findViewById(R.id.etEmailAddress);
        bRegister = (Button) findViewById(R.id.bRegister);
        bRecover = (Button) findViewById(R.id.bRecover);
        bBack = (Button) findViewById(R.id.bBack);
        ivIntratuin = (ImageView) findViewById(R.id.ivIntratuin);

        ivIntratuin.setOnClickListener(this);
        bRegister.setOnClickListener(this);
        bRecover.setOnClickListener(this);
        bBack.setOnClickListener(this);
        ivIntratuin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ivIntratuin.performClick();
                }
            }
        });


    }

    /**
     * Trying to recover password when entering email by user or register
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRegister:
                Intent registerIntent = new Intent(this, RegisterActivity.class);
                startActivity(registerIntent);
                break;

            case R.id.bRecover:
                pattern = Pattern.compile(LoginActivity.EMAIL_PATTERN);
                matcher = pattern.matcher(etEmailAddress.getText().toString());
                if (!matcher.matches()) {
                    if (etEmailAddress.getText().length() == 0)
                        etEmailAddress.setError("Email can't be blank!");
                    else etEmailAddress.setError("Wrong email format!");
                } else {
                    //TODO
                }

                break;

            case R.id.ivIntratuin:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.intratuin.nl/"));
                startActivity(browserIntent);
                break;

            case R.id.bBack:
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                break;
        }
    }
}
