package nl.intratuin.net;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import nl.intratuin.dto.Product;
import nl.intratuin.handlers.ErrorFragment;
import nl.intratuin.settings.Settings;

public class RequestResponseGET<T, V> extends AsyncTask<T, Void, List<V>> {
    private String  uri;
    private int retry;
    Class<V> responseType;
    private FragmentManager fragmentManager;
    public RequestResponseGET(String uri, int retry, Class<V> responseType, FragmentManager fragmentManager) {
        super();
        this.uri=uri;
        this.responseType = responseType;
        if(retry<1)
            this.retry=1;
        else this.retry=retry;
        this.fragmentManager=fragmentManager;
    }
    @Override
    protected List<V> doInBackground(T... param) {
        try {
            V[] jsonObject=null;
                for(int i=0; i<retry; i++) {
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    SimpleClientHttpRequestFactory rf =
                            (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
                    rf.setReadTimeout(Settings.getConnectionTimeout());
                    rf.setConnectTimeout(Settings.getConnectionTimeout());
                    jsonObject = (V[]) restTemplate.getForObject(uri, responseType, param);
                    if(jsonObject!=null)
                        break;
            }
            return Arrays.asList(jsonObject);
        } catch (Exception e) {
            ErrorFragment ef= ErrorFragment.newError("GET request error!");
            ef.show(fragmentManager, "Intratuin");
            return null;
        }
    }
}