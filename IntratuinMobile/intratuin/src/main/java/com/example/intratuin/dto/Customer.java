package com.example.intratuin.dto;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;

/**
 * Created by Иван on 19.03.2016.
 */
public class Customer {
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    private java.sql.Date birthday;

    private String city;

    private String streetName;

    private int houseNumber;

    private String postalCode;

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

    public JSONObject toJSON(){
        JSONObject json=new JSONObject();
        try{
            json.put("id",id);
            json.put("firstName",firstName);
            json.put("lastName",lastName);
            json.put("email",email);
            json.put("password",password);
            json.put("phoneNumber",phoneNumber);
            json.put("birthday",birthday);
            json.put("city",city);
            json.put("streetName",streetName);
            json.put("houseNumber",houseNumber);
            json.put("postalCode",postalCode);
            return json;
        }
        catch(JSONException e){
            return null;
        }
    }
}
