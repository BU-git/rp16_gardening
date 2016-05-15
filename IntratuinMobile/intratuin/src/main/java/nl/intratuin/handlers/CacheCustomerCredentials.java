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
import nl.intratuin.manager.AuthManager;
import nl.intratuin.SearchActivity;
import nl.intratuin.WebActivity;
import nl.intratuin.net.RequestResponse;
import nl.intratuin.net.UriConstructor;
import nl.intratuin.settings.BuildType;
import nl.intratuin.settings.Mainscreen;
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
    //why cache -> facebookCache -> twitterCache?
    public static void cache(Context context) {
        String username = App.getAuthManager().getAccessKeyUsername();
        String password = App.getAuthManager().getAccessKeyPassword();
        if (username != null && password != null) {
//            URI uri = new UriConstructor(context).makeURI("login");
//            try {
//                MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//                map.add("grant_type", "password");
//                map.add("client_id", username);
//                map.add("client_secret", password);
//                map.add("username", username);
//                map.add("password", password);
//
//                AsyncTask<MultiValueMap<String, String>, Void, String> jsonRespond =
//                        new RequestResponse<MultiValueMap<String, String>, String>(uri, 3,
//                                String.class, App.getShowManager(), context).execute(map);
//                JSONObject responseJsonObject = new JSONObject(jsonRespond.get());
//                if (responseJsonObject != null && responseJsonObject.getString("token_type").equals("bearer")) {
//                    String accessKey = responseJsonObject.getString("access_token");
//                    if (Settings.getMainscreen(context) == Mainscreen.WEB)
//                        context.startActivity(new Intent(context, WebActivity.class).putExtra(LoginActivity.ACCESS_TOKEN, accessKey));
//                    else
//                        context.startActivity(new Intent(context, SearchActivity.class).putExtra(LoginActivity.ACCESS_TOKEN, accessKey));
//                    ((FragmentActivity) context).finish();
//                } else {
//                    String errorStr;
//                    if (responseJsonObject == null)
//                        errorStr = "Error! Null response!";
//                    else
//                        errorStr = "Error " + responseJsonObject.getString("code") + ": "
//                                + responseJsonObject.getString("error")
//                                + ": " + responseJsonObject.getString("error_description");
//
//                    App.getShowManager().showMessage(errorStr, context);
                    context.getSharedPreferences(AuthManager.PREF_FILENAME, Context.MODE_PRIVATE)
                            .edit()
                            .clear()
                            .commit();
                }
//            } catch (InterruptedException | ExecutionException | JSONException e) {
//                Log.e("Error!!!!!! ", e.getMessage());
//            }
//        } else {
//            if (Settings.getBuildType(context) == BuildType.LOCAL || Settings.getBuildType(context) == BuildType.DEPLOYED)
//                facebookCache(context);
//        }
    }

    /**
     * Facebook check handler
     *
     * @param context
     */
    private static void facebookCache(Context context) {
        String accessToken = App.getAuthManager().getAccessTokenFacebook();
        if (accessToken != null) {
            URI uri = new UriConstructor(context).makeURI("facebookLogin");
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("facebook_token", accessToken);
            AsyncTask<MultiValueMap<String, String>, Void, String> jsonRespond =
                    new RequestResponse<MultiValueMap<String, String>, String>(uri, 3,
                            String.class, App.getShowManager(), context).execute(map);
            try {
                JSONObject responseJsonObject = new JSONObject(jsonRespond.get());

                if (responseJsonObject != null && responseJsonObject.has("token_type") && responseJsonObject.getString("token_type").equals("bearer")) {
                    String accessKey = responseJsonObject.getString("access_token");
                    if (Settings.getMainscreen(context) == Mainscreen.WEB)
                        context.startActivity(new Intent(context, WebActivity.class).putExtra(LoginActivity.ACCESS_TOKEN, accessKey));
                    else
                        context.startActivity(new Intent(context, SearchActivity.class).putExtra(LoginActivity.ACCESS_TOKEN, accessKey));
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
                Log.e("Error!!!!!! ", e.getMessage());
            }
        } else
            twitterCache(context);
    }

    /**
     * Twitter cache handler
     *
     * @param context
     */
    private static void twitterCache(Context context) {
        String accessToken = App.getAuthManager().getAccessTokenTwitter();
        String secretAccessToken = App.getAuthManager().getSecretAccessTokenTwitter();
        if (accessToken != null && secretAccessToken != null) {
            URI uri = new UriConstructor(context).makeURI("twitterLogin");
            try {
                MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                map.add("twitter_token", accessToken);
                map.add("twitter_secret", secretAccessToken);

                AsyncTask<MultiValueMap<String, String>, Void, String> jsonRespond =
                        new RequestResponse<MultiValueMap<String, String>, String>(uri, 3,
                                String.class, App.getShowManager(), context).execute(map);
                JSONObject responseJsonObject = new JSONObject(jsonRespond.get());
                if (responseJsonObject != null && responseJsonObject.has("token_type") && responseJsonObject.getString("token_type").equals("bearer")) {
                    String accessKey = responseJsonObject.getString("access_token");
                    if (Settings.getMainscreen(context) == Mainscreen.WEB)
                        context.startActivity(new Intent(context, WebActivity.class).putExtra(LoginActivity.ACCESS_TOKEN, accessKey));
                    else
                        context.startActivity(new Intent(context, SearchActivity.class).putExtra(LoginActivity.ACCESS_TOKEN, accessKey));
                } else {
                    String errorStr;
                    if (responseJsonObject == null)
                        errorStr = "Error! Null response!";
                    else
                        errorStr = "Error " + responseJsonObject.getString("code") + ": "
                                + responseJsonObject.getString("error")
                                + ": " + responseJsonObject.getString("error_description");
                    ((FragmentActivity) context).finish();

                    App.getShowManager().showMessage(errorStr, context);
                    context.getSharedPreferences(AuthManager.PREF_FILENAME, Context.MODE_PRIVATE)
                            .edit()
                            .clear()
                            .commit();
                }
            } catch (InterruptedException | ExecutionException | JSONException e) {
                Log.e("Error!!!!!! ", e.getMessage());
            }
        }
    }
}
