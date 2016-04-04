package nl.intratuin;

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

import nl.intratuin.dto.Product;
import nl.intratuin.handlers.ProductAutoCompleteAdapter;

public class SearchActivity extends AppCompatActivity{
    ListView lSearch;
    String[] categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

