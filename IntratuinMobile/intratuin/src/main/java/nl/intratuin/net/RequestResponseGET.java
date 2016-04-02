package nl.intratuin.net;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.intratuin.dto.Product;

public class RequestResponseGET<T> extends AsyncTask<T, Void, List<Product>> {
    private String  uri;
    private int retry;
    private FragmentManager fragmentManager;
    public RequestResponseGET(String uri, int retry, FragmentManager fragmentManager) {
        super();
        this.uri=uri;
        if(retry<1)
            this.retry=1;
        else this.retry=retry;
//        this.fragmentManager=fragmentManager;
    }
    @Override
    protected List<Product> doInBackground(T... param) {
        try {
            Product[] jsonObject=null;
            for(int i=0; i<retry; i++) {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                jsonObject = restTemplate.getForObject(uri, Product[].class, param);
                if(jsonObject!=null)
                    break;
            }
            return Arrays.asList(jsonObject);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Product> products) {
        super.onPostExecute(products);
    }
}