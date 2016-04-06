package nl.intratuin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProductDetailsPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        getSupportActionBar().hide();

        TextView tvProductName = (TextView) findViewById(R.id.tvProductName);
        ImageView ivProductImage = (ImageView) findViewById(R.id.ivProductImage);
        TextView tvProductPrice = (TextView) findViewById(R.id.tvProductPrice);

        tvProductName.setText(getIntent().getExtras().getString("productName"));
        tvProductPrice.setText("" + getIntent().getExtras().getDouble("productPrice"));
        Picasso.with(this)
                .load(getIntent().getExtras().getString("productImage"))
                .resize(500, 500)
                .centerCrop()
                .into(ivProductImage);
    }
}