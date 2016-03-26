package nl.intratuin.net;

import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import nl.intratuin.handlers.ErrorFragment;
import nl.intratuin.settings.Settings;

/**
 * Created by Иван on 25.03.2016.
 */
public class UriConstructor {
    private FragmentManager fragmentManager;
    public UriConstructor(FragmentManager fragmentManager){
        this.fragmentManager=fragmentManager;
    }
    public URI makeFullURI(String localURL){
        try {
            String finLocalUrl=localURL;
            if(Settings.usingDeployed())
                finLocalUrl="/Intratuin"+finLocalUrl;
            int port=Settings.usingDeployed()?8888:8080;
            return new URL("http", Settings.getHost(),port,finLocalUrl).toURI();
        } catch (MalformedURLException e){
            ErrorFragment ef= ErrorFragment.newError("Wrong URL format!");
            ef.show(fragmentManager, "Intratuin");
        } catch (URISyntaxException e){
            ErrorFragment ef= ErrorFragment.newError("Wrong URI format!");
            ef.show(fragmentManager, "Intratuin");
        }
        return null;
    }
}
