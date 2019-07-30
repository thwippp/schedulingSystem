/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author bscha
 */
public class Master {

    public static String user;
    public static String offset;

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }
    public static Appointment getAppointment(int appointment){
        return allAppointments.get(appointment);
    }

    public static void setAllAppointments(ObservableList<Appointment> allAppointments) {
        Master.allAppointments = allAppointments;
    }

    public static void addAppointment(Appointment appointment) {
        allAppointments.add(appointment);
    }

    public static void deleteAppointment(Appointment appointment) {
        allAppointments.remove(appointment);
    }

    public static void deleteAllAppointments() {
        allAppointments.clear();
    }
    
     public static String getOffset() {
        return offset;
    }

    public static void setOffset(String offset) {
        Master.offset = offset;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Master.user = user;
    }

    public static void addCustomer(Customer customer) {
        allCustomers.add(customer);
    }

    public static void deleteCustomer(Customer customer) {
        allCustomers.remove(customer);
    }

    public static void deleteAllCustomers() {
        allCustomers.clear();
    }

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static void setAllCustomers(ObservableList<Customer> allCustomers) {
        Master.allCustomers = allCustomers;
    }

}
