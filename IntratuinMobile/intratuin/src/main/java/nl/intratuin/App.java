package nl.intratuin;

import android.app.Application;

import nl.intratuin.dto.ShowManagerImpl;
import nl.intratuin.handlers.AuthManager;
import nl.intratuin.handlers.CacheCustomerCredentials;

public class App extends Application {

    private static App sInstance;
    private AuthManager authManager;
    private ShowManagerImpl showManager;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        authManager = new AuthManager(this);
        showManager = new ShowManagerImpl();

    }

    public static AuthManager getAuthManager() {
        return sInstance.authManager;
    }
    public static ShowManagerImpl getShowManager() {
        return sInstance.showManager;
    }
}
