package com.intratuin.testmarket.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    Integer productId;

    @Column(name = "PRODUCT_NAМE")
    String productName;

    @Column(name = "PRODUCT_PRICE")//TODO:BigDecimal
    int productPrice;

    /*
//    @OneToMany(mappedBy = "product")
    private Set<OrderRule> orderRules = new HashSet<>();

    public Product() {
    }

    public Set<OrderRule> getOrderRules() {
        return orderRules;
    }

    public void setOrderRules(Set<OrderRule> orderRules) {
        this.orderRules = orderRules;
    }*/

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }
}
