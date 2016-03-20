package com.intratuin.testmarket.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ORDER_RULE")
public class OrderRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_RULE_ID")
    Integer orderRuleId;

    @Column(name = "PRODUCT_ID")
    Integer productId;

    @Column(name = "AMOUNT")
    Integer amount;

    @Column(name = "TOTAL")
    Integer total;

//    @OneToMany(mappedBy = "orderRule")
    private Set<Order> orders = new HashSet<>();

//    @ManyToOne
//    @JoinColumn(name = "productId")
    private Product product;

    public OrderRule() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Integer getOrderRuleId() {
        return orderRuleId;
    }

    public void setOrderRuleId(Integer orderRuleId) {
        this.orderRuleId = orderRuleId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
