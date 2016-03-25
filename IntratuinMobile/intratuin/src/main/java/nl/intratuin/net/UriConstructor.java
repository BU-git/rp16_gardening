package nl.intratuin.net;

import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import nl.intratuin.settings.Settings;

/**
 * Created by Иван on 25.03.2016.
 */
public class UriConstructor {
    private TextView errorView;
    public UriConstructor(TextView errorView){
        this.errorView=errorView;
    }
    public URI makeFullURI(String localURL){
        try {
            String finLocalUrl=localURL;
            if(Settings.usingDeployed())
                finLocalUrl="/Intratuin"+finLocalUrl;
            int port=Settings.usingDeployed()?8888:8080;
            return new URL("http", Settings.getHost(),port,finLocalUrl).toURI();
        } catch (MalformedURLException e){
            errorView.setText("Wrong URL format!");
        } catch (URISyntaxException e){
            errorView.setText("Wrong URI format!");
        }
        return null;
    }
}
