package nl.intratuin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.Random;
import java.util.Vector;

import nl.intratuin.manager.AuthManager;
import nl.intratuin.manager.RequestResponseManager;
import nl.intratuin.settings.BuildType;
import nl.intratuin.settings.Settings;

import nl.intratuin.net.WebSocket;

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
    private String access_token;

    private String barcode_format;
    private String barcode_content;

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
            barcode_format = extra.getString(ScannerActivity.FORMAT);
            barcode_content = extra.getString(ScannerActivity.CONTENT);
        }
        if (access_token != null) {
            setContentView(R.layout.activity_web);

            webView = (WebView) findViewById(R.id.webView);
            ibNfc = (ImageButton) findViewById(R.id.ibNFC);
            ibBarcode = (ImageButton) findViewById(R.id.ibBarcode);

            if (this.getString(R.string.nfc).equals("off"))
                ibNfc.setVisibility(View.INVISIBLE);
            if (this.getString(R.string.scandit).equals("off"))
                ibBarcode.setVisibility(View.INVISIBLE);
            ibNfc.setOnClickListener(this);

            webView.setWebChromeClient(new WebChromeClient());
            WebSettings webSettings = webView.getSettings();
            webSettings.setDomStorageEnabled(true);
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new MyWebViewClient());
            Handler handler = new Handler();
            webView.addJavascriptInterface(new WebSocketFactory(handler, webView), "WebSocketFactory");

            if (barcode_content == null && barcode_format == null) {
                webView.loadUrl("https://" + Settings.getHost(this) + "/#page:debtor_order");

                //show user login
                String name = "anonymous";
                try {
                    String userInfoUri = Settings.getUriConfig().getUserInfo().toString();
                    userInfoUri += "?access_token={access_token}";
                    if (userInfoUri != null) {
                        RequestResponseManager<String> managerLoader = new RequestResponseManager(this, App.getShowManager(), String.class);
                        String jsonRespond = managerLoader.loaderFromWebService(userInfoUri, access_token);
                        jsonRespond = jsonRespond.substring(1, jsonRespond.length() - 1);
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
                webView.loadUrl("https://" + Settings.getHost(this) + "/#page:debtor_order");
            }
        } else {
            App.getShowManager().showMessage("No access token found!", WebActivity.this);
            startActivity(new Intent(WebActivity.this, LoginActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibNFC:
                Intent nfcIntent = new Intent(WebActivity.this, NFCActivity.class);
                nfcIntent.putExtra(LoginActivity.ACCESS_TOKEN, access_token);
                startActivity(nfcIntent);
                break;
            case R.id.ibBarcode:
                Intent scannerIntent = new Intent(WebActivity.this, ScannerActivity.class);
                scannerIntent.putExtra(LoginActivity.ACCESS_TOKEN, access_token);
                startActivity(scannerIntent);
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
         *
         * @param view
         * @param url
         * @return
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals(Settings.getHost(WebActivity.this))) {
                // On same host, do not override; let WebView load the page
                view.loadUrl(url);
                return false;
            }
            // Otherwise, the link is not for a page on same host, launch browser
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            webView.loadUrl("javascript:localStorage.setItem('wehandcraft.accessToken', '" + access_token + "');");
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public class WebSocketFactory {
        private Vector<WebSocket> socketList = new Vector<WebSocket>();
        private Handler handler;
        /** The app view. */
        WebView appView;
        /**
         * Instantiates a new web socket factory.
         *
         * @param appView
         * the app view
         */
        public WebSocketFactory(Handler h, WebView appView) {
            this.appView = appView;
            this.handler = h;
        }

        /**
         * Show scanner.
         */
        @JavascriptInterface
        public void ShowScanner() {
            Intent scannerIntent = new Intent(WebActivity.this, ScannerActivity.class);
            scannerIntent.putExtra(LoginActivity.ACCESS_TOKEN, access_token);
            startActivity(scannerIntent);
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
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                Toast.makeText(WebActivity.this, "Device doesn't support fingerprint authentication",
                        Toast.LENGTH_LONG).show();
            else
                startActivity(new Intent(WebActivity.this, FingerprintActivity.class));
        }

        @JavascriptInterface
        public Vector<WebSocket> getSocketList() {
            return socketList;
        }
        @JavascriptInterface
        public WebSocket getInstance(String url) {
        // use Draft76 by default
            return getInstance(url, WebSocket.Draft.DRAFT76);
        }
        @JavascriptInterface
        public WebSocket getInstance(String url, WebSocket.Draft draft) {
            WebSocket socket = null;
            Thread th = null;
            try {
                socket = new WebSocket(handler, appView, new URI(url), draft, getRandonUniqueId());
                socketList.add(socket);
                th = socket.connect();
                return socket;
            } catch (Exception e) {
//Log.v("websocket", e.toString());
                if(th != null) {
                    th.interrupt();
                }
            }
            return null;
        }
        /**
         * Generates random unique ids for WebSocket instances
         *
         * @return String
         */
        private String getRandonUniqueId() {
            return "WEBSOCKET." + new Random().nextInt(100);
        }
    }
}