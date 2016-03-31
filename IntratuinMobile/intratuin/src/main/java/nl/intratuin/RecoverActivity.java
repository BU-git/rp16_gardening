package nl.intratuin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecoverActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmailAddress;
    Button bRecover;
    Button bRegister;
    Button bBack;
    ImageView ivIntratuin;

    Pattern pattern;
    Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_recover);

        etEmailAddress = (EditText)findViewById(R.id.etEmailAddress);
        bRegister = (Button) findViewById(R.id.bRegister);
        bRecover = (Button) findViewById(R.id.bRecover);
        bBack = (Button) findViewById(R.id.bBack);
        ivIntratuin = (ImageView) findViewById(R.id.ivIntratuin);

        ivIntratuin.setOnClickListener(this);
        bRegister.setOnClickListener(this);
        bRecover.setOnClickListener(this);
        bBack.setOnClickListener(this);
    }

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
                if(!matcher.matches()){
                    if(etEmailAddress.getText().length()==0)
                        etEmailAddress.setError("Email can't be blank!");
                    else etEmailAddress.setError("Wrong email format!");
                }
                else{
                    
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
