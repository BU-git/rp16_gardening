package nl.intratuin.net;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import nl.intratuin.dto.TransferMessage;
import nl.intratuin.handlers.ErrorFragment;
import nl.intratuin.settings.Settings;

/**
 * Created by Иван on 25.03.2016.
 */
public class RequestResponse<T, V> extends AsyncTask<T, Void, V> {
    private URI uri;
    private int retry;
    Class<V> responseType;
    private FragmentManager fragmentManager;
    public RequestResponse(URI uri, int retry, Class<V> responseType, FragmentManager fragmentManager) {
        super();
        this.uri=uri;
        this.responseType = responseType;
        if(retry<1)
            this.retry=1;
        else this.retry=retry;
        this.fragmentManager=fragmentManager;
    }
    @Override
    protected V doInBackground(T... param) {
        try {
            V jsonObject=null;
            for(int i=0; i<retry; i++) {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                SimpleClientHttpRequestFactory rf =
                        (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
                rf.setReadTimeout(Settings.getConnectionTimeout());
                rf.setConnectTimeout(Settings.getConnectionTimeout());
                jsonObject = restTemplate.postForObject(uri, param[0], responseType);
                if(jsonObject!=null)
                    break;
            }
            return jsonObject;
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    protected void onPostExecute(V msg){
            ErrorFragment ef= ErrorFragment.newError(msg==null?"Request error!":msg.toString());
            //if(msg!=null && msg.getClass()==TransferMessage.class &&
            //        msg.toString().indexOf("success")==-1)
            if(msg!=null && msg.getClass()==TransferMessage.class)
                ef.show(fragmentManager, "Intratuin");
    }
}