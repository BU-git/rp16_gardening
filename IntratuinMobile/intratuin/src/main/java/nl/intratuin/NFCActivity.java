package nl.intratuin;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import nl.intratuin.handlers.NFCHandler;

public class NFCActivity extends AppCompatActivity {
    private NfcAdapter mNfcAdapter;
    private NFCHandler nfcHandler;

    private ImageView ivNFC;
    private TextView tvNFC;

    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_nfc);
        final Bundle extra = getIntent().getExtras();
        if(extra != null){
            accessToken=extra.getString(LoginActivity.ACCESS_TOKEN);
        }
        super.onCreate(savedInstanceState);

        ivNFC = (ImageView) findViewById(R.id.ivNFC);
        tvNFC = (TextView) findViewById(R.id.tvNFC);

        nfcHandler = new NFCHandler();
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setMessage("This device doesn't support NFC.")
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            });
            android.app.AlertDialog alert = builder.create();
            alert.show();
        } else {

            if (!mNfcAdapter.isEnabled()) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setTitle("NFC is disabled.")
                        .setMessage("go to Settings -> (Wireless & networks) ...More ->")
                        .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(dialogIntent);
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                android.app.AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    protected void onResume() {
        super.onResume();
        if(mNfcAdapter!=null)
            NFCHandler.setupForegroundDispatch(this, mNfcAdapter);
    }

    @Override
    protected void onPause() {
        if(mNfcAdapter!=null)
            NFCHandler.stopForegroundDispatch(this, mNfcAdapter);
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String credentials = nfcHandler.handleIntent(intent);
        String[] parseContent = credentials.split(":");

        Intent webPageIntent = new Intent(NFCActivity.this, WebActivity.class);
        webPageIntent.putExtra(LoginActivity.ACCESS_TOKEN, accessToken);
        webPageIntent.putExtra(ScannerActivity.FORMAT, "NFC");
        webPageIntent.putExtra(ScannerActivity.CONTENT, parseContent[2]);
        startActivity(webPageIntent);
        finish();
    }
}
