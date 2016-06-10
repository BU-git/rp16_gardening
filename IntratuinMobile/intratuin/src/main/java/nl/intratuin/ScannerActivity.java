package nl.intratuin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
    private final String KEY = "4R+u6NGkE0KUkuaFA2MgbdDI+OTC097HIPuiHNq+lbg";

    public static final String FORMAT = "format";
    public static final String CONTENT = "content";

    private String accessToken;

    /**
     * Provide logic when activity created. Mapping field, setting barcode scanner.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        if(permission == PackageManager.PERMISSION_GRANTED) {
            scannerInitialize();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 1);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            scannerInitialize();
        } else {
            Toast.makeText(this, "No camera permission!", Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void scannerInitialize(){
        final Bundle extra = getIntent().getExtras();
        if(extra != null){
            accessToken=extra.getString(LoginActivity.ACCESS_TOKEN);
        }
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
        if(mPicker!=null)
            mPicker.startScanning();
        super.onResume();
    }

    /**
     * Pause scanning barcode
     */
    @Override
    protected void onPause() {
        if(mPicker!=null)
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

                String uri = Settings.getUriConfig().getBarcode().toString();
                uri += "/{code}";
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
                Intent webPageIntent = new Intent(ScannerActivity.this, WebActivity.class);
                webPageIntent.putExtra(LoginActivity.ACCESS_TOKEN, accessToken);
                webPageIntent.putExtra(ScannerActivity.FORMAT, code.getSymbologyString());
                webPageIntent.putExtra(ScannerActivity.CONTENT, code.getData());
                startActivity(webPageIntent);
                finish();
            }
        }
    }
}
