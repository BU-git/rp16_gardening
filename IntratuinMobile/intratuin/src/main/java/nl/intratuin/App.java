package nl.intratuin;

import android.app.Application;

import nl.intratuin.handlers.AuthManager;
import nl.intratuin.handlers.CacheCustomerCredentials;

public class App extends Application {

    private static App sInstance;
    private AuthManager authManager;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        authManager = new AuthManager(this);
    }

    public static AuthManager getAuthManager() {
        return sInstance.authManager;
    }
}
