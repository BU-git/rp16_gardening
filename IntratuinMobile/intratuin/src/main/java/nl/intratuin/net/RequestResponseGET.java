package nl.intratuin.net;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import nl.intratuin.dto.ShowManager;
import nl.intratuin.handlers.ErrorFragment;
import nl.intratuin.settings.Settings;

public class RequestResponseGET<T, V> extends AsyncTask<T, Void, V> {
    private String uri;
    private int retry;
    Class<V> responseType;
    private ShowManager showManager;
    Context context;

    public RequestResponseGET(String uri, int retry, Class<V> responseType, ShowManager showManager, Context context) {
        super();
        this.uri = uri;
        this.responseType = responseType;
        if (retry < 1)
            this.retry = 1;
        else this.retry = retry;
        this.showManager = showManager;
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
                return (V) restTemplate.getForObject(uri, responseType, param);
            } catch (Exception e) {
                showManager.showMessage("Error: " + e.getMessage(), context);
            }
        }
        return null;
    }
}