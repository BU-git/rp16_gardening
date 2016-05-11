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

/**
 * The class {@code ScannerActivity} is used to provide logic on Scanner Activity
 * Scan different barcodes using {@code ScanditSDKOnScanListener}
 *
 * @see AppCompatActivity
 * @see ScanditSDKOnScanListener
 */
public class ScannerActivity extends AppCompatActivity implements ScanditSDKOnScanListener {

    private ScanditSDKBarcodePicker mPicker;
    private final String KEY = "0Oq9DpP9jIjYPPYEQCHc+5juV/BzKowcmd2bTWoQYaI";

    /**
     * Provide logic when activity created. Mapping field, setting barcode scanner.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        ScanditSDKScanSettings settings = ScanditSDKScanSettings.getDefaultSettings();
        final ScanditSDK.Symbology[] symbologies = new ScanditSDK.Symbology[]{ScanditSDK.Symbology.CODE11,
                ScanditSDK.Symbology.EAN13, ScanditSDK.Symbology.UPCE, ScanditSDK.Symbology.UPC12, ScanditSDK.Symbology.QR};
        settings.enableSymbologies(symbologies);

        // Instantiate the barcode picker by using the settings defined above.
        mPicker = new ScanditSDKBarcodePicker(this, KEY, settings);

        // Set the on scan listener to receive barcode scan events.
        mPicker.addOnScanListener(this);
        setContentView(mPicker);
    }

    /**
     * Resume scanning barcode after pause
     */
    @Override
    protected void onResume() {
        mPicker.startScanning();
        super.onResume();
    }

    /**
     * Pause scanning barcode
     */
    @Override
    protected void onPause() {
        mPicker.stopScanning();
        super.onPause();
    }

    /**
     * Scan the barcode with the internal camera
     *
     * @param scanSession
     */
    @Override
    public void didScan(ScanditSDKScanSession scanSession) {
        scanSession.stopScanning();

        for (final ScanditSDKCode code : scanSession.getNewlyDecodedCodes()) {

            Toast toast = Toast.makeText(this, "Content:" + code.getData() + " Format:" + code.getSymbologyString(), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
            finish();
        }
    }
}
