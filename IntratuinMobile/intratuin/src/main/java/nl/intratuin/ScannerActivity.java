package nl.intratuin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mirasense.scanditsdk.ScanditSDKAutoAdjustingBarcodePicker;
import com.mirasense.scanditsdk.ScanditSDKBarcodePicker;
import com.mirasense.scanditsdk.interfaces.ScanditSDKListener;

public class ScannerActivity extends AppCompatActivity implements ScanditSDKListener {

    private ScanditSDKAutoAdjustingBarcodePicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        // Setup the barcode scanner
        picker = new ScanditSDKAutoAdjustingBarcodePicker(this, "0Oq9DpP9jIjYPPYEQCHc+5juV/BzKowcmd2bTWoQYaI", ScanditSDKBarcodePicker.CAMERA_FACING_BACK);
        setContentView(picker);
        picker.getOverlayView().addListener(this);
        picker.startScanning();
    }


    @Override
    protected void onResume() {
        picker.startScanning();
        super.onResume();
    }
    @Override
    protected void onPause() {
        picker.stopScanning();
        super.onPause();
    }

    @Override
    public void didCancel() {

    }

    @Override
    public void didScanBarcode(String s, String s1) {

    }

    @Override
    public void didManualSearch(String s) {

    }
}
