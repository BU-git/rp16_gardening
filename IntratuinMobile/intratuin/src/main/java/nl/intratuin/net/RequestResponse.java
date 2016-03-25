package nl.intratuin.net;

import android.os.AsyncTask;
import android.widget.TextView;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import nl.intratuin.dto.Message;

/**
 * Created by Иван on 25.03.2016.
 */
public class RequestResponse<T> extends AsyncTask<T, Void, Message> {
    private URI uri;
    private int retry;
    private TextView errorView;
    public RequestResponse(URI uri, int retry, TextView errorView) {
        super();
        this.uri=uri;
        if(retry<1)
            this.retry=1;
        else this.retry=retry;
        this.errorView=errorView;
    }
    @Override
    protected Message doInBackground(T... param) {
        try {
            Message jsonObject=null;
            for(int i=0; i<retry; i++) {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                SimpleClientHttpRequestFactory rf =
                        (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
                rf.setReadTimeout(2000);
                rf.setConnectTimeout(2000);
                jsonObject = restTemplate.postForObject(uri, param[0], Message.class);
                if(jsonObject!=null)
                    break;
            }
            return jsonObject;
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    protected void onPostExecute(Message msg){
        errorView.setText(msg==null?"Request error!":msg.getMessage());
    }
}