package nl.intratuin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import nl.intratuin.manager.AuthManager;
import nl.intratuin.manager.RequestResponseManager;
import nl.intratuin.settings.BuildType;
import nl.intratuin.settings.Settings;

/**
 * The class {@code WebActivity} is used to provide logic on Web Activity
 * Alternative Web activity
 *
 * @see AppCompatActivity
 */
public class WebActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView webView;
    private ImageButton ibNfc;
    private ImageButton ibBarcode;
    private ImageButton ibUnknown;
    private String access_token;

    /**
     * Provide logic when activity created. Mapping field, creating HTML page, loading data to page.
     *
     * @param savedInstanceState
     */
    //break this method for few less
    //code duplication(show user login)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle extra = getIntent().getExtras();
        if (extra != null) {
            access_token = extra.getString(LoginActivity.ACCESS_TOKEN);
        }
        if (access_token != null) {
            setContentView(R.layout.activity_web);

            webView = (WebView) findViewById(R.id.webView);
            ibNfc = (ImageButton) findViewById(R.id.ibNFC);
            ibBarcode = (ImageButton) findViewById(R.id.ibBarcode);
            ibUnknown = (ImageButton) findViewById(R.id.ibUnknown);

            if(this.getString(R.string.nfc).equals("off"))
                ibNfc.setVisibility(View.INVISIBLE);
            if(this.getString(R.string.scandit).equals("off"))
                ibBarcode.setVisibility(View.INVISIBLE);

            webView.setWebChromeClient(new WebChromeClient());
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new MyWebViewClient());
            webView.addJavascriptInterface(new WebInterface(this), "Android");

            if (Settings.getBuildType(WebActivity.this) == BuildType.DEPLOYED || Settings.getBuildType(WebActivity.this) == BuildType.LOCAL) {
                //String mime = "text/html";
                //String encoding = "utf-8";
                //webView.loadDataWithBaseURL(null, "file:///android_asset/xyz.html, mime, encoding, null);
                webView.loadUrl("file:///android_asset/pages/dummy.html");
            } else {
                webView.loadUrl("https://" + Settings.getHost(WebActivity.this) + "/?" + access_token + "#page:debtor_order");
            }

            //show user login
            String name = "anonymous";
            try {
                String userInfoUri = Settings.getUriConfig().getUserInfo().toString();
                userInfoUri += "?access_token={access_token}";
                if (userInfoUri != null) {
                    RequestResponseManager<String> managerLoader = new RequestResponseManager(this, App.getShowManager(), String.class);
                    String jsonRespond = managerLoader.loaderFromWebService(userInfoUri, access_token);
                    jsonRespond=jsonRespond.substring(1,jsonRespond.length()-1);
                    JSONObject response = new JSONObject(jsonRespond);
                    if (response != null && response.has("id")) {
                        if (response.has("name") && response.getString("name").length() > 0)
                            name = response.getString("name");
                        else
                            name = response.getString("email");
                    } else {
                        String errorStr;
                        if (response == null)
                            errorStr = "Error! Null response!";
                        else
                            errorStr = "Error" + response.getString("code") + ": " + response.getString("error") + ": " + response.getString("error_description");
                        App.getShowManager().showMessage(errorStr, WebActivity.this);
                        startActivity(new Intent(WebActivity.this, LoginActivity.class));
                    }
                }
            } catch (JSONException e) {
                App.getShowManager().showMessage("Can't get user info!", WebActivity.this);
                startActivity(new Intent(WebActivity.this, LoginActivity.class));
            }

            Toast.makeText(this, "Logged as " + name, Toast.LENGTH_LONG).show();
        } else {
            App.getShowManager().showMessage("No access token found!", WebActivity.this);
            startActivity(new Intent(WebActivity.this, LoginActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibNFC:
                break;
            case R.id.ibBarcode:
                startActivity(new Intent(WebActivity.this, ScannerActivity.class));
                break;
            case R.id.ibUnknown:
                break;
        }
    }

    /**
     * Class {@code MyWebViewClient} is Configured implementation of WebViewClient
     *
     * @see WebViewClient
     */
    private class MyWebViewClient extends WebViewClient {

        /**
         * Overriding URL if needed
         * @param view
         * @param url
         * @return
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals(Settings.getHost(WebActivity.this))) {
                // On same host, do not override; let WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on same host, launch browser
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

    /**
     * Class {@code WebInterface} provide web interface
     */
    private class WebInterface {
        private Context cont;

        /**
         * Instantiates a new Web interface.
         *
         * @param c the c
         */
        WebInterface(Context c) {
            cont = c;
        }

        /**
         * Show scanner.
         */
        @JavascriptInterface
        public void ShowScanner() {
            startActivity(new Intent(WebActivity.this, ScannerActivity.class));
        }

        /**
         * Logout.
         */
        @JavascriptInterface
        public void Logout() {
            WebActivity.this.getSharedPreferences(AuthManager.PREF_FILENAME, Context.MODE_PRIVATE)
                    .edit()
                    .clear()
                    .commit();
            Intent loginIntent = new Intent(WebActivity.this, LoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
        }

        /**
         * Logout.
         */
        @JavascriptInterface
        public void Fingerprint() {
            //TODO: make dummy fingerprint scanning
        }
    }
}
