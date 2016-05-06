package nl.intratuin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nl.intratuin.dto.Product;
import nl.intratuin.settings.ToolBarActivity;

/**
 * The class {@code ProductDetailsPageActivity} is used to provide logic on Product Detail Page Activity
 *
 * @see AppCompatActivity
 */
public class ProductDetailsPageActivity extends ToolBarActivity {
    private Product productBySearch;

    /**
     * Provide logic when activity created. Mapping field, setting and resizing images.
     *
     * @param savedInstanceState
     */
    //break this method for few less
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_product_page);
        super.onCreate(savedInstanceState);

        TextView tvProductName = (TextView) findViewById(R.id.tvProductName);
        ImageView ivProductImage = (ImageView) findViewById(R.id.ivProductImage);
        TextView tvProductPrice = (TextView) findViewById(R.id.tvProductPrice);
        ImageView ivRelated1 = (ImageView) findViewById(R.id.ivRelated1);
        ImageView ivRelated2 = (ImageView) findViewById(R.id.ivRelated2);
        ImageView ivRelated3 = (ImageView) findViewById(R.id.ivRelated3);
        ImageView ivRelated4 = (ImageView) findViewById(R.id.ivRelated4);
        Spinner sSize = (Spinner) findViewById(R.id.sSize);

        final Bundle extra = getIntent().getExtras();
        if (extra != null) {
            productBySearch = extra.getParcelable(SearchActivity.PRODUCT);
        }

        tvProductName.setText(productBySearch.getProductName());
        tvProductPrice.setText("" + productBySearch.getProductPrice());
        Picasso.with(this)
                .load(productBySearch.getProductImage())
                .resize(500, 500)
                .centerCrop()
                .into(ivProductImage);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.size, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sSize.setAdapter(adapter);

        //aaaaaa, what's that?
        //it's magic, don't touch
        Picasso.with(this)
                .load(getIntent().getExtras().getString("productImage"))
                .resize(80, 80)
                .centerCrop()
                .into(ivRelated1);
        Picasso.with(this)
                .load(getIntent().getExtras().getString("productImage"))
                .resize(80, 80)
                .centerCrop()
                .into(ivRelated2);
        Picasso.with(this)
                .load(getIntent().getExtras().getString("productImage"))
                .resize(80, 80)
                .centerCrop()
                .into(ivRelated3);
        Picasso.with(this)
                .load(getIntent().getExtras().getString("productImage"))
                .resize(80, 80)
                .centerCrop()
                .into(ivRelated4);
    }
}