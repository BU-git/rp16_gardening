package nl.intratuin.testmarket.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    Integer orderId;


//    @ManyToOne
//    @JoinColumn(name = "customerId")
    @Column(name = "CUSTOMER_ID")
    int customerId  ;

//    @ManyToOne
//    @JoinColumn(name = "orderRuleId")
    @Column(name = "ORDER_RULE_ID")
    int orderRuleId;

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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customer) {
        this.customerId = customer;
    }

    public int getOrderRuleId() {
        return orderRuleId;
    }

    public void setOrderRuleId(int orderRule) {
        this.orderRuleId = orderRule;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
