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

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    
    

    public static void addCustomer(Customer customer) {
        allCustomers.add(customer);
    }

    public static void deleteCustomer(Customer customer) {
        allCustomers.remove(customer);
    }
    
    public static void deleteAllCustomers(){
        allCustomers.clear();
    }

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static void setAllCustomers(ObservableList<Customer> allCustomers) {
        Master.allCustomers = allCustomers;
    }

}
