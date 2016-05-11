package nl.intratuin.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Class {@code AuthManager} is used for login with user chosen credentials.
 *
 * @see nl.intratuin.LoginActivity
 */
public class AuthManager {
    /**
     * The constant PREF_FILENAME is used for authentication.
     */
    public static final String PREF_FILENAME = "auth";

    /**
     * The constant PREF_USERNAME hold the "username" key for access.
     */
    public static final String PREF_USERNAME = "access key: username";
    /**
     * The constant PREF_PASSWORD hold the "password" key for access.
     */
    public static final String PREF_PASSWORD = "access key: password";
    /**
     * The constant PREF_FACEBOOK hold the key to access to Facebook with token.
     */
    public static final String PREF_FACEBOOK = "access token: facebook";
    /**
     * The constant PREF_TWITTER_ACCESS_TOKEN hold the key to access to Twitter with token.
     */
    public static final String PREF_TWITTER_ACCESS_TOKEN = "access token: twitter";
    /**
     * The constant PREF_TWITTER_SECRET_ACCESS_TOKEN hold the additional key to access to Facebook with token..
     */
    public static final String PREF_TWITTER_SECRET_ACCESS_TOKEN = "secret access key: twitter";

    private final SharedPreferences pref;

    /**
     * Instantiates a new Auth manager.
     *
     * @param context the context
     * @see nl.intratuin.App
     */
    public AuthManager(Context context) {
        pref = context.getSharedPreferences(PREF_FILENAME, Context.MODE_PRIVATE);
    }

    /**
     * Setting and applying login and cache.
     *
     * @param keyToCache  the key to cache
     * @param accessToken the access token
     */
    public void loginAndCache(@NonNull String keyToCache, @NonNull String accessToken) {
        pref.edit()
                .putString(keyToCache, accessToken)
                .apply();
    }

    /**
     * Gets facebook access token.
     *
     * @return the access token facebook
     */
    public String getAccessTokenFacebook() {
        return pref.getString(PREF_FACEBOOK, null);
    }

    /**
     * Gets twitter access token.
     *
     * @return the access token twitter
     */
    public String getAccessTokenTwitter() {
        return pref.getString(PREF_TWITTER_ACCESS_TOKEN, null);
    }

    /**
     * Gets twitter secret access token.
     *
     * @return the secret access token twitter
     */
    public String getSecretAccessTokenTwitter() {
        return pref.getString(PREF_TWITTER_SECRET_ACCESS_TOKEN, null);
    }

    /**
     * Gets username access key.
     *
     * @return the access key username
     */
    public String getAccessKeyUsername() {
        return pref.getString(PREF_USERNAME, null);
    }

    /**
     * Gets password access key.
     *
     * @return the access key password
     */
    public String getAccessKeyPassword() {
        return pref.getString(PREF_PASSWORD, null);
    }
}
