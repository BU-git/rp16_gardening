package nl.intratuin.handlers;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.concurrent.ExecutionException;

import nl.intratuin.App;
import nl.intratuin.WebActivity;
import nl.intratuin.dto.ResponseAccessToken;
import nl.intratuin.net.RequestResponse;
import nl.intratuin.net.UriConstructor;

public class CacheCustomerCredentials {

    public static void cache(Context context) {
        String username = App.getAuthManager().getAccessKeyUsername();
        String password = App.getAuthManager().getAccessKeyPassword();
        if (username != null && password != null) {
            ResponseAccessToken responseAccessToken = new ResponseAccessToken();
            URI uri = new UriConstructor(context, ((FragmentActivity) context).getSupportFragmentManager()).makeURI("login");
            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            map.add("grant_type", "password");
            map.add("client_id", username);
            map.add("client_secret", password);
            map.add("username", username);
            map.add("password", password);

            AsyncTask<MultiValueMap<String, String>, Void, ResponseAccessToken> jsonRespond =
                    new RequestResponse<MultiValueMap<String, String>, ResponseAccessToken>(uri, 3,
                            ResponseAccessToken.class, ((FragmentActivity) context).getSupportFragmentManager(), context).execute(map);
            if (jsonRespond == null) {
                ErrorFragment ef = ErrorFragment.newError("Error! No response.");
                ef.show(((FragmentActivity) context).getSupportFragmentManager(), "Intratuin");
            }
            try {
                responseAccessToken = jsonRespond.get();
                if (responseAccessToken != null && responseAccessToken.getToken_type().equals("bearer")) {
                    //TODO: save access token, pass it to next activity, and remove toast!
                    Toast.makeText(context, "Cached user " + responseAccessToken.toString(), Toast.LENGTH_LONG).show();
                    context.startActivity(new Intent(context, WebActivity.class));
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
            context.getSharedPreferences(AuthManager.PREF_FILENAME, Context.MODE_PRIVATE)
                    .edit()
                    .clear()
                    .commit();
//            facebookCache(context);
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
