package nl.intratuin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.intratuin.dto.Category;
import nl.intratuin.dto.Product;
import nl.intratuin.dto.TreeNode;
import nl.intratuin.handlers.HierarchyCategoryAdapter;
import nl.intratuin.handlers.ProductAutoCompleteAdapter;
import nl.intratuin.handlers.RequestResponseManager;
import nl.intratuin.net.UriConstructor;

public class SearchActivity extends AppCompatActivity implements OnClickListener {
    public static final String PRODUCT_SEARCH = "productSearch";
    public static final String TREENODE = "TreeNode";

    private HierarchyCategoryAdapter categoryAdapter;
    private ListView categoryListView;
    private List<TreeNode> treeCategory;

    private ImageButton ibBarcode;
    private ImageButton ibMan;
    private ImageButton ibBusket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
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
    }

    private List<TreeNode> generateCategoryHierarchy() {
        String uri = new UriConstructor(((FragmentActivity) this).getSupportFragmentManager())
                .makeFullURI("/category").toString() + "/all";
//            String uri = BuildConfig.API_HOST + "category/all";
        RequestResponseManager<Category[]> managerLoader = new RequestResponseManager(this, Category[].class);
        List<Category> allCategory = Arrays.asList(managerLoader.loaderFromWebService(uri, null));

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibBarcode:
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);
                break;

            case R.id.ibMan:

                break;

            case R.id.ibBusket:

                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                // Handle successful scan
                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
                Toast toast = Toast.makeText(this, "Scan was Cancelled!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();

            }
        }
    }
}

