package nl.intratuin;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.intratuin.dto.Category;
import nl.intratuin.dto.Product;
import nl.intratuin.dto.TreeNode;
import nl.intratuin.handlers.HierarchyCategoryAdapter;
import nl.intratuin.handlers.ManagerLoader;
import nl.intratuin.handlers.ProductAutoCompleteAdapter;
import nl.intratuin.net.UriConstructor;

public class SearchActivity extends AppCompatActivity{
    private HierarchyCategoryAdapter categoryAdapter;
    private ListView categoryListView;
    private List<TreeNode> treeCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_search);

        categoryListView = (ListView) findViewById(R.id.categoryListView);

        treeCategory = generateCategoryHierarchy();

        categoryAdapter = new HierarchyCategoryAdapter(this, treeCategory);
        categoryListView.setAdapter(categoryAdapter);

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                TreeNode treeNode = (TreeNode) adapter.getItemAtPosition(position);
                Intent productListOfCategoryIntent = new Intent(SearchActivity.this, ProductListOfCategogyActivity.class);

                List<TreeNode> children = treeNode.getChildren();
//                if(children != null) {
//                }
                startActivity(productListOfCategoryIntent);
            }
        });

        ProductAutoCompleteAdapter searchAdapter = new ProductAutoCompleteAdapter(this);
        AutoCompleteTextView autoCompleteTV = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autoCompleteTV.setThreshold(3);
        autoCompleteTV.setAdapter(searchAdapter);
        autoCompleteTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) parent.getItemAtPosition(position);

                Intent productPageIntent = new Intent(SearchActivity.this, ProductDetailsPageActivity.class);
                productPageIntent.putExtra("productName", product.getProductName());
                productPageIntent.putExtra("productPrice", product.getProductPrice());
                productPageIntent.putExtra("productImage", product.getProductImage());
                startActivity(productPageIntent);
            }
        });
    }

    private List<TreeNode> generateCategoryHierarchy() {
        String uri = new UriConstructor(((FragmentActivity) this).getSupportFragmentManager())
                .makeFullURI("/category").toString() + "/all";
        ManagerLoader managerLoader = new ManagerLoader(this);
        List<Category> allCategory = managerLoader.loaderFromWebService(uri, null);

        return buildArrTreeNode(allCategory, 0);
    }

    public List<TreeNode> buildArrTreeNode(List<Category> categoryList, int id) {
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Category category : categoryList) {
            if (category.getParentId() == id) {
                TreeNode treeNode = new TreeNode(category.getCategoryId(), category.getName());
                treeNode.setChildren(buildArrTreeNode(categoryList, treeNode.getId()));
                treeNodes.add(treeNode);
            }
        }
        return treeNodes;
    }
}
