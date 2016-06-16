package nl.intratuin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import nl.intratuin.dto.Product;
import nl.intratuin.handlers.CartAdapter;
import nl.intratuin.handlers.ShoppingCartHelper;

public class ShoppingCartActivity extends ToolBarActivity {

    private List<Product> cartList;
    private CartAdapter productAdapter;
    private Button bCheckout;
    private ListView listViewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopping_cart);
        super.onCreate(savedInstanceState);

        cartList = ShoppingCartHelper.getCart();

        listViewProduct = (ListView) findViewById(R.id.ListViewProduct);
        productAdapter = new CartAdapter(this, cartList);
        listViewProduct.setAdapter(productAdapter);

        bCheckout = (Button) findViewById(R.id.checkout);
        bCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShoppingCartActivity.this, OrderDataActivity.class));
            }
        });
    }
}
