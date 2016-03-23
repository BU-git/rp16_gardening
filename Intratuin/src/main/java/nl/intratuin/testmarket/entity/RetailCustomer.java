package nl.intratuin.testmarket.entity;

import javax.persistence.*;

@Entity
@Table(name = "RETAIL_CUSTOMER")
public class RetailCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RC_ID")
    private int id;

    @Column(name = "RETAIL_ID")
    int retailId;

    @Column(name = "CUSTOMER_ID")
    int customerId;

    public RetailCustomer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRetailId() {
        return retailId;
    }

    public void setRetailId(int retail) {
        this.retailId = retail;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customer) {
        this.customerId = customer;
    }
}
