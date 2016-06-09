package nl.intratuin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import nl.intratuin.dto.CachedProduct;
import nl.intratuin.dto.Product;
import nl.intratuin.handlers.CachedProductListAdapter;

public class CachedProductListActivity extends ToolBarActivity{
        private CachedProductListAdapter adapter;

        private ListView cachedProductListView;
        private List<CachedProduct> cachedProductList;
        private Product product;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            setContentView(R.layout.activity_cached_product_list);
            super.onCreate(savedInstanceState);

            cachedProductListView = (ListView) findViewById(R.id.cachedProduct_listView);

            final Bundle extra = getIntent().getExtras();
            if (extra != null) {
                cachedProductList = extra.getParcelableArrayList(ToolBarActivity.CACHEDPRODUCTS);
            }

            if(cachedProductList != null) {
                CachedProductListAdapter adapter = new CachedProductListAdapter(this, cachedProductList);
                cachedProductListView.setAdapter(adapter);
                cachedProductListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CachedProduct cachedProduct = (CachedProduct) parent.getItemAtPosition(position);

                        product = new Product();
                        product.setProductId(cachedProduct.getProductId());
                        product.setProductName(cachedProduct.getProductName());
                        product.setProductPrice(cachedProduct.getProductPrice());
                        product.setProductImage(cachedProduct.getProductImage());
                        product.setBarcode(cachedProduct.getBarcode());

                        Intent productDetailIntent = new Intent(CachedProductListActivity.this, ProductDetailsPageActivity.class);
                        productDetailIntent.putExtra(SearchActivity.PRODUCT, product);
                        startActivity(productDetailIntent);
                    }
                });
            }
        }
}
