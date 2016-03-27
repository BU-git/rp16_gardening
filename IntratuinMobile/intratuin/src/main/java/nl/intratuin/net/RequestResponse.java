package nl.intratuin.net;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.json.JSONObject;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import nl.intratuin.dto.Message;
import nl.intratuin.handlers.ErrorFragment;
import nl.intratuin.settings.Settings;

/**
 * Created by Иван on 25.03.2016.
 */
public class RequestResponse<T> extends AsyncTask<T, Void, Message> {
    private URI uri;
    private int retry;
    private FragmentManager fragmentManager;
    public RequestResponse(URI uri, int retry, FragmentManager fragmentManager) {
        super();
        this.uri=uri;
        if(retry<1)
            this.retry=1;
        else this.retry=retry;
        this.fragmentManager=fragmentManager;
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
                rf.setReadTimeout(Settings.getConnectionTimeout());
                rf.setConnectTimeout(Settings.getConnectionTimeout());
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
        ErrorFragment ef= ErrorFragment.newError(msg==null?"Request error!":msg.getMessage());
        ef.show(fragmentManager, "Intratuin");
    }
}