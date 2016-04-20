package nl.intratuin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;
import com.mirasense.scanditsdk.ScanditSDKBarcodePicker;
import com.mirasense.scanditsdk.ScanditSDKScanSettings;
import com.mirasense.scanditsdk.interfaces.ScanditSDK;
import com.mirasense.scanditsdk.interfaces.ScanditSDKCode;
import com.mirasense.scanditsdk.interfaces.ScanditSDKOnScanListener;
import com.mirasense.scanditsdk.interfaces.ScanditSDKScanSession;

public class ScannerActivity extends AppCompatActivity implements ScanditSDKOnScanListener {

    private ScanditSDKBarcodePicker mPicker;
    private final String KEY = "0Oq9DpP9jIjYPPYEQCHc+5juV/BzKowcmd2bTWoQYaI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_scanner);

        ScanditSDKScanSettings settings = ScanditSDKScanSettings.getDefaultSettings();
        final ScanditSDK.Symbology[] symbologies = new ScanditSDK.Symbology[] {ScanditSDK.Symbology.CODE11,
                ScanditSDK.Symbology.EAN13, ScanditSDK.Symbology.UPCE, ScanditSDK.Symbology.UPC12, ScanditSDK.Symbology.QR};
        settings.enableSymbologies(symbologies);

        // Instantiate the barcode picker by using the settings defined above.
        mPicker = new ScanditSDKBarcodePicker(this, KEY, settings);

        // Set the on scan listener to receive barcode scan events.
        mPicker.addOnScanListener(this);
        setContentView(mPicker);
    }

    @Override
    protected void onResume() {
        mPicker.startScanning();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mPicker.stopScanning();
        super.onPause();
    }

    @Override
    public void didScan(ScanditSDKScanSession scanSession) {
        scanSession.stopScanning();

        for(final ScanditSDKCode code : scanSession.getNewlyDecodedCodes()) {

            Toast toast = Toast.makeText(this, "Content:" + code.getData() + " Format:" + code.getSymbologyString(), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
            finish();
        }
    }
}
