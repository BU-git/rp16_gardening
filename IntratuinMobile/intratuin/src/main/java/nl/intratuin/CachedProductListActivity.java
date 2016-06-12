package nl.intratuin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.List;

import nl.intratuin.dto.CachedProduct;
import nl.intratuin.dto.Product;
import nl.intratuin.handlers.MovieAdapter;
import nl.intratuin.handlers.MovieTouchHelper;

public class CachedProductListActivity extends ToolBarActivity{
        private List<CachedProduct> cachedProductList;
        private Product product;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            setContentView(R.layout.activity_cached_product_list);
            super.onCreate(savedInstanceState);
            RecyclerView movieRecyclerView = (RecyclerView) findViewById(R.id.movie_recycler_view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            movieRecyclerView.setLayoutManager(linearLayoutManager);

            final Bundle extra = getIntent().getExtras();
            if (extra != null) {
                cachedProductList = extra.getParcelableArrayList(ToolBarActivity.CACHEDPRODUCTS);
            }

            if(cachedProductList != null) {
                MovieAdapter movieAdapter = new MovieAdapter(this, cachedProductList, new MovieAdapter.OnItemClickListener() {
                    @Override public void onItemClick(CachedProduct cachedProduct) {
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
                movieRecyclerView.setAdapter(movieAdapter);

                // Setup ItemTouchHelper
                ItemTouchHelper.Callback callback = new MovieTouchHelper(movieAdapter);
                ItemTouchHelper helper = new ItemTouchHelper(callback);
                helper.attachToRecyclerView(movieRecyclerView);
            }
        }
}
