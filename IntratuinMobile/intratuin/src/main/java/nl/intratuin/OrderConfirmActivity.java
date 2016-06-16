package nl.intratuin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import nl.intratuin.dto.Customer;
import nl.intratuin.dto.Product;
import nl.intratuin.handlers.OrderProductAdapter;
import nl.intratuin.handlers.ShoppingCartHelper;
import nl.intratuin.manager.AuthManager;

public class OrderConfirmActivity extends ToolBarActivity implements View.OnClickListener{
    private TextView customerName;
    private TextView customerEmail;
    private TextView customerPhone;
    private TextView totalAmount;
    private ListView ListViewProduct;

    private Button bConfirm;
    private Customer customer;
    private List<Product> orderedProductList;
    private OrderProductAdapter orderedProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.order_confirm);
        super.onCreate(savedInstanceState);

        initComponents();
        fillActivity(customer);

        orderedProductAdapter = new OrderProductAdapter(this, orderedProductList);
        ListViewProduct.setAdapter(orderedProductAdapter);
    }

    private void initComponents() {
        customer = AuthManager.getCustomer(this);
        orderedProductList = ShoppingCartHelper.getCart();

        customerName = (TextView) findViewById(R.id.name);
        customerEmail = (TextView) findViewById(R.id.email);
        customerPhone = (TextView) findViewById(R.id.phone);
        totalAmount = (TextView) findViewById(R.id.totalAmount);
        ListViewProduct = (ListView) findViewById(R.id.ListViewProduct);

        bConfirm = (Button) findViewById(R.id.bConfirm);

        bConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your order is being processed. Our operator will contact you.")
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                orderedProductList.clear();
                                startActivity(new Intent(OrderConfirmActivity.this, SearchActivity.class));
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void fillActivity(Customer customer) {
        String fullName = customer.getFirstName() + " " + customer.getLastName();
        String email = customer.getEmail();
        String phone = customer.getPhoneNumber();

        customerName.setText(fullName);
        customerEmail.setText("email: " + email);
        customerPhone.setText("phone number: " + phone);
        totalAmount.setText("Total amount:   " + "€  " + getTotalAmount());
    }

    public double getTotalAmount() {
        double totalAmount = 0;
        for(Product product : orderedProductList) {
            totalAmount += product.getProductPrice();
        }
        return totalAmount;
    }
}
