package nl.intratuin.net;
import android.os.AsyncTask;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import nl.intratuin.dto.Product;

public class RequestResponseGET<T, V> extends AsyncTask<T, Void, List<V>> {
    private String  uri;
    private int retry;
    Class<V> responseType;
    public RequestResponseGET(String uri, int retry, Class<V> responseType) {
        super();
        this.uri=uri;
        this.responseType = responseType;
        if(retry<1)
            this.retry=1;
        else this.retry=retry;
    }
    @Override
    protected List<V> doInBackground(T... param) {
        try {
            V[] jsonObject=null;
                for(int i=0; i<retry; i++) {
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    jsonObject = (V[]) restTemplate.getForObject(uri, responseType, param);
                    if(jsonObject!=null)
                        break;
            }
            return Arrays.asList(jsonObject);
        } catch (Exception e) {
            return null;
        }
    }
}