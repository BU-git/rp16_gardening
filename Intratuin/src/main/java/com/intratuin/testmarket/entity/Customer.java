package com.intratuin.testmarket.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "CUSTOMER")
public class Customer {

    @Id
    @Column(name = "CUSTOMER_ID")
    Integer customerId;

    @Column(name = "CUSTOMER_NAМE")
    String customerName;

    @Column(name = "CUSTOMER_SURNAME")
    String customerSurname;

    @Column(name = "DATE_OF_BIRTH")
    Date dateOfBirth;

    @Column(name = "CITY")
    String city;

    @Column(name = "STREET_NAME")
    String streetName;

    @Column(name = "HOUSE_NUMBER")
    Integer houseNumber;

    @Column(name = "AREA_CODE")
    String areaCode;

    //TODO:unique field
    //TODO:null or not null(maybe in construxtor)
    //TODO:gender

    @Column(name = "EMAIL_ADDRESS")
    String email;

    @Column(name = "CELLPHONE_NUMBER")
    String cellphoneNumber;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerSurname() {
        return customerSurname;
    }

    public void setCustomerSurname(String customerSurname) {
        this.customerSurname = customerSurname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphoneNumber() {
        return cellphoneNumber;
    }

    public void setCellphoneNumber(String cellphoneNumber) {
        this.cellphoneNumber = cellphoneNumber;
    }
}
