package nl.intratuin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nl.intratuin.dto.Product;

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
        TextView tvPrice = (TextView) findViewById(R.id.tvPrice);

        final Bundle extra = getIntent().getExtras();
        if (extra != null) {
            productBySearch = extra.getParcelable(SearchActivity.PRODUCT);
        }

        tvProductName.setText(productBySearch.getProductName());
        tvProductPrice.setText("" + productBySearch.getProductPrice());
        Picasso.with(this)
                .load(productBySearch.getProductImage())
                .resize(300, 300)
                .centerCrop()
                .into(ivProductImage);

    }
}