package nl.intratuin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import nl.intratuin.dto.Product;
import nl.intratuin.dto.TreeNode;
import nl.intratuin.handlers.ProductListAdapter;
import nl.intratuin.handlers.RequestResponseManager;
import nl.intratuin.net.UriConstructor;

/**
 * The class {@code ProductListByCategoryActivity} is used to provide Product list logic
 *
 * @see AppCompatActivity
 */
public class ProductListByCategoryActivity extends AppCompatActivity {
    private ListView subCategoryListView;
    private ListView productByCategoryListView;
    private TreeNode category;
    private String[] childrenName;


    /**
     * Provide logic when activity created. Mapping field, getting product by categories.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        subCategoryListView = (ListView) findViewById(R.id.subcategory_listView);
        productByCategoryListView = (ListView) findViewById(R.id.productOfCategory_listView);

        final Bundle extraCategory = getIntent().getExtras();
        if (extraCategory != null) {
            category = extraCategory.getParcelable(SearchActivity.TREENODE);
        }

        generateProductListByCategory("" + category.getId());

        childrenName = getChildrenName(category);
        if (childrenName != null) {
            ArrayAdapter<String> subCategoryAdapter = new ArrayAdapter<>(this, R.layout.activity_subcategory, R.id.bsubCategory, childrenName);
            subCategoryListView.setAdapter(subCategoryAdapter);
        }
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
        }
    }
}