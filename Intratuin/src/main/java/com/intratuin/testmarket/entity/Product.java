package com.intratuin.testmarket.entity;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue//TODO:Strategy
    @Column(name = "PRODUCT_ID")
    Integer productId;

    @Column(name = "PRODUCT_NAМE")
    String productName;

    @Column(name = "PRODUCT_PRICE")//TODO:BigDecimal
    Double productPrice;

    //TODO: constructors(include empty), if needed

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

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
}
