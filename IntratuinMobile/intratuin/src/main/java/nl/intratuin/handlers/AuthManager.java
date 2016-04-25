package nl.intratuin.handlers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class AuthManager {
    public static final String PREF_FILENAME = "auth";

    public static final String PREF_USERNAME = "access key: username";
    public static final String PREF_PASSWORD = "access key: password";
    public static final String PREF_FACEBOOK= "access token: facebook";
    public static final String PREF_TWITTER = "access key: twitter";

    private final SharedPreferences pref;

    public AuthManager(Context context) {
        pref = context.getSharedPreferences(PREF_FILENAME, Context.MODE_PRIVATE);
    }

    public void loginAndCache(@NonNull String keyToCache, @NonNull String accessToken) {
        pref.edit()
                .putString(keyToCache, accessToken)
                .apply();
    }

    public String getAccessTokenFacebook() {
        return pref.getString(PREF_FACEBOOK, null);
    }

    public String getAccessTokenTwitter() {
        return pref.getString(PREF_TWITTER, null);
    }

    public String getAccessKeyUsername() {
        return pref.getString(PREF_USERNAME, null);
    }

    public String getAccessKeyPassword() {
        return pref.getString(PREF_PASSWORD, null);
    }
}
