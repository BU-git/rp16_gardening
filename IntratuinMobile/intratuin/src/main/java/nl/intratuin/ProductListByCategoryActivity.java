package nl.intratuin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import nl.intratuin.dto.Product;
import nl.intratuin.dto.TreeNode;
import nl.intratuin.handlers.CategoryListAdapter;
import nl.intratuin.handlers.ProductListAdapter;
import nl.intratuin.manager.RequestResponseManager;
import nl.intratuin.net.UriConstructor;
import nl.intratuin.settings.ToolBarActivity;

/**
 * The class {@code ProductListByCategoryActivity} is used to provide Product list logic
 *
 * @see AppCompatActivity
 */
public class ProductListByCategoryActivity extends ToolBarActivity{
    private ProductListAdapter productListAdapter;

    private ListView subCategoryListView;
    private ListView productByCategoryListView;
    private TreeNode category;

    /**
     * Provide logic when activity created. Mapping field, getting product by categories.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_product_list);
        super.onCreate(savedInstanceState);

        subCategoryListView = (ListView) findViewById(R.id.subcategory_listView);
        productByCategoryListView = (ListView) findViewById(R.id.productOfCategory_listView);

        final Bundle extraCategory = getIntent().getExtras();
        if (extraCategory != null) {
            category = extraCategory.getParcelable(SearchActivity.TREENODE);
        }

        generateProductListByCategory("" + category.getId());

        CategoryListAdapter subCategoryAdapter = new CategoryListAdapter(this, category.getChildren());
        subCategoryListView.setAdapter(subCategoryAdapter);
        subCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TreeNode subCategory = (TreeNode) parent.getItemAtPosition(position);
                Intent productListOfCategoryIntent = new Intent(ProductListByCategoryActivity.this, ProductListByCategoryActivity.class);
                productListOfCategoryIntent.putExtra(SearchActivity.TREENODE, subCategory);
                startActivity(productListOfCategoryIntent);
            }
        });
    }

    /**
     * Returns name of sub-TreeNodes
     *
     * @param treeNode
     * @return name of subcategories
     */
    private String[] getChildrenName(TreeNode treeNode) {
        List<TreeNode> children = treeNode.getChildren();
        String[] childrenName = new String[children.size()];
        if (children != null) {
            for (int index = 0; index < children.size(); index++) {
                childrenName[index] = children.get(index).getName();
            }
            return childrenName;
        }
        return null;
    }

    /**
     * Generating product list in specific category
     *
     * @param idCategory
     */
    private void generateProductListByCategory(String idCategory) {
        String uri = new UriConstructor(this).makeURI("productsInCategory").toString() + "{idCategory}";
        RequestResponseManager<Product[]> managerLoader = new RequestResponseManager<>(this, App.getShowManager(), Product[].class);
        List<Product> allProductByCategory = Arrays.asList(managerLoader.loaderFromWebService(uri, idCategory));
        if (allProductByCategory != null) {
            ProductListAdapter productListAdapter = new ProductListAdapter(ProductListByCategoryActivity.this, allProductByCategory);
            productByCategoryListView.setAdapter(productListAdapter);
            productByCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Product product = (Product) parent.getItemAtPosition(position);

                    Intent productPageIntent = new Intent(ProductListByCategoryActivity.this, ProductDetailsPageActivity.class);
                    productPageIntent.putExtra(SearchActivity.PRODUCT, product);
                    startActivity(productPageIntent);
                }
            });
        }
    }
}