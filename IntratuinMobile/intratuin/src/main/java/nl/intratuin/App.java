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

    private static App sInstance;
    private AuthManager authManager;
    private ShowManagerImpl showManager;

    /**
     * Instantiating fields on creating
     */
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        authManager = new AuthManager(this);
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

    /**
     * Gets show manager.
     *
     * @return the show manager
     */
    public static ShowManagerImpl getShowManager() {
        return sInstance.showManager;
    }
}
