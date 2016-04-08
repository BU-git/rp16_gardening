package nl.intratuin;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import nl.intratuin.dto.Category;
import nl.intratuin.dto.Product;
import nl.intratuin.dto.TreeNode;
import nl.intratuin.handlers.HierarchyCategoryAdapter;
import nl.intratuin.handlers.ManagerLoader;
import nl.intratuin.handlers.ProductAutoCompleteAdapter;
import nl.intratuin.net.UriConstructor;

public class SearchActivity extends AppCompatActivity {
    public static final String PRODUCT_SEARCH = "productSearch";
    public static final String TREENODE = "TreeNode";
    HierarchyCategoryAdapter categoryAdapter;
    ListView categoryListView;
    List<TreeNode> treeCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();

        setContentView(R.layout.activity_search);

        treeCategory = generateCategoryHierarchy();
        categoryListView = (ListView) findViewById(R.id.categoryListView);

        categoryAdapter = new HierarchyCategoryAdapter(this, treeCategory);
        categoryListView.setAdapter(categoryAdapter);

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                categoryAdapter.clickOnCategory(position);
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
                productPageIntent.putExtra(PRODUCT_SEARCH, product);
                startActivity(productPageIntent);
            }
        });
    }

    private List<TreeNode> generateCategoryHierarchy() {
        String uri = new UriConstructor(((FragmentActivity) this).getSupportFragmentManager())
                .makeFullURI("/category").toString() + "/all";
        ManagerLoader managerLoader = new ManagerLoader(this, Category[].class);
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
