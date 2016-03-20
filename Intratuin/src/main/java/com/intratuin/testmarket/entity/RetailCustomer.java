package com.intratuin.testmarket.entity;

import javax.persistence.*;

@Entity
@Table(name = "RETAIL_CUSTOMER")
public class RetailCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RC_ID")
    private int id;

//    @OneToOne
//    @JoinColumn(name = "retailId")
//    @Column(name = "RETAIL_ID")
    Retail retail;

//    @OneToOne
//    @JoinColumn(name = "customerId")
//    @Column(name = "CUSTOMER_ID")
    Customer customer;

    public RetailCustomer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Retail getRetail() {
        return retail;
    }

    public void setRetail(Retail retail) {
        this.retail = retail;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
