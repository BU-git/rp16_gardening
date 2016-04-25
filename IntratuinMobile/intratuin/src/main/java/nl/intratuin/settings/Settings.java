package nl.intratuin.settings;

import android.content.Context;

import com.twitter.sdk.android.core.TwitterAuthConfig;

import nl.intratuin.R;

/**
 * Created by Иван on 19.03.2016.
 */
public class Settings {
        public static BuildType getBuildType(Context context) {
        String type=context.getString(R.string.build_type);
        return BuildType.valueOf(type);
    }
    public static Mainscreen getMainscreen(Context context) {
        BuildType type = getBuildType(context);
        if(type==BuildType.API || type==BuildType.DEMOAPI)
            return Mainscreen.WEB;
        String screen=context.getString(R.string.mainscreen);
        return Mainscreen.valueOf(screen);
    }
    public static String getHost(Context context){
        BuildType type=getBuildType(context);
        switch (type){
            case DEPLOYED:
                return context.getString(R.string.host_deployed);
            case API:
                return context.getString(R.string.host_api);
            case DEMOAPI:
                return context.getString(R.string.host_demoapi);
            default:
                return context.getString(R.string.host_local);
        }
    }
    public static TwitterAuthConfig getTwitterConfig(Context context) {
        return new TwitterAuthConfig(context.getString(R.string.twitter_customer_key)
                ,context.getString(R.string.twitter_customer_secret));
    }

    public static int getConnectionTimeout(Context context) {
        String timeout=context.getString(R.string.connection_timeout);
        return Integer.parseInt(timeout);
    }
}