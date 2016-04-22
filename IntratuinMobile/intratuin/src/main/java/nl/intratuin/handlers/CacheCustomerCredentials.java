package nl.intratuin.handlers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import nl.intratuin.App;
import nl.intratuin.WebActivity;
import nl.intratuin.dto.LoginAndCacheResult;
import nl.intratuin.net.UriConstructor;

public class CacheCustomerCredentials {
    /*
    public static void cache(Context context) {
        String accessKey = App.getAuthManager().getAccessKeyCredentials();
        if (accessKey != null) {
            String loginByCache = new UriConstructor(context, ((FragmentActivity) context).getSupportFragmentManager())
                    .makeFullURI("/customer").toString() + "/confirmCredentialsAccessKey/{accessKey}";
//            String loginByCache = BuildConfig.API_HOME + "customer/confirmCredentialsAccessKey/{accessKey}";
            RequestResponseManager<LoginAndCacheResult> managerLoader = new RequestResponseManager<>(context, LoginAndCacheResult.class);
            LoginAndCacheResult loginWithCache = managerLoader.loaderFromWebService(loginByCache, accessKey);
            if (loginWithCache != null) {
                if ((loginWithCache.getAccessKey() != null)) {
                    Toast.makeText(context, loginWithCache.getMessage(), Toast.LENGTH_LONG).show();
                    context.startActivity(new Intent(context, WebActivity.class));
                } else {
                    Toast.makeText(context, loginWithCache.getMessage(), Toast.LENGTH_LONG).show();
                    context.getSharedPreferences(AuthManager.PREF_FILENAME, Context.MODE_PRIVATE)
                            .edit()
                            .clear()
                            .commit();
                }
            }
        } else facebookCache(context);
    }

    private static void facebookCache(Context context) {
        String accessToken = App.getAuthManager().getAccessTokenFacebook();
        if (accessToken != null) {
            String loginByCache = new UriConstructor(((FragmentActivity) context).getSupportFragmentManager())
                    .makeFullURI("/customer").toString() + "/confirmFacebookAccessKey/{accessToken}";
//            String loginByCache = BuildConfig.API_HOME + "customer/confirmFacebookAccessKey/{accessToken}";
            RequestResponseManager<LoginAndCacheResult> managerLoader = new RequestResponseManager<>(context, LoginAndCacheResult.class);
            LoginAndCacheResult loginWithCache = managerLoader.loaderFromWebService(loginByCache, accessToken);
            if (loginWithCache != null) {
                if ((loginWithCache.getAccessKey() != null)) {
                    Toast.makeText(context, loginWithCache.getMessage(), Toast.LENGTH_LONG).show();
                    context.startActivity(new Intent(context, WebActivity.class));
                } else {
                    Toast.makeText(context, loginWithCache.getMessage(), Toast.LENGTH_LONG).show();
                    context.getSharedPreferences(AuthManager.PREF_FILENAME, Context.MODE_PRIVATE)
                            .edit()
                            .clear()
                            .commit();
                }
            }
        }
        // twitterCache(context);
    }

    private static void twitterCache(Context context) {
    }*/
}
