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

import org.json.JSONException;
import org.json.JSONObject;

import nl.intratuin.handlers.ErrorFragment;
import nl.intratuin.handlers.RequestResponseManager;
import nl.intratuin.net.UriConstructor;
import nl.intratuin.settings.BuildType;
import nl.intratuin.settings.Settings;

public class WebActivity extends AppCompatActivity {
    WebView webView;
    String access_token;
    private String dummyPage="<!DOCTYPE html>\n" +
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
        super.onCreate(savedInstanceState);
        final Bundle extra = getIntent().getExtras();
        if (extra != null) {
            access_token=extra.getString(LoginActivity.ACCESS_TOKEN);
        }
        if(access_token!=null) {
            setContentView(R.layout.activity_web);

            getSupportActionBar().hide();
            webView = (WebView) findViewById(R.id.webView);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new MyWebViewClient());
            webView.addJavascriptInterface(new WebInterface(this), "Android");

            if(Settings.getBuildType(WebActivity.this)== BuildType.DEPLOYED || Settings.getBuildType(WebActivity.this)== BuildType.LOCAL){
                String mime = "text/html";
                String encoding = "utf-8";
                webView.loadDataWithBaseURL(null, dummyPage, mime, encoding, null);
            } else {
                webView.loadUrl("http://"+Settings.getHost(WebActivity.this)+"?"+access_token);
            }

            //show user login
            String name = "anonymous";
            try {
                String userInfoUri = new UriConstructor(WebActivity.this, getSupportFragmentManager()).makeURI("userInfo").toString();
                userInfoUri+="?access_token={access_token}";
                if (userInfoUri != null) {
                    RequestResponseManager<String> managerLoader = new RequestResponseManager(this, String.class);
                    String jsonRespond = managerLoader.loaderFromWebService(userInfoUri, access_token);
                    JSONObject response = new JSONObject(jsonRespond);
                    if (response != null && response.has("user_id")) {
                        //Toast.makeText(WebActivity.this, "Customer: " + response.getString("name"), Toast.LENGTH_LONG).show();
                        if (response.has("name") && response.getString("name").length()>0)
                            name = response.getString("name");
                        else name = response.getString("client_id");//name = response.getString("client_id");
                    } else {
                        String errorStr;
                        if (response == null)
                            errorStr = "Error! Null response!";
                        else errorStr = "Error"+response.getString("code")+": "+response.getString("error")+": "+response.getString("error_description");
                        ErrorFragment ef = ErrorFragment.newError(errorStr);
                        ef.show(getSupportFragmentManager(), "Intratuin");
                        startActivity(new Intent(WebActivity.this, LoginActivity.class));
                    }
                }
            } catch (JSONException e) {
                ErrorFragment ef = ErrorFragment.newError("Can't get user info!");
                ef.show(getSupportFragmentManager(), "Intratuin");
                startActivity(new Intent(WebActivity.this, LoginActivity.class));
            }

            Toast.makeText(this, "Logged as " + name, Toast.LENGTH_LONG).show();
        } else {
            ErrorFragment ef = ErrorFragment.newError("No access token found!");
            ef.show(getSupportFragmentManager(), "Intratuin");
            startActivity(new Intent(WebActivity.this, LoginActivity.class));
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
