/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Customer;
import Model.Master;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bscha
 */
public class CustomerScreenController implements Initializable {

    @FXML
    private TextField customerIdTextField;
    
    @FXML
    private TextField customerNameTextField;

    @FXML
    private RadioButton activeRadioButton;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField address2TextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private TextField countryTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;
    
    @FXML
    private Button updateButton;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, String> customerIdTableColumn;

    @FXML
    private TableColumn<Customer, String> customerNameTableColumn;

    @FXML
    private TableColumn<Customer, String> activeTableColumn;

    @FXML
    private TableColumn<Customer, String> addressTableColumn;

    @FXML
    private TableColumn<Customer, String> address2TableColumn;

    @FXML
    private TableColumn<Customer, String> cityTableColumn;

    @FXML
    private TableColumn<Customer, String> postalCodeTableColumn;

    @FXML
    private TableColumn<Customer, String> countryTableColumn;

    @FXML
    private TableColumn<Customer, String> phoneTableColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // FAKE TEST DATA DELETE ME
        
        customerNameTextField.setText("Rebecca");
        activeRadioButton.selectedProperty().set(true);
        addressTextField.setText("address");
        address2TextField.setText("address2");
        cityTextField.setText("city");
        countryTextField.setText("country");
        postalCodeTextField.setText("postalcode");
        phoneTextField.setText("phone");

        // Cleans out customer list so that the query can re-populate it
        Master.deleteAllCustomers();

        // Get list of products in Main Screen
        customerTableView.setItems(Master.getAllCustomers());
        customerIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        activeTableColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
        addressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        address2TableColumn.setCellValueFactory(new PropertyValueFactory<>("address2"));
        cityTableColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        postalCodeTableColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        countryTableColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        phoneTableColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        boolean result = false;
        //            result = MYSQL.query("Select * from customer");
//            String q = "call customerinfo";
        String q = "Call CustomerTableView";
        try {
            result = MYSQL.addCustomersFromQuery(q);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void saveButtonAction(ActionEvent event) throws IOException, SQLException {
        //TODO-- add textfield values to database, update tableview, then go to mainscreen

        String cu = customerNameTextField.getText();

        int ac = 0;
        boolean acrb = activeRadioButton.isSelected();
        if (acrb) {
            ac = 1;
        }

        String ad = addressTextField.getText();
        String ad2 = address2TextField.getText();
        String ci = cityTextField.getText();
        String co = countryTextField.getText();
        String po = postalCodeTextField.getText();
        String ph = phoneTextField.getText();

        boolean result = false;
        try {
        CallableStatement cs = null;
        String q = "{Call InsertNewCustomer(?,?,?,?,?,?,?,?)}";
        Connection conn = MYSQL.getConnection();
        cs = conn.prepareCall(q);
        cs.setString(1, cu);
        cs.setBoolean(2, acrb);
        cs.setString(3, ad);
        cs.setString(4, ad2);
        cs.setString(5, ci);
        cs.setString(6, co);
        cs.setString(7, po);
        cs.setString(8, ph);
        
        cs.executeQuery();  // could change this
        conn.close();
      
        } catch (SQLException ex) {
            Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }

        // Go to MainScreen
        Stage stage;
        Parent root;
        stage = (Stage) saveButton.getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/View_Controller/MainScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) cancelButton.getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/View_Controller/MainScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void tableViewSelectionAction(){
         Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

        customerIdTextField.setText(selectedCustomer.getCustomerId());

        customerNameTextField.setText(selectedCustomer.getCustomerName());
        boolean active;
        int a = Integer.valueOf(selectedCustomer.isActive());
        active = a > 0;
        System.out.println(a);
        
        activeRadioButton.setSelected(active);
        addressTextField.setText(selectedCustomer.getAddress());
        address2TextField.setText(selectedCustomer.getAddress2());
        cityTextField.setText(selectedCustomer.getCity());
        countryTextField.setText(selectedCustomer.getCountry());
        postalCodeTextField.setText(selectedCustomer.getPostalCode());
        phoneTextField.setText(selectedCustomer.getPhone());
    }
    
    @FXML
    private void updateButtonAction(){
       
        
        //TODO-- after you press update, update the selected customer in the DB
        //Todo-- currently have the button and the table set to populate the fields on the left
    }

}
