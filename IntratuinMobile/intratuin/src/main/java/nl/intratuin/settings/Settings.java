package nl.intratuin.settings;

import android.content.Context;

import com.twitter.sdk.android.core.TwitterAuthConfig;

import nl.intratuin.R;
import nl.intratuin.settings.parser.AbstractUriConfigParser;
import nl.intratuin.settings.parser.UriParserFactory;

/**
 * Class {@code Settings} is a utility class with connection details.
 *
 * @author Ivan
 * @since 19.03.2016.
 */
//rename class
public class Settings {
    private static UriConfig uriConfig;
    private AbstractUriConfigParser uriConfigParser;

    public Settings(Context context){
        uriConfigParser = UriParserFactory.getParserImplementation(getBuildType(context), context);
        uriConfig=new UriConfig();
        uriConfigParser.parseUriConfiguration();
    }
    /**
     * Gets build type.
     *
     * @param context the context
     * @return the build type
     */
    public static BuildType getBuildType(Context context) {
        String type=context.getString(R.string.build_type);
        return BuildType.valueOf(type);
    }

    /**
     * Get host string.
     *
     * @param context the context
     * @return the string
     */
    public static String getHost(Context context){
        BuildType type=getBuildType(context);
        switch (type){
            case API:
                return context.getString(R.string.host_api);
            default:
                return context.getString(R.string.host_demoapi);
        }
    }

    /**
     * Gets twitter config.
     *
     * @param context the context
     * @return the twitter config
     */
    public static TwitterAuthConfig getTwitterConfig(Context context) {
        return new TwitterAuthConfig(context.getString(R.string.twitter_customer_key)
                ,context.getString(R.string.twitter_customer_secret));
    }

    /**
     * Gets the connection timeout
     *
     * @param context the context
     * @return the timeout
     */
    public static int getConnectionTimeout(Context context) {
        String timeout=context.getString(R.string.connection_timeout);
        return Integer.parseInt(timeout);
    }

    public static UriConfig getUriConfig() {
        return uriConfig;
    }

    public static void setUriConfig(UriConfig c){
        uriConfig=c;
    }
}