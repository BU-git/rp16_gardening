package nl.intratuin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RecoverActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmailAddress;
    Button bRecover;
    Button bRegister;
    ImageView ivIntratuin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);

        etEmailAddress = (EditText)findViewById(R.id.etEmailAddress);
        bRegister = (Button) findViewById(R.id.bRegister);
        bRecover = (Button) findViewById(R.id.bRecover);
        ivIntratuin = (ImageView) findViewById(R.id.ivIntratuin);

        ivIntratuin.setOnClickListener(this);
        bRegister.setOnClickListener(this);
        bRecover.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRegister:
                Intent registerIntent = new Intent(this, RegisterActivity.class);
                startActivity(registerIntent);
                break;

            case R.id.bRecover:

                break;

            case R.id.ivIntratuin:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.intratuin.nl/"));
                startActivity(browserIntent);
                break;
        }
    }
}
