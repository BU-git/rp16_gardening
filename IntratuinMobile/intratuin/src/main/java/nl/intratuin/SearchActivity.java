package nl.intratuin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import nl.intratuin.handlers.ProductAutoCompleteAdapter;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    AutoCompleteTextView svIntratuin;
    ListView lSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.svIntratuin);
        textView.setThreshold(3);
        ProductAutoCompleteAdapter adapter = new ProductAutoCompleteAdapter(this);
        textView.setAdapter(adapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}