package nl.intratuin;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import nl.intratuin.db.DBHelper;
import nl.intratuin.db.contract.ProductContract;
import nl.intratuin.dto.Product;

/**
 * The class {@code ProductDetailsPageActivity} is used to provide logic on Product Detail Page Activity
 *
 * @see AppCompatActivity
 */
public class ProductDetailsPageActivity extends ToolBarActivity {
    private Product productBySearch;

    private TextView tvProductName, tvProductPrice;
    private ImageView ivProductImage;

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private ContentValues values;
    private String currentDate;

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

        initComponents();

        final Bundle extra = getIntent().getExtras();
        if (extra != null) {
            productBySearch = extra.getParcelable(SearchActivity.PRODUCT);
        }

        if(productBySearch != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            currentDate = simpleDateFormat.format(new Date()).toString();
            Cursor cursor = sqLiteDatabase.query(ProductContract.ProductEntity.TABLE_NAME,
                    new String[]{ProductContract.ProductEntity._ID},
                    ProductContract.ProductEntity.PRODUCT_ID + " = ? AND " +
                            ProductContract.ProductEntity.DATE + "= ? AND " + ProductContract.ProductEntity.CUSTOMER_NAME + "= ?",
                    new String[]{"" + productBySearch.getProductId(), currentDate, ToolBarActivity.profileItem},
                    null, null, null);
            if (cursor.getCount() == 0) {
                cursor = sqLiteDatabase.query(ProductContract.ProductEntity.TABLE_NAME,
                        new String[]{ProductContract.ProductEntity._ID},
                        ProductContract.ProductEntity.PRODUCT_ID + " = ? AND " + ProductContract.ProductEntity.CUSTOMER_NAME + "= ?",
                        new String[]{"" + productBySearch.getProductId(), ToolBarActivity.profileItem},
                        null, null, null);
                if (cursor.getCount() != 0) {
                    int id = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntity._ID));
                    updateInTable(id);
                } else
                    insertInTable();
            }
            dbHelper.close();
        }

        tvProductName.setText(productBySearch.getProductName());
        tvProductPrice.setText("" + productBySearch.getProductPrice());
        Picasso.with(this)
                .load(productBySearch.getProductImage())
                .resize(400, 400)
                .centerCrop()
                .into(ivProductImage);

    }

    private void insertInTable() {
        values.put(ProductContract.ProductEntity.CUSTOMER_NAME, ToolBarActivity.profileItem);
        values.put(ProductContract.ProductEntity.PRODUCT_ID, productBySearch.getProductId());
        values.put(ProductContract.ProductEntity.NAME, productBySearch.getProductName());
        values.put(ProductContract.ProductEntity.PRICE, productBySearch.getProductPrice());
        values.put(ProductContract.ProductEntity.IMAGE, productBySearch.getProductImage());
        values.put(ProductContract.ProductEntity.CATEGORY_ID, productBySearch.getCategoryId());
        values.put(ProductContract.ProductEntity.BARCODE, productBySearch.getBarcode());
        values.put(ProductContract.ProductEntity.STATUS, "review");
        values.put(ProductContract.ProductEntity.DATE, currentDate);

        sqLiteDatabase.insert(ProductContract.ProductEntity.TABLE_NAME, null, values);
    }

    private void initComponents() {
        tvProductName = (TextView) findViewById(R.id.tvProductName);
        ivProductImage = (ImageView) findViewById(R.id.ivProductImage);
        tvProductPrice = (TextView) findViewById(R.id.tvProductPrice);

        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        values = new ContentValues();
    }

    private void updateInTable(int id) {
        values.put(ProductContract.ProductEntity.DATE, currentDate);
        sqLiteDatabase.update(ProductContract.ProductEntity.TABLE_NAME,
                values,
                "ProductContract.ProductEntity._ID = ?",
                new String[]{"" + id});
    }
}