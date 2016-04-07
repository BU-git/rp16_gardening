package nl.intratuin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.List;

import nl.intratuin.dto.Category;
import nl.intratuin.dto.Product;
import nl.intratuin.handlers.ProductAutoCompleteAdapter;
import nl.intratuin.net.UriConstructor;

public class SearchActivity extends AppCompatActivity implements OnClickListener {
    ListView lSearch;
    String[] categories;
    ImageButton bSearchBarcode;
    ImageButton bSearchMan;
    ImageButton bSearchBasket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_search);

        bSearchBarcode = (ImageButton)findViewById(R.id.bSearchBarcode);
        bSearchMan = (ImageButton)findViewById(R.id.bSearchMan);
        bSearchBasket = (ImageButton)findViewById(R.id.bSearchBasket);
        lSearch = (ListView) findViewById(R.id.lSearch);
        categories = getResources().getStringArray(R.array.categories);
        final ArrayAdapter<String> adapterCategories = new ArrayAdapter<String>(this, R.layout.activity_categories, android.R.id.text1, categories);

        bSearchBasket.setOnClickListener(this);
        bSearchMan.setOnClickListener(this);
        bSearchBasket.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bSearchBarcode:
                Intent scannerIntent = new Intent(this, ScannerActivity.class);
                startActivity(scannerIntent);
                break;

            case R.id.bSearchMan:

                break;

            case R.id.bSearchBasket:

                break;
        }
    }
}