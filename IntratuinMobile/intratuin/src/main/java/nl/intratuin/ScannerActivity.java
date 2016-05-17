package nl.intratuin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mirasense.scanditsdk.ScanditSDKBarcodePicker;
import com.mirasense.scanditsdk.ScanditSDKScanSettings;
import com.mirasense.scanditsdk.interfaces.ScanditSDK;
import com.mirasense.scanditsdk.interfaces.ScanditSDKCode;
import com.mirasense.scanditsdk.interfaces.ScanditSDKOnScanListener;
import com.mirasense.scanditsdk.interfaces.ScanditSDKScanSession;

import org.json.JSONException;
import org.json.JSONObject;

import nl.intratuin.dto.Product;
import nl.intratuin.manager.RequestResponseManager;
import nl.intratuin.settings.Mainscreen;
import nl.intratuin.settings.Settings;

/**
 * The class {@code ScannerActivity} is used to provide logic on Scanner Activity
 * Scan different barcodes using {@code ScanditSDKOnScanListener}
 *
 * @see AppCompatActivity
 * @see ScanditSDKOnScanListener
 */
public class ScannerActivity extends AppCompatActivity implements ScanditSDKOnScanListener {

    private ScanditSDKBarcodePicker mPicker;
    private final String KEY = "QIGbqG4vLkqJN+EXOqWUxvS0V3akzdlwjwbUTueP8C4";

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
            if (Settings.getMainscreen(ScannerActivity.this) == Mainscreen.SEARCH) {
                Product productByBarcode = new Product();
//                String uri = BuildConfig.API_HOME + "product/barcode/{code}";
                String uri = BuildConfig.API_HOST + "product/barcode/{code}";
                RequestResponseManager<String> managerLoader = new RequestResponseManager(this, App.getShowManager(), String.class);
                String jsonRespond = managerLoader.loaderFromWebService(uri, code.getData());
                try {
                    JSONObject response = new JSONObject(jsonRespond);
                    if (response != null && response.has("productId")) {
                        productByBarcode.setProductId(Integer.parseInt(response.getString("productId")));
                        productByBarcode.setCategoryId(Integer.parseInt(response.getString("categoryId")));
                        productByBarcode.setProductName(response.getString("productName"));
                        productByBarcode.setProductPrice(Double.parseDouble(response.getString("productPrice")));
                        productByBarcode.setProductImage(response.getString("productImage"));
                        productByBarcode.setBarcode(Long.parseLong(response.getString("barcode")));

                        Intent productPageIntent = new Intent(ScannerActivity.this, ProductDetailsPageActivity.class);
                        productPageIntent.putExtra(SearchActivity.PRODUCT, productByBarcode);
                        startActivity(productPageIntent);
                    } else {
                        String errorStr;
                        if (response == null)
                            errorStr = "Error! Null response!";
                        else
                            errorStr = "Error " + response.getString("code") + ": " + response.getString("error_description");
                        Toast.makeText(this, errorStr, Toast.LENGTH_LONG).show();
                        finish();
                    }
                } catch (JSONException e) {
                    App.getShowManager().showMessage(e.getMessage(), ScannerActivity.this);
                }
            } else {
                Toast.makeText(this, "Content:" + code.getData() + " Format:" + code.getSymbologyString(), Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
