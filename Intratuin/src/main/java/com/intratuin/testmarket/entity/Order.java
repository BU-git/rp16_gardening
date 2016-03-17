package com.intratuin.testmarket.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    Integer orderId;

    @Column(name = "CUSTOMER_ID")
    Integer customerId;

    @Column(name = "ORDER_RULE_ID")
    Integer orderRuleId;

    @Column(name = "ORDER_DATE")
    Date orderDate;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getOrderRuleId() {
        return orderRuleId;
    }

    public void setOrderRuleId(Integer orderRuleId) {
        this.orderRuleId = orderRuleId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
