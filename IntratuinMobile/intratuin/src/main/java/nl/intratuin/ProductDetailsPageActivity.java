package nl.intratuin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
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
        ImageView ivRelated1 = (ImageView) findViewById(R.id.ivRelated1);
        ImageView ivRelated2 = (ImageView) findViewById(R.id.ivRelated2);
        ImageView ivRelated3 = (ImageView) findViewById(R.id.ivRelated3);
        ImageView ivRelated4 = (ImageView) findViewById(R.id.ivRelated4);
        Spinner sSize = (Spinner) findViewById(R.id.sSize);

        tvProductName.setText(getIntent().getExtras().getString("productName"));
        tvProductPrice.setText("" + getIntent().getExtras().getDouble("productPrice"));
        Picasso.with(this)
                .load(getIntent().getExtras().getString("productImage"))
                .resize(500, 500)
                .centerCrop()
                .into(ivProductImage);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.size, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sSize.setAdapter(adapter);

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