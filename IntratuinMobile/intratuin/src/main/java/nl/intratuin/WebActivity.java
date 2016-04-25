package nl.intratuin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import nl.intratuin.handlers.CacheCustomerCredentials;

public class WebActivity extends AppCompatActivity {
    WebView webView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String mime = "text/html";
        String encoding = "utf-8";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        //show access token
        final Bundle extra = getIntent().getExtras();
        if (extra != null) {
            Toast.makeText(this, "TRANSFER ACCESS TOKEN: " + extra.getString(LoginActivity.ACCESS_TOKEN), Toast.LENGTH_LONG).show();
        }

        getSupportActionBar().hide();
        webView=(WebView)findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.addJavascriptInterface(new WebInterface(this), "Android");
        //webView.loadUrl("http://demo.weorder.at");
        webView.loadDataWithBaseURL(null, html, mime, encoding, null);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("demo.weorder.at")) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
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
