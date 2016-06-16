package nl.intratuin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nl.intratuin.db.DBHelper;
import nl.intratuin.db.contract.ProductContract;
import nl.intratuin.dto.CachedProduct;
import nl.intratuin.dto.Customer;
import nl.intratuin.manager.AuthManager;
import nl.intratuin.manager.RequestResponseManager;
import nl.intratuin.settings.Mainscreen;
import nl.intratuin.settings.Settings;


public class ToolBarActivity extends AppCompatActivity {
    protected static String profileItem = "Profile";
    protected static final String CUSTOMER = "customer";
    public static final String CACHEDPRODUCTS = "cashed product";
    Toolbar toolBar;
    MenuItem item;
    ImageView toolbarTitle;

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    private List<CachedProduct> cachedProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(null);
        initToolBar();

        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        cachedProductList = new ArrayList<>();
    }

    private void initToolBar() {
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        toolbarTitle = (ImageView) findViewById(R.id.toolbarTitle);
        toolbarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToolBarActivity.this, SearchActivity.class));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        item = menu.findItem(R.id.profile);
        item.setTitle(profileItem);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.profile):
                toCustomerProfile(this);
                break;
            case (R.id.logout): {
                ToolBarActivity.this.getSharedPreferences(AuthManager.PREF_FILENAME, Context.MODE_PRIVATE)
                        .edit()
                        .clear()
                        .commit();
                Intent logoutIntent = new Intent(ToolBarActivity.this, LoginActivity.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutIntent);
                break;
            }
            case (R.id.viewedProducts): {
                Cursor cursor = sqLiteDatabase.query(ProductContract.ProductEntity.TABLE_NAME,
                        null,
                        ProductContract.ProductEntity.CUSTOMER_NAME + " = ? AND " + ProductContract.ProductEntity.STATUS + "= ?",
                        new String[]{profileItem, "review"},
                        null, null, null);
                if (cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            CachedProduct cachedProduct = new CachedProduct();
                            cachedProduct.setProductId(cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntity.PRODUCT_ID)));
                            cachedProduct.setProductName(cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntity.NAME)));
                            cachedProduct.setProductImage(cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntity.IMAGE)));
                            cachedProduct.setCachedDate(cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntity.DATE)));
                            cachedProduct.setProductPrice(cursor.getDouble(cursor.getColumnIndex(ProductContract.ProductEntity.PRICE)));
                            cachedProduct.setBarcode(cursor.getLong(cursor.getColumnIndex(ProductContract.ProductEntity.BARCODE)));

                            cachedProductList.add(cachedProduct);
                        } while (cursor.moveToNext());
                    }
                    cursor.close();

                    Intent cashedProductList = new Intent(this, CachedProductListActivity.class);
                    cashedProductList.putExtra(CACHEDPRODUCTS, (ArrayList) cachedProductList);
                    startActivity(cashedProductList);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("List of viewed products is empty")
                            .setNegativeButton("ОК",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                dbHelper.close();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public static void toCustomerProfile(Context context) {
        String userInfoUri = Settings.getUriConfig().getCustomerByToken().toString() + ("{token}");
        RequestResponseManager<Customer> managerLoader = new RequestResponseManager<>(context, App.getShowManager(), Customer.class);
        Customer customerByAccessToken = managerLoader.loaderFromWebService(userInfoUri, AuthManager.access_token);

        Intent profilePageIntent = new Intent(context, ProfileActivity.class);
        profilePageIntent.putExtra(CUSTOMER, customerByAccessToken);
        context.startActivity(profilePageIntent);
    }

}