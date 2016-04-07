package nl.intratuin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.scandit.barcodepicker.BarcodePicker;
import com.scandit.barcodepicker.OnScanListener;
import com.scandit.barcodepicker.ScanSession;
import com.scandit.barcodepicker.ScanSettings;
import com.scandit.barcodepicker.ScanditLicense;
import com.scandit.recognition.Barcode;

public class ScannerActivity extends AppCompatActivity implements OnScanListener {

    private BarcodePicker mPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_scanner);

        // Set your app key
        ScanditLicense.setAppKey("0Oq9DpP9jIjYPPYEQCHc+5juV/BzKowcmd2bTWoQYaI");
        ScanSettings settings = ScanSettings.create();
        settings.setSymbologyEnabled(Barcode.SYMBOLOGY_EAN13, true);
        settings.setSymbologyEnabled(Barcode.SYMBOLOGY_UPCA, true);

        // Instantiate the barcode picker by using the settings defined above.
        BarcodePicker picker = new BarcodePicker(this, settings);

        // Set the on scan listener to receive barcode scan events.
        picker.setOnScanListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {

        }
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
    public void didScan(ScanSession scanSession) {

    }
}
