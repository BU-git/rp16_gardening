package nl.intratuin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nl.intratuin.dto.TreeNode;

public class ProductListOfCategogyActivity extends AppCompatActivity {
    ListView subCategoryListView;
    ListView productOfCategoryListView;
    List<TreeNode> subCategory;
    ArrayList<String> subCategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        subCategoryListView = (ListView) findViewById(R.id.subcategory_listView);
        productOfCategoryListView = (ListView) findViewById(R.id.productOfCategory_listView);

    }
}