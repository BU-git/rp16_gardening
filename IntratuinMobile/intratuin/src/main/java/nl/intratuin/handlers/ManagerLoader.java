package nl.intratuin.handlers;


import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nl.intratuin.dto.Category;
import nl.intratuin.dto.Product;
import nl.intratuin.net.RequestResponseGET;
import nl.intratuin.net.UriConstructor;

public class ManagerLoader<T> {
    private final Context context;
    Class<T> responseType;

    public ManagerLoader(Context context, Class<T> responseType) {
        this.context = context;
        this.responseType = responseType;
    }
    public List<T> loaderFromWebService(String url, String paramQuery) {
        List<T> loaderResult = new ArrayList<>();

        AsyncTask<String, Void, List<T>> loaderCategoryResult =
                new RequestResponseGET(url, 1, responseType,
                        ((FragmentActivity) context).getSupportFragmentManager()).execute(paramQuery);

        try {
            loaderResult = loaderCategoryResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return loaderResult;
    }

}
