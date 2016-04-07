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

public class ManagerLoader {
    private final Context context;

    public ManagerLoader(Context context) {
        this.context = context;
    }
    public List<Category> loaderFromWebService(String url, String paramQuery) {
        List<Category> loaderResult = new ArrayList<>();

        AsyncTask<String, Void, List<Category>> loaderCategoryResult =
                new RequestResponseGET(url, 1, Category[].class,
                        ((FragmentActivity) context).getSupportFragmentManager()).execute(paramQuery);
//        RequestResponseGET<String, Category> requestResponseGET = new RequestResponseGET(url, 1, Category[].class,
//                ((FragmentActivity) context).getSupportFragmentManager());
//
//        List<Category> loaderCategoryResult = (List<Category>) requestResponseGET.execute(paramQuery);
        try {
            loaderResult = loaderCategoryResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return loaderResult;
    }

}
