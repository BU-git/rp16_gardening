package nl.intratuin.handlers;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.concurrent.ExecutionException;

import nl.intratuin.App;
import nl.intratuin.LoginActivity;
import nl.intratuin.R;
import nl.intratuin.WebActivity;
import nl.intratuin.manager.AuthManager;
import nl.intratuin.net.RequestResponse;
import nl.intratuin.settings.BuildType;
import nl.intratuin.settings.Settings;

/**
 * Class {@code Cache customer} uses for check users credentials(include Facebook and Twitter credentials),
 * and to save user's credentials in cache.
 */
public class CacheCustomerCredentials {
    /**
     * Checking cache and start Web Activity
     * or start Facebook/Twitter cache handlers
     *
     * @param context the context
     */
    public static void cache(Context context) {
        String timeOfLoginStr=App.getAuthManager().getTime();
        if(timeOfLoginStr==null)
            return;
        long timeOfLogin = Long.parseLong(timeOfLoginStr);
        long currentTimeInMillis = System.currentTimeMillis();
        long cachingTime = Long.parseLong(context.getString(R.string.login_caching_time));
        if ((currentTimeInMillis - timeOfLogin - cachingTime) > 0) {
            context.getSharedPreferences(AuthManager.PREF_FILENAME, Context.MODE_PRIVATE)
                    .edit()
                    .clear()
                    .commit();
            return;
        }

        String username = App.getAuthManager().getAccessKeyUsername();
        String password = App.getAuthManager().getAccessKeyPassword();
        if (username != null && password != null) {
            URI uri = Settings.getUriConfig().getLogin();
            try {
                MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                map.add("grant_type", "password");
                map.add("client_id", username);
                map.add("client_secret", password);
                map.add("username", username);
                map.add("password", password);

                AsyncTask<MultiValueMap<String, String>, Void, String> jsonRespond =
                        new RequestResponse<MultiValueMap<String, String>, String>(uri, 3,
                                String.class, App.getShowManager(), context).execute(map);
                JSONObject responseJsonObject = new JSONObject(jsonRespond.get());
                if (responseJsonObject != null && responseJsonObject.getString("token_type").equals("bearer")) {
                    String accessKey = responseJsonObject.getString("access_token");
                    context.startActivity(new Intent(context, WebActivity.class).putExtra(LoginActivity.ACCESS_TOKEN, accessKey));
                    ((FragmentActivity) context).finish();
                } else {
                    String errorStr;
                    if (responseJsonObject == null)
                        errorStr = "Error! Null response!";
                    else
                        errorStr = "Error " + responseJsonObject.getString("code") + ": "
                                + responseJsonObject.getString("error")
                                + ": " + responseJsonObject.getString("error_description");

                    App.getShowManager().showMessage(errorStr, context);
                    context.getSharedPreferences(AuthManager.PREF_FILENAME, Context.MODE_PRIVATE)
                            .edit()
                            .clear()
                            .commit();
                }
            } catch (InterruptedException | ExecutionException | JSONException e) {
                Log.e("Error! ", e.getMessage());
            }
        }
    }
}
