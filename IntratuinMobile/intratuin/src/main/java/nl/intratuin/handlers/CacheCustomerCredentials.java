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
import nl.intratuin.SearchActivity;
import nl.intratuin.WebActivity;
import nl.intratuin.net.RequestResponse;
import nl.intratuin.net.UriConstructor;
import nl.intratuin.settings.Mainscreen;
import nl.intratuin.settings.Settings;

public class CacheCustomerCredentials {
    public static final String ACCESS_TOKEN = "accessToken";

    public static void cache(Context context) {
        String username = App.getAuthManager().getAccessKeyUsername();
        String password = App.getAuthManager().getAccessKeyPassword();
        if (username != null && password != null) {
            URI uri = new UriConstructor(context, ((FragmentActivity) context).getSupportFragmentManager()).makeURI("login");
            try {
                MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                map.add("grant_type", "password");
                map.add("client_id", username);
                map.add("client_secret", password);
                map.add("username", username);
                map.add("password", password);

                AsyncTask<MultiValueMap<String, String>, Void, String> jsonRespond =
                        new RequestResponse<MultiValueMap<String, String>, String>(uri, 3,
                                String.class, ((FragmentActivity) context).getSupportFragmentManager(), context).execute(map);
                JSONObject responseJsonObject = new JSONObject(jsonRespond.get());
                if (responseJsonObject != null && responseJsonObject.getString("token_type").equals("bearer")) {
                    String accessToken = responseJsonObject.getString("access_token");
                    if (Settings.getMainscreen(context) == Mainscreen.WEB)
                        context.startActivity(new Intent(context, WebActivity.class).putExtra(ACCESS_TOKEN, accessToken));
                    else context.startActivity(new Intent(context, SearchActivity.class).putExtra(ACCESS_TOKEN, accessToken));
                } else {
                    String errorStr;
                    if (responseJsonObject == null)
                        errorStr = "Error! Null response!";
                    else
                        errorStr = "Error " + responseJsonObject.getString("code") + ": "
                                + responseJsonObject.getString("error")
                                + ": " + responseJsonObject.getString("error_description");

                    ErrorFragment ef = ErrorFragment.newError(errorStr);
                    ef.show(((FragmentActivity) context).getSupportFragmentManager(), "Intratuin");
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

//private static void facebookCache(Context context){
//        String accessToken=App.getAuthManager().getAccessTokenFacebook();
//        if(accessToken!=null){
//        String loginByCache=new UriConstructor(((FragmentActivity)context).getSupportFragmentManager())
//        .makeFullURI("/customer").toString()+"/confirmFacebookAccessKey/{accessToken}";
////            String loginByCache = BuildConfig.API_HOME + "customer/confirmFacebookAccessKey/{accessToken}";
//        RequestResponseManager<LoginAndCacheResult>managerLoader=new RequestResponseManager<>(context,LoginAndCacheResult.class);
//        LoginAndCacheResult loginWithCache=managerLoader.loaderFromWebService(loginByCache,accessToken);
//        if(loginWithCache!=null){
//        if((loginWithCache.getAccessKey()!=null)){
//        Toast.makeText(context,loginWithCache.getMessage(),Toast.LENGTH_LONG).show();
//        context.startActivity(new Intent(context,WebActivity.class));
//        }else{
//        Toast.makeText(context,loginWithCache.getMessage(),Toast.LENGTH_LONG).show();
//        context.getSharedPreferences(AuthManager.PREF_FILENAME,Context.MODE_PRIVATE)
//        .edit()
//        .clear()
//        .commit();
//        }
//        }
//        }
//        // twitterCache(context);
//        }
//
//private static void twitterCache(Context context){
//        }
}
