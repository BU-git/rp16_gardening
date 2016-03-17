package com.intratuin.testmarket.entity;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_RULE")
public class OrderRule {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_RULE_ID")
    Integer orderRuleId;

    @Column(name = "PRODUCT_ID")
    Integer productId;

    @Column(name = "AMOUNT")
    Integer amount;

    @Column(name = "TOTAL")
    Integer total;

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
