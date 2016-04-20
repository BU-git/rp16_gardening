package nl.intratuin.settings;

import com.twitter.sdk.android.core.TwitterAuthConfig;

/**
 * Created by Иван on 19.03.2016.
 */
public class Settings {
    // Note: Parameters should be obfuscated in your source code before shipping.
    private static final String twitter_par1 = "CpdWZOr27su9Vduhipt3XVUgD";
    private static final String twitter_par2 = "pcOrskD4hWcUGxi5n3nMvtL3bArhIuyj2pmiMCQ7E9jj71wH5w";

    private static boolean useDeployed = true;

    private static int connectionTimeout=10000;

    private static String hostLocal="192.168.1.23";//local -- depends on your computer's inner ip
    private static String hostBionic="128.0.169.5";//bionic -- do not change

    public static boolean usingDeployed() { return useDeployed; }
    public static String getHost(){ return useDeployed?hostBionic:hostLocal;}
    public static TwitterAuthConfig getTwitterConfig() {
        return new TwitterAuthConfig(twitter_par1,twitter_par2);
    }

    public static int getConnectionTimeout() { return connectionTimeout; }
}