package nl.intratuin.testmarket.entity;

import javax.persistence.*;

@Entity
@Table(name = "ACCESSKEY")
public class AccessKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "CUSTOMER_ID")
    private int customerId;

    @Column(name = "ACCESS_KEY")
    private String accessKey;

    @Column(name = "REFRESH_ACCESS_KEY")
    private String refreshAccessKey;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRE_DATE")
    private java.util.Date expireDate;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public AccessKey() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public java.util.Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(java.util.Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getRefreshAccessKey() {
        return refreshAccessKey;
    }

    public void setRefreshAccessKey(String refreshAccessKey) {
        this.refreshAccessKey = refreshAccessKey;
    }
}
