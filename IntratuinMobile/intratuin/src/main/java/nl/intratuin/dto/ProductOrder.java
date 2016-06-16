package nl.intratuin.dto;

import java.util.List;
import java.util.UUID;

public class ProductOrder {
    private UUID valueRandom;
    private byte[] publicKey;
    private List<Product> productList;

    public ProductOrder(byte[] publicKey, List<Product> productList) {
        valueRandom = UUID.randomUUID();
        this.publicKey = publicKey;
        this.productList = productList;
    }

    public UUID getValueRandom() {
        return valueRandom;
    }

    public void setValueRandom(UUID valueRandom) {
        this.valueRandom = valueRandom;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
