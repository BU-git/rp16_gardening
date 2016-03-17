package com.intratuin.testmarket.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "RETAIL")
public class Retail {

    @Id
    @GeneratedValue
    @Column(name = "RETAIL_ID")
    Integer retailId;

    @Column(name = "EMPLOYEE_NAМE")
    String employeeName;

    @Column(name = "EMPLOYEE_SURNAМE")
    String employeeSurname;

    @Column(name = "DATE_OF_BIRTH")//TODO:implement JodaTime framework for all dates
    Date dateOfBirth;

    @Column(name = "DATE_OF_EMPLOYMENT")
    Date dateOfEmployment;

    public Integer getRetailId() {
        return retailId;
    }

    public void setRetailId(Integer retailId) {
        this.retailId = retailId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeSurname() {
        return employeeSurname;
    }

    public void setEmployeeSurname(String employeeSurname) {
        this.employeeSurname = employeeSurname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfEmployment() {
        return dateOfEmployment;
    }

    public void setDateOfEmployment(Date dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }
}
