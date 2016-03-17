package com.intratuin.testmarket.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "CUSTOMER")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private int id;

    @Column(name = "CUSTOMER_NAME")
    private String firstName;

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
    private int houseNumber;

    @Column(name = "AREA_CODE")
    private String postalCode;

    //TODO:unique field
    //TODO:null or not null(maybe in construxtor)
    //TODO:gender

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
