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
public class Appointment {

    private String id;
    private String title;
    private String description;

    private String customer;
    private String type;

    private String contact;
    private String location;

    private String date;
    private String start;
    private String end;
    
    private String url;

    public Appointment(String id, String title, String description, String customer, String type, String contact, String location, String date, String start, String end, String url) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.customer = customer;
        this.type = type;
        this.contact = contact;
        this.location = location;
        this.date = date;
        this.start = start;
        this.end = end;
        this.url = url;
    }
    
    public Appointment(ArrayList<String> arrayList) {
        this.id = arrayList.get(0);
        this.title = arrayList.get(1);
        this.description = arrayList.get(2);
        this.type = arrayList.get(3);
        this.customer = arrayList.get(4);
        this.contact = arrayList.get(5);
        this.location = arrayList.get(6);
        this.date = arrayList.get(7);
        this.start = arrayList.get(8);
        this.end = arrayList.get(9);
        this.url = arrayList.get(10);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    

}
