package com.intratuin.testmarket.entity;

import javax.persistence.*;

@Entity
@Table(name = "RETAIL_CUSTOMER")
public class RetailCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RC_ID")
    private int id;

    @Column(name = "RETAIL_ID")//TODO: connections between tables
    Integer retailId;//TODO: id or entity(id prefered)

    @Column(name = "CUSTOMER_ID")
    Integer customerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getRetailId() {
        return retailId;
    }

    public void setRetailId(Integer retailId) {
        this.retailId = retailId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
