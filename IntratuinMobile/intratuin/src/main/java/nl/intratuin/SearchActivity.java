package nl.intratuin;

<<<<<<< HEAD
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SearchView;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener {

    SearchView svIntratuin;
    ListView lSearch;
    // Progress Dialog
    private ProgressDialog pDialog;
=======
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.List;

import nl.intratuin.dto.Category;
import nl.intratuin.dto.Product;
import nl.intratuin.handlers.ProductAutoCompleteAdapter;
import nl.intratuin.net.UriConstructor;

public class SearchActivity extends AppCompatActivity{
    ListView lSearch;
    String[] categories;
>>>>>>> 2bae88b5df9a58aefce22a28e19a5c75f5dffdfa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        setContentView(R.layout.activity_search);

        svIntratuin = (SearchView) findViewById(R.id.svIntratuin);
        lSearch = (ListView) findViewById(R.id.lSearch);

        svIntratuin.setOnQueryTextListener(this);
        svIntratuin.setOnCloseListener(this);

        new JsonSearchTask().execute();

    }

    public boolean onQueryTextChange(String newText) {

        return false;
    }

    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    public boolean onClose() {

        return false;
    }

    private class JsonSearchTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchActivity.this);
            pDialog.setMessage("@string/loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
=======
        getSupportActionBar().hide();

        setContentView(R.layout.activity_search);

        lSearch = (ListView) findViewById(R.id.lSearch);
        categories = getResources().getStringArray(R.array.categories);
        final ArrayAdapter<String> adapterCategories = new ArrayAdapter<String>(this, R.layout.activity_categories, android.R.id.text1, categories);
        lSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int position, long id) {
                String categoryName = (String) adapterView.getItemAtPosition(position);
                Toast.makeText(SearchActivity.this, categoryName, Toast.LENGTH_SHORT).show();
            }
        });
        lSearch.setAdapter(adapterCategories);

        ProductAutoCompleteAdapter searchAdapter = new ProductAutoCompleteAdapter(this);
        AutoCompleteTextView autoCompleteTV = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autoCompleteTV.setThreshold(3);
        autoCompleteTV.setAdapter(searchAdapter);
        autoCompleteTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) parent.getItemAtPosition(position);

                Intent productPadeIntent = new Intent(SearchActivity.this, ProductDetailsPageActivity.class);
                productPadeIntent.putExtra("productName", product.getProductName());
                productPadeIntent.putExtra("productPrice", product.getProductPrice());
                productPadeIntent.putExtra("productImage", product.getProductImage());
                startActivity(productPadeIntent);
            }
        });
    }
}
>>>>>>> 2bae88b5df9a58aefce22a28e19a5c75f5dffdfa
