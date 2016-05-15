package nl.intratuin.net;

import android.content.Context;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import nl.intratuin.R;
import nl.intratuin.settings.Settings;

/**
 * Class {@code UriConstructor} is helping to construct URI.
 *
 * @author Ivan
 * @since 25.03.2016.
 */
//optimize
public class UriConstructor {
    private Context context;

    /**
     * Instantiates a new Uri constructor.
     *
     * @param context the context
     */
    public UriConstructor(Context context) {
        this.context = context;
    }

    /**
     * Make the correct URI. First in cases of login type, then in cases to which server we want to connect
     *
     * @param action the action
     * @return the uri
     */
    public URI makeURI(String action) {
        try {
            switch (action) {
                case "login":
                    switch (Settings.getBuildType(context)) {
                        case DEPLOYED:
                            return new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                                    , "/Intratuin" + context.getString(R.string.login_server)).toURI();
                        case API:
                        case DEMOAPI:
                            return new URL("http", Settings.getHost(context), context.getString(R.string.login_api)).toURI();
                        default:
                            return new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_local))
                                    , context.getString(R.string.login_server)).toURI();
                    }
                case "registration":
                    switch (Settings.getBuildType(context)) {
                        case DEPLOYED:
                            return new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                                    , "/Intratuin" + context.getString(R.string.registration_server)).toURI();
                        case API:
                        case DEMOAPI:
                            return new URL("https", context.getString(R.string.registration_host),
                                    context.getString(R.string.registration_relative)+context.getString(R.string.company_id)).toURI();
                        default:
                            return new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_local))
                                    , context.getString(R.string.registration_server)).toURI();
                    }
                case "twitterLogin":
                    switch (Settings.getBuildType(context)) {
                        case DEPLOYED:
                            return new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                                    , "/Intratuin" + context.getString(R.string.twitter_login)).toURI();
                        case API:
                        case DEMOAPI:
                            return null;
                        default:
                            return new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_local))
                                    , context.getString(R.string.twitter_login)).toURI();
                    }
                case "facebookLogin":
                    switch (Settings.getBuildType(context)) {
                        case DEPLOYED:
                            return new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                                    , "/Intratuin" + context.getString(R.string.facebook_login)).toURI();
                        case API:
                        case DEMOAPI:
                            return null;
                        default:
                            return new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_local))
                                    , context.getString(R.string.facebook_login)).toURI();
                    }
                case "categoryList":
                    switch (Settings.getBuildType(context)) {
                        case DEPLOYED:
                            return new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                                    , "/Intratuin" + context.getString(R.string.category_list)).toURI();
                        case API:
                        case DEMOAPI:
                            return null;
                        default:
                            return new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_local))
                                    , context.getString(R.string.category_list)).toURI();
                    }
                case "productSearch":
                    switch (Settings.getBuildType(context)) {
                        case DEPLOYED:
                            return new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                                    , "/Intratuin" + context.getString(R.string.product_search)).toURI();
                        case API:
                        case DEMOAPI:
                            return null;
                        default:
                            return new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_local))
                                    , context.getString(R.string.product_search)).toURI();
                    }
                case "userInfo":
                    switch (Settings.getBuildType(context)) {
                        case DEPLOYED:
                            return new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                                    , "/Intratuin" + context.getString(R.string.info_server)).toURI();
                        case API:
                        case DEMOAPI:
                            return new URL("http", Settings.getHost(context), context.getString(R.string.info_api)
                            +context.getString(R.string.company_id)+context.getString(R.string.info_api_end)).toURI();
                        default:
                            return new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_local))
                                    , context.getString(R.string.info_server)).toURI();
                    }
                case "search"://product search by name beginning
                    switch (Settings.getBuildType(context)) {
                        case DEPLOYED:
                            return new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                                    , "/Intratuin" + context.getString(R.string.product_search)).toURI();
                        case API:
                        case DEMOAPI:
                            return null;
                        default:
                            return new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_local))
                                    , context.getString(R.string.product_search)).toURI();
                    }
                case "productsInCategory":
                    switch (Settings.getBuildType(context)) {
                        case DEPLOYED:
                            return new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                                    , "/Intratuin" + context.getString(R.string.products_in_category)).toURI();
                        case API:
                        case DEMOAPI:
                            return null;
                        default:
                            return new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_local))
                                    , context.getString(R.string.products_in_category)).toURI();
                    }
                default:
                    return null;
            }
            //correct handling
        } catch (MalformedURLException | URISyntaxException e) {
            return null;
        }
    }
}
