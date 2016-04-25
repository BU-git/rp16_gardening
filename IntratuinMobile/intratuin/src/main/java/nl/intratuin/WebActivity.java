package nl.intratuin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import nl.intratuin.handlers.AuthManager;
import nl.intratuin.handlers.CacheCustomerCredentials;
import nl.intratuin.handlers.ErrorFragment;
import nl.intratuin.net.RequestResponse;
import nl.intratuin.net.RequestResponseGET;
import nl.intratuin.net.UriConstructor;
import nl.intratuin.settings.Mainscreen;
import nl.intratuin.settings.Settings;

public class WebActivity extends AppCompatActivity {
    WebView webView;
    String access_token;

    private String html="<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\"></meta>\n" +
            "</head>\n" +
            "<body>\n" +
            "    Dummy page.\n" +
            "    <input type=\"button\" value=\"Scanner\" onClick=\"showAndroidScanner()\" />\n" +
            "\n" +
            "    <script type=\"text/javascript\">\n" +
            "        function showAndroidScanner() {\n" +
            "            Android.ShowScanner();\n" +
            "        }\n" +
            "    </script>\n" +
            "</body>\n" +
            "</html>";

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle extra = getIntent().getExtras();
        accessToken=extra.getString(LoginActivity.ACCESS_TOKEN);

        String mime = "text/html";
        String encoding = "utf-8";

        setContentView(R.layout.activity_web);

        getSupportActionBar().hide();
        webView=(WebView)findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.addJavascriptInterface(new WebInterface(this), "Android");
        //webView.loadUrl("http://demo.weorder.at");
        webView.loadDataWithBaseURL(null, html, mime, encoding, null);

        //show user login
        String name="anonymous";
        try {
            String param="?access_token="+accessToken;
            URI userInfoUri = new UriConstructor(WebActivity.this, getSupportFragmentManager()).makeURI("userInfo",param);
            if(userInfoUri!=null) {
                AsyncTask<Void, Void, String> jsonRespond =
                        new RequestResponseGET<Void, String>(userInfoUri.toString(), 3,
                                String.class, getSupportFragmentManager(), this).execute();
                if (jsonRespond == null) {
                    ErrorFragment ef = ErrorFragment.newError("Error! No response.");
                    ef.show(getSupportFragmentManager(), "Intratuin");
                }
                JSONObject response = new JSONObject(jsonRespond.get());
                if (response != null && response.has("client_id")) {
                    if (response.has("name"))
                        name = response.getString("name");
                    else name = response.getString("client_id");
                } else {
                    String errorStr;
                    if (response == null)
                        errorStr = "Error! Null response!";
                    else errorStr = "Error!";
                    ErrorFragment ef = ErrorFragment.newError(errorStr);
                    ef.show(getSupportFragmentManager(), "Intratuin");
                }
            }
        } catch(InterruptedException | ExecutionException | JSONException e){
            ErrorFragment ef = ErrorFragment.newError("Can't get user info!");
            ef.show(getSupportFragmentManager(), "Intratuin");
        }

        if (extra != null) {
            Toast.makeText(this, "Logged as " + name, Toast.LENGTH_LONG).show();
        }

    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle extra = getIntent().getExtras();
        access_token=extra.getString(LoginActivity.ACCESS_TOKEN);

        String mime = "text/html";
        String encoding = "utf-8";

        setContentView(R.layout.activity_web);

        getSupportActionBar().hide();
        webView=(WebView)findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.addJavascriptInterface(new WebInterface(this), "Android");
        //webView.loadUrl("http://demo.weorder.at");
        webView.loadDataWithBaseURL(null, html, mime, encoding, null);

        //show user login
        String name="anonymous";
        try {
            String token=access_token;
//            URI userInfoUri = new UriConstructor(WebActivity.this, getSupportFragmentManager()).makeURI("userInfo/{access_token}",param);
            String userInfoUri = BuildConfig.API_HOME + "customer/info/{access_token}";
            if(userInfoUri!=null) {
                AsyncTask<String, Void, String> jsonRespond =
                        new RequestResponseGET<String, String>(userInfoUri.toString(), 3,
                                String.class, getSupportFragmentManager(), this).execute(access_token);
                if (jsonRespond == null) {
                    ErrorFragment ef = ErrorFragment.newError("Error! No response.");
                    ef.show(getSupportFragmentManager(), "Intratuin");
                }
                JSONObject response = new JSONObject(jsonRespond.get());
                if (response != null && response.has("client_id")) {
                    if (response.has("name"))
                        name = response.getString("name");
                    else name = response.getString("client_id");
                } else {
                    String errorStr;
                    if (response == null)
                        errorStr = "Error! Null response!";
                    else errorStr = "Error!";
                    ErrorFragment ef = ErrorFragment.newError(errorStr);
                    ef.show(getSupportFragmentManager(), "Intratuin");
                }
            }
        } catch(InterruptedException | ExecutionException | JSONException e){
            ErrorFragment ef = ErrorFragment.newError("Can't get user info!");
            ef.show(getSupportFragmentManager(), "Intratuin");
        }

        if (extra != null) {
            Toast.makeText(this, "Logged as " + name, Toast.LENGTH_LONG).show();
        }

    }

    private class MyWebViewClient extends WebViewClient {
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

    private class WebInterface{
        Context cont;

        WebInterface(Context c){
            cont=c;
        }

        @JavascriptInterface
        public void ShowScanner(){
            startActivity(new Intent(WebActivity.this, ScannerActivity.class));
        }

    }
}
