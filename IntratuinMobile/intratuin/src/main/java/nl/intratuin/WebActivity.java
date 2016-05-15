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

import nl.intratuin.manager.AuthManager;
import nl.intratuin.manager.RequestResponseManager;
import nl.intratuin.net.UriConstructor;
import nl.intratuin.settings.BuildType;
import nl.intratuin.settings.Settings;

/**
 * The class {@code WebActivity} is used to provide logic on Web Activity
 * Alternative Web activity
 *
 * @see AppCompatActivity
 */
public class WebActivity extends AppCompatActivity {
    private WebView webView;
    private String access_token;
    private String dummyPage = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\"></meta>\n" +
            "</head>\n" +
            "<body>\n" +
            "    Dummy page.\n" +
            "    <input type=\"button\" value=\"Scanner\" onClick=\"showAndroidScanner()\" />\n" +
            "\n" + "<br/>" +
            "    <script type=\"text/javascript\">\n" +
            "        function showAndroidScanner() {\n" +
            "            Android.ShowScanner();\n" +
            "        }\n" +
            "    </script>\n" +
            "   <input type=\"button\" value=\"Logout\" onClick=\"logout()\" />\n" +
            "\n" + "<br/>" +
            "    <script type=\"text/javascript\">\n" +
            "        function logout() {\n" +
            "            Android.Logout();\n" +
            "        }\n" +
            "    </script>\n" +
            "</body>\n" +
            "</html>";

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
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new MyWebViewClient());
            webView.addJavascriptInterface(new WebInterface(this), "Android");

            if (Settings.getBuildType(WebActivity.this) == BuildType.DEPLOYED || Settings.getBuildType(WebActivity.this) == BuildType.LOCAL) {
                String mime = "text/html";
                String encoding = "utf-8";
                webView.loadDataWithBaseURL(null, dummyPage, mime, encoding, null);
            } else {
                webView.loadUrl("https://" + Settings.getHost(WebActivity.this) + "/?" + access_token + "#page:debtor_order");
            }

            //show user login
            String name = "anonymous";
            try {
                String userInfoUri = new UriConstructor(WebActivity.this).makeURI("userInfo").toString();
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
    }
}
