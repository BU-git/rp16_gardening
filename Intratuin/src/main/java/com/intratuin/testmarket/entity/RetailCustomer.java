package com.intratuin.testmarket.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RETAIL_CUSTOMER")
public class RetailCustomer {

    @Column(name = "RETAIL_ID")//TODO: connections between tables
    Integer retailId;//TODO: id or entity(id prefered)

    @Column(name = "CUSTOMER_ID")
    Integer customerId;


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
