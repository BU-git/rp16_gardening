package nl.intratuin.manager;

import android.content.Context;
import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

import nl.intratuin.manager.contract.ShowManager;
import nl.intratuin.net.RequestResponseGET;


/**
 * The class RequestResponseManager is helping to get a response from web service and generify it to specific type
 *
 * @param <T> the type parameter
 */
public class RequestResponseManager<T> {
    private final Context context;
    private Class<T> responseType;
    private ShowManager showManager;

    /**
     * Instantiates a new Request response manager.
     *
     * @param context      the context
     * @param showManager  the show manager
     * @param responseType the response type
     */
    public RequestResponseManager(Context context, ShowManager showManager, Class<T> responseType) {
        this.context = context;
        this.responseType = responseType;
        this.showManager = showManager;
    }

    /**
     * Send a request to web server and load a result in a specific type
     *
     * @param url        the url
     * @param paramQuery the query
     * @return the type of structure
     */
    public T loaderFromWebService(String url, String paramQuery) {
        T loaderResult = null;

        AsyncTask<String, Void, T> loaderCategoryResult =
                new RequestResponseGET(url, 1, responseType, showManager, context).execute(paramQuery);

        try {
            loaderResult = loaderCategoryResult.get();
        } catch (InterruptedException | ExecutionException e) {
            showManager.showMessage("Error in RequestResponseManager: " + e.getMessage(), context);
        }
        return loaderResult;
    }
}
