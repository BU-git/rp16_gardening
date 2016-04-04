package nl.intratuin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.net.URI;
import java.util.List;

import nl.intratuin.dto.Category;
import nl.intratuin.handlers.ProductAutoCompleteAdapter;
import nl.intratuin.net.UriConstructor;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ListView lSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_search);

        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.svIntratuin);
        textView.setThreshold(3);
        ProductAutoCompleteAdapter adapter = new ProductAutoCompleteAdapter(this);
        textView.setAdapter(adapter);

        List<Category> root = null;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private List<Category> getRootCategories(){
        URI rootCategoryURI = new UriConstructor(getSupportFragmentManager())
                .makeFullURI("/category/root");
        return null;
    }
}