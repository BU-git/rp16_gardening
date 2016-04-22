package nl.intratuin.handlers;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import java.util.concurrent.ExecutionException;

import nl.intratuin.net.RequestResponseGET;


public class RequestResponseManager<T> {
    private final Context context;
    Class<T> responseType;

    public RequestResponseManager(Context context, Class<T> responseType) {
        this.context = context;
        this.responseType = responseType;
    }

    public T loaderFromWebService(String url, String paramQuery) {
        T loaderResult = null;

        AsyncTask<String, Void, T> loaderCategoryResult =
                new RequestResponseGET(url, 1, responseType,
                        ((FragmentActivity) context).getSupportFragmentManager(), context).execute(paramQuery);

        try {
            loaderResult = loaderCategoryResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return loaderResult;
    }
}
