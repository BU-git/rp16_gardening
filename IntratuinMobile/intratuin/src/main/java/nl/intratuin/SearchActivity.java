package nl.intratuin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.intratuin.dto.Category;
import nl.intratuin.dto.Product;
import nl.intratuin.dto.TreeNode;
import nl.intratuin.handlers.ErrorFragment;
import nl.intratuin.handlers.HierarchyCategoryAdapter;
import nl.intratuin.handlers.ProductAutoCompleteAdapter;
import nl.intratuin.handlers.RequestResponseManager;
import nl.intratuin.net.UriConstructor;

/**
 * The class {@code SearchActivity} is used to provide logic on Search Activity
 * Activity, where user can search products
 *
 * @see AppCompatActivity
 * @see OnClickListener
 */
public class SearchActivity extends AppCompatActivity implements OnClickListener {
    /**
     * The constant PRODUCT_SEARCH is used as a key to pass Product parameter to {@code SearchActivity}
     */
    public static final String PRODUCT_SEARCH = "productSearch";
    /**
     * The constant TREENODE is used as a key to pass TreeNode parameter to {@code ProductListByCategoryActivity}
     */
    public static final String TREENODE = "TreeNode";

    private String access_token;

    private HierarchyCategoryAdapter categoryAdapter;
    private ListView categoryListView;
    private List<TreeNode> treeCategory;

    private ImageButton ibBarcode;
    private ImageButton ibMan;
    private ImageButton ibBusket;

    /**
     * Provide logic when activity created. Mapping field, configuring AutoCompleteTextView, showing user info.
     *
     * @param savedInstanceState
     */
    //break this method for few less
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle extra = getIntent().getExtras();
        if (extra != null) {
            access_token = extra.getString(LoginActivity.ACCESS_TOKEN);
        }
        if (access_token != null) {
            getSupportActionBar().hide();
            setContentView(R.layout.activity_search);

            ibBarcode = (ImageButton) findViewById(R.id.ibBarcode);
            ibMan = (ImageButton) findViewById(R.id.ibMan);
            ibBusket = (ImageButton) findViewById(R.id.ibBusket);
            categoryListView = (ListView) findViewById(R.id.categoryListView);

            ibBarcode.setOnClickListener(this);
            ibMan.setOnClickListener(this);
            ibBusket.setOnClickListener(this);

            treeCategory = generateCategoryHierarchy();//all categories
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

            //show user login
            //???
            String name = "anonymous";
            try {
                String userInfoUri = new UriConstructor(SearchActivity.this).makeURI("userInfo").toString();
                userInfoUri += "?access_token={access_token}";
                if (userInfoUri != null) {
                    RequestResponseManager<String> managerLoader = new RequestResponseManager(this, App.getShowManager(), String.class);
                    String jsonRespond = managerLoader.loaderFromWebService(userInfoUri, access_token);
                    JSONObject response = new JSONObject(jsonRespond);
                    if (response != null && response.has("user_id")) {
                        //Toast.makeText(WebActivity.this, "Customer: " + response.getString("name"), Toast.LENGTH_LONG).show();
                        if (response.has("name") && response.getString("name").length() > 0)
                            name = response.getString("name");
                        else
                            name = response.getString("client_id");//name = response.getString("client_id");
                    } else {
                        String errorStr;
                        if (response == null)
                            errorStr = "Error! Null response!";
                        else
                            errorStr = "Error" + response.getString("code") + ": " + response.getString("error") + ": " + response.getString("error_description");
                        ErrorFragment ef = ErrorFragment.newError(errorStr);
                        ef.show(getSupportFragmentManager(), "Intratuin");
                        startActivity(new Intent(SearchActivity.this, LoginActivity.class));
                    }
                }
            } catch (JSONException e) {
                ErrorFragment ef = ErrorFragment.newError("Can't get user info!");
                ef.show(getSupportFragmentManager(), "Intratuin");
                startActivity(new Intent(SearchActivity.this, LoginActivity.class));
            }

            Toast.makeText(this, "Logged as " + name, Toast.LENGTH_LONG).show();
        } else {
            ErrorFragment ef = ErrorFragment.newError("No access token found!");
            ef.show(getSupportFragmentManager(), "Intratuin");
            startActivity(new Intent(SearchActivity.this, LoginActivity.class));
        }
    }

    /**
     * Generate hierarchy of categories
     * @return the list of Category as TreeNode
     */
    private List<TreeNode> generateCategoryHierarchy() {
        URI uri = new UriConstructor(this).makeURI("categoryList");

        RequestResponseManager<Category[]> managerLoader = new RequestResponseManager(this, App.getShowManager(), Category[].class);
        List<Category> allCategory = Arrays.asList(managerLoader.loaderFromWebService(uri.toString(), null));

        return buildArrTreeNode(allCategory, 0);
    }

    /**
     * Build category list on current tree level.
     *
     * @param categoryList the category list
     * @param id           the tree level
     * @return the list of Category as TreeNode
     */
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

    /**
     * Provide logic for clicking buttons.
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibBarcode:
                Intent scannerIntent = new Intent(this, ScannerActivity.class);
                startActivity(scannerIntent);
                break;

            case R.id.ibMan:
                break;

            case R.id.ibBusket:
                break;
        }
    }
}

