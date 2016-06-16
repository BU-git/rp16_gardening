package nl.intratuin.handlers;

import java.util.ArrayList;
import java.util.List;

import nl.intratuin.dto.Product;

public class ShoppingCartHelper {
    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static List<Product> cart;

    public static List<Product> getCart() {
        if(cart == null) {
            cart = new ArrayList<>();
        }
        return cart;
    }
}
