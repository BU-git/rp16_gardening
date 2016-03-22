package com.intratuin.testmarket.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CUSTOMER")
public class Customer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private int id;

    @Column(name = "CUSTOMER_NAME")
    private String firstName;

    @Column(name = "CUSTOMER_TUSSEN")
    private String tussen;

    @Column(name = "CUSTOMER_SURNAME")
    private String lastName;

    @Column(name = "EMAIL_ADDRESS")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "CELLPHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "DATE_OF_BIRTH")
    private java.sql.Date birthday;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STREET_NAME")
    private String streetName;

    @Column(name = "HOUSE_NUMBER")
    private String houseNumber;

    @Column(name = "AREA_CODE")
    private String postalCode;

    @Column(name = "GENDER")
    private int gender;

    //@OneToOne(mappedBy = "customer")
    //private RetailCustomer retailCustomer;

    //@OneToMany(mappedBy = "customer")
    //private Set<Order> orders = new HashSet<>();

    //TODO:unique field
    //TODO:null or not null(maybe in construxtor)
    //TODO:gender

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int customerId) {
        this.id = customerId;
    }

    /*public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }*/

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getTussen() {
        return tussen;
    }

    public void setTussen(String tussen) {
        this.tussen = tussen;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
/**/
    /*public RetailCustomer getRetailCustomer() {
        return retailCustomer;
    }

    public void setRetailCustomer(RetailCustomer retailCustomer) {
        this.retailCustomer = retailCustomer;
    }*/
}
