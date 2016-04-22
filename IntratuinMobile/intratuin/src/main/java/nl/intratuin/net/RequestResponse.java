package nl.intratuin.net;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import nl.intratuin.settings.Settings;

/**
 * Created by Иван on 25.03.2016.
 */
public class RequestResponse<T, V> extends AsyncTask<T, Void, V> {
    private URI uri;
    private int retry;
    Class<V> responseType;
    Context context;
    private FragmentManager fragmentManager;
    public RequestResponse(URI uri, int retry, Class<V> responseType, FragmentManager fragmentManager, Context context) {
        super();
        this.uri=uri;
        this.responseType = responseType;
        if(retry<1)
            this.retry=1;
        else this.retry=retry;
        this.fragmentManager=fragmentManager;
        this.context=context;
    }
    @Override
    protected V doInBackground(T... param) {
        for (int i = 0; i < retry; i++) {
            try {
                RestTemplate restTemplate = new RestTemplate(true);
                SimpleClientHttpRequestFactory rf =
                        (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
                rf.setReadTimeout(Settings.getConnectionTimeout(context));
                rf.setConnectTimeout(Settings.getConnectionTimeout(context));
                return (V) restTemplate.postForObject(uri, param[0], responseType);
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage(), e);
            }
        }
        return null;
    }
}