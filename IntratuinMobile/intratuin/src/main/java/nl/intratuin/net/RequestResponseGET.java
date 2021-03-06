package nl.intratuin.net;

import android.content.Context;
import android.os.AsyncTask;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import nl.intratuin.manager.contract.ShowManager;
import nl.intratuin.settings.Settings;

/**
 * The type Request response get.
 *
 * @param <T> the type parameter
 * @param <V> the type parameter
 */
public class RequestResponseGET<T, V> extends AsyncTask<T, Void, V> {
    private String uri;
    private int retry;
    private Class<V> responseType;
    private ShowManager showManager;
    private Context context;

    /**
     * Instantiates a new Request response get.
     *
     * @param uri          the uri
     * @param retry        the retry
     * @param responseType the response type
     * @param showManager  the show manager
     * @param context      the context
     */
    public RequestResponseGET(String uri, int retry, Class<V> responseType, ShowManager showManager, Context context) {
        super();
        this.uri = uri;
        this.responseType = responseType;
        if (retry < 1)
            this.retry = 1;
        else this.retry = retry;
        this.showManager = showManager;
        this.context = context;
    }

    /**
     * Connection to web service with number of tries
     * @param param
     * @return
     */
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