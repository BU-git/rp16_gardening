package com.intratuin.testmarket.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    Integer orderId;

    @Column(name = "CUSTOMER_ID")
    @ManyToOne
    @JoinColumn(name = "customerId")
    Customer customer;

    @Column(name = "ORDER_RULE_ID")
    @ManyToOne
    @JoinColumn(name = "orderRuleId")
    OrderRule orderRule;

    @Column(name = "ORDER_DATE")
    Date orderDate;

    public Order() {
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OrderRule getOrderRule() {
        return orderRule;
    }

    public void setOrderRule(OrderRule orderRule) {
        this.orderRule = orderRule;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
