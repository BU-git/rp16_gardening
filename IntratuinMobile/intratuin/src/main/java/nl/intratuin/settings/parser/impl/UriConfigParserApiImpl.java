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
public class UriConfigParserApiImpl extends AbstractUriConfigParser {

    protected UriConfig uriConfig;

    public UriConfigParserApiImpl(Context context) {
        super(context);
    }

    @Override
    public void parseUriConfiguration() {
        uriConfig = Settings.getUriConfig();

        try {
            uriConfig.setLogin(new URL("http", Settings.getHost(context), context.getString(R.string.login_api)).toURI());
            uriConfig.setRegistration(new URL("https", context.getString(R.string.registration_host),
                    context.getString(R.string.registration_relative) + context.getString(R.string.company_id)).toURI());
            uriConfig.setTwitterLogin(null);
            uriConfig.setFacebookLogin(null);
            uriConfig.setCategoryList(null);
            uriConfig.setProductSearch(null);
            uriConfig.setUserInfo(new URL("http", Settings.getHost(context), context.getString(R.string.info_api)
                    + context.getString(R.string.company_id) + context.getString(R.string.info_api_end)).toURI());
            uriConfig.setSearch(null);
            uriConfig.setProductsInCategory(null);
            uriConfig.setBarcode(new URL("http", Settings.getHost(context), context.getString(R.string.barcode_api)
                    + context.getString(R.string.company_id) + context.getString(R.string.barcode_api_ending)).toURI());
            Settings.setUriConfig(uriConfig);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}