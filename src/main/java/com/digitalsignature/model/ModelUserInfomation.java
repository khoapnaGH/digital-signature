/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.model;

/**
 *
 * @author Admin
 */
public class ModelUserInfomation {
    private String serial;
    private String userFullName;
    private String emailContact;
    private String organization;
    private String address;
    private String activateDate;

    public ModelUserInfomation(String serial, String userFullName, String emailContact, String organization, String address, String activateDate) {
        this.serial = serial;
        this.userFullName = userFullName;
        this.emailContact = emailContact;
        this.organization = organization;
        this.address = address;
        this.activateDate = activateDate;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }
    
    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getActiveDate() {
        return activateDate;
    }

    public void setActiveDate(String activateDate) {
        this.activateDate = activateDate;
    }
    
    
}
