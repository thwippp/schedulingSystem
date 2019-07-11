/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author bscha
 */
public class Customer {

    private String customerId;
    private String customerName;
    private String active;
    private String address;
    private String address2;
    private String city;
    private String country;
    private String postalCode;
    private String phone;

    public Customer(String customerId, String customerName, String active, String address, String address2, String city, String country, String postalCode, String phone) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.active = active;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public Customer(ArrayList<String> arrayList) {
        this.customerId = arrayList.get(0);
        this.customerName = arrayList.get(1);
        this.active = arrayList.get(2);
        this.address = arrayList.get(3);
        this.address2 = arrayList.get(4);
        this.city = arrayList.get(5);
        this.country = arrayList.get(6);
        this.postalCode = arrayList.get(7);
        this.phone = arrayList.get(8);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String isActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
