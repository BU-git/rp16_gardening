package nl.intratuin.settings.parser.impl;

import android.content.Context;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import nl.intratuin.R;
import nl.intratuin.settings.Settings;
import nl.intratuin.settings.UriConfig;
import nl.intratuin.settings.parser.AbstractUriConfigParser;

/**
 * Created by Иван on 17.05.2016.
 */
public class UriConfigParserDeployedImpl extends AbstractUriConfigParser {

    protected UriConfig uriConfig;

    public UriConfigParserDeployedImpl(Context context) {
        super(context);
    }

    @Override
    public void parseUriConfiguration() {
        uriConfig = Settings.getUriConfig();

        try {
            uriConfig.setLogin(new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                    , "/Intratuin" + context.getString(R.string.login_server)).toURI());
            uriConfig.setRegistration(new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                    , "/Intratuin" + context.getString(R.string.registration_server)).toURI());
            uriConfig.setTwitterLogin(new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                    , "/Intratuin" + context.getString(R.string.twitter_login)).toURI());
            uriConfig.setFacebookLogin(new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                    , "/Intratuin" + context.getString(R.string.facebook_login)).toURI());
            uriConfig.setCategoryList(new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                    , "/Intratuin" + context.getString(R.string.category_list)).toURI());
            uriConfig.setProductSearch(new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                    , "/Intratuin" + context.getString(R.string.product_search)).toURI());
            uriConfig.setUserInfo(new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                    , "/Intratuin" + context.getString(R.string.info_server)).toURI());
            uriConfig.setSearch(new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                    , "/Intratuin" + context.getString(R.string.product_search)).toURI());
            uriConfig.setProductsInCategory(new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                    , "/Intratuin" + context.getString(R.string.products_in_category)).toURI());
            uriConfig.setBarcode(new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                    , "/Intratuin" + context.getString(R.string.barcode_server)).toURI());
            uriConfig.setCustomerByToken(new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                    , "/Intratuin" + context.getString(R.string.customer_by_token)).toURI());
            uriConfig.setCustomerPersonal(new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                    , "/Intratuin" + context.getString(R.string.update_customer_data)).toURI());
            uriConfig.setCategoryName(new URL("http", Settings.getHost(context), Integer.parseInt(context.getString(R.string.port_deployed))
                    , "/Intratuin" + context.getString(R.string.category_name)).toURI());
            Settings.setUriConfig(uriConfig);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}