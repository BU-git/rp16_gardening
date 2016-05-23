package nl.intratuin;

import android.app.Application;

import nl.intratuin.manager.ShowManagerImpl;
import nl.intratuin.manager.AuthManager;

/**
 * Class {@code Category} is a base class to work with authorisation and showing alerts
 *
 * @see Application
 * @see AuthManager
 * @see ShowManagerImpl
 */
public class App extends Application {
    public static final String Fingerprint_FILENAME = "fingerprint";

    private static App sInstance;
    private AuthManager authManager;
    private AuthManager authManagerOfFingerprint;
    private ShowManagerImpl showManager;

    /**
     * Instantiating fields on creating
     */
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        authManager = new AuthManager(this);
        authManagerOfFingerprint = new AuthManager(this, Fingerprint_FILENAME);
        showManager = new ShowManagerImpl();

    }

    /**
     * Gets auth manager.
     *
     * @return the auth manager
     */
    public static AuthManager getAuthManager() {
        return sInstance.authManager;
    }
    public static AuthManager getAuthManagerOfFingerprint() {
        return sInstance.authManagerOfFingerprint;
    }

    /**
     * Gets show manager.
     *
     * @return the show manager
     */
    public static ShowManagerImpl getShowManager() {
        return sInstance.showManager;
    }
}
