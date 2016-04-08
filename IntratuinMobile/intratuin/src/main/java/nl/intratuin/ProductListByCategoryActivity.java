package nl.intratuin;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import nl.intratuin.dto.Product;
import nl.intratuin.dto.TreeNode;
import nl.intratuin.handlers.ManagerLoader;
import nl.intratuin.handlers.ProductListAdapter;
import nl.intratuin.net.UriConstructor;

public class ProductListByCategoryActivity extends AppCompatActivity {
    ListView subCategoryListView;
    ListView productByCategoryListView;
    List<Product> allProductByCategory;
    TreeNode category;
    String[] childrenName;

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

        childrenName = getChildrenName(category);
        if(childrenName != null) {
            ArrayAdapter<String> subCategoryAdapter = new ArrayAdapter<String>(this, R.layout.activity_subcategory, R.id.bsubCategory, childrenName);
            subCategoryListView.setAdapter(subCategoryAdapter);
        }

        String idCategory = "" + category.getId();
        allProductByCategory = generateProductListByCategory(idCategory);
        ProductListAdapter productListAdapter = new ProductListAdapter(this, allProductByCategory);
        productByCategoryListView.setAdapter(productListAdapter);
    }

    private String[] getChildrenName(TreeNode treeNode) {
        List<TreeNode> children = treeNode.getChildren();
        String[] childrenName = new String[children.size()];
        if (children != null) {
            for (int index = 0; index < children.size(); index++) {
                childrenName[index] = children.get(index).getName();
            }
            return childrenName;
        }
        return  null;
    }

    private List<Product> generateProductListByCategory(String idCategory) {
        String uri = new UriConstructor(((FragmentActivity) this).getSupportFragmentManager())
                .makeFullURI("/product").toString() + "/list/byCategory/{idCategory}";
        ManagerLoader managerLoader = new ManagerLoader(this, Product[].class);
        List<Product> allProductByCategory = managerLoader.loaderFromWebService(uri, idCategory);

        return allProductByCategory;
    }
}