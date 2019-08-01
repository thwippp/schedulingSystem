/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.MYSQL;
import Model.Customer;
import Model.DBConnection;
import Model.Master;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button clearButton;

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

        ObservableList<ArrayList> result = null;
        try {
            String sql = "SELECT Auto_increment FROM information_schema.tables WHERE table_name='customer'";
            result = new MYSQL().query(sql);

            String cId = result.get(0).get(0).toString();
            customerIdTextField.setText(cId);

            System.out.println(result);
        } catch (Exception ex) {
            Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error");
        }

        // TODO -- deelete dummy values- Customer
//        customerNameTextField.setText("Brian Schaffeld");
//        activeRadioButton.selectedProperty().set(true);
//        addressTextField.setText("4129 Winners Circle Ave SE");
//        address2TextField.setText("");
//        cityTextField.setText("Albany");
//        countryTextField.setText("USA");
//        postalCodeTextField.setText("97322");
//        phoneTextField.setText("5419086580");
        // Cleans out customer list so that the query can re-populate it
        Master.deleteAllCustomers();

        // Get list of customers in CustomerScreen
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

        boolean isAdded = false;
        try {
            String sql = "Call CustomerTableView";
            isAdded = new MYSQL().addCustomersFromQuery(sql);
            System.out.println(isAdded);
        } catch (Exception ex) {
            Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error");
        }

    }

    @FXML
    private void addButtonAction(ActionEvent event) throws IOException, SQLException, Exception {
        //add textfield values to database, update tableview, then go to mainscreen

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

        ArrayList<String> customerFields = new ArrayList();
        customerFields.add(cu);
        customerFields.add(ad);
        //  customerFields.add(ad2);  // not required
        customerFields.add(ci);
        customerFields.add(co);
        customerFields.add(po);
        customerFields.add(ph);

        if (customerFields.contains(null)) {
            // ALERT
            String ti = "Error";
            String header = "Invalid Data";
            String content = "Please enter a value for each field on the left.";

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ti);
            alert.setHeaderText(header);
            alert.setContentText(content);

            Image image = new Image("/Model/invisible.png");
            ImageView imageView = new ImageView(image);
            alert.setGraphic(imageView);
            alert.showAndWait();
            System.out.println("Invalid Customer Data");
        } else {
            boolean result = false;
            try {
                CallableStatement cs = null;
                String q = "{Call InsertCustomer(?,?,?,?,?,?,?,?)}";
                Connection conn = DBConnection.getConnection();
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
            stage = (Stage) addButton.getScene().getWindow();
            //load up OTHER FXML document
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/View_Controller/MainScreen.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

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
    private void tableViewSelectionAction() {
        try {
            Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

            customerIdTextField.setText(selectedCustomer.getCustomerId());

            customerNameTextField.setText(selectedCustomer.getCustomerName());
            boolean active;
            int a = Integer.valueOf(selectedCustomer.isActive());
            active = a > 0;
//        System.out.println(a);

            activeRadioButton.setSelected(active);
            addressTextField.setText(selectedCustomer.getAddress());
            address2TextField.setText(selectedCustomer.getAddress2());
            cityTextField.setText(selectedCustomer.getCity());
            countryTextField.setText(selectedCustomer.getCountry());
            postalCodeTextField.setText(selectedCustomer.getPostalCode());
            phoneTextField.setText(selectedCustomer.getPhone());
        } // Null selectedAppointment exception
        catch (Exception e) {
            // ALERT
            String ti = "Error";
            String header = "No Customer Selected";
            String content = "Please select a valid customer from the table on the right.";

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ti);
            alert.setHeaderText(header);
            alert.setContentText(content);

            Image image = new Image("/Model/invisible.png");
            ImageView imageView = new ImageView(image);
            alert.setGraphic(imageView);
            alert.showAndWait();
            System.out.println("Invalid Customer Selected");
        }
    }

    @FXML
    private void updateButtonAction() throws Exception {

        int cid = Integer.valueOf(customerIdTextField.getText());
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

        ArrayList<String> customerFields = new ArrayList();
        customerFields.add(cu);
        customerFields.add(ad);
        //  customerFields.add(ad2);  // not required
        customerFields.add(ci);
        customerFields.add(co);
        customerFields.add(po);
        customerFields.add(ph);

        if (customerFields.contains("") || customerFields.contains(null)) {
            // ALERT
            String ti = "Error";
            String header = "Invalid Data";
            String content = "Please enter a value for each field on the left.";

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ti);
            alert.setHeaderText(header);
            alert.setContentText(content);

            Image image = new Image("/Model/invisible.png");
            ImageView imageView = new ImageView(image);
            alert.setGraphic(imageView);
            alert.showAndWait();
            System.out.println("Invalid Customer Data");
        } else {
            boolean result = false;
            try {
                CallableStatement cs = null;
                String q = "{Call UpdateCustomer(?,?,?,?,?,?,?,?,?,?)}";
                Connection conn = DBConnection.getConnection();
                cs = conn.prepareCall(q);
                cs.setInt(1, cid);
                cs.setString(2, cu);
                cs.setBoolean(3, acrb);
                cs.setString(4, ad);
                cs.setString(5, ad2);
                cs.setString(6, ci);
                cs.setString(7, co);
                cs.setString(8, po);
                cs.setString(9, ph);
                cs.setString(10, Master.getUser());

                cs.executeQuery();  // could change this
                conn.close();

            } catch (SQLException ex) {
                Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
            }

            //Clears old customerTableView
            Master.deleteAllCustomers();

            boolean isAdded = false;
            try {
                String sql = "Call CustomerTableView";
                isAdded = new MYSQL().addCustomersFromQuery(sql);
                System.out.println(isAdded);
            } catch (Exception ex) {
                Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error");
            }
        }// end else if not null
    }

    @FXML
    private void deleteButtonAction() throws Exception {
        System.out.println("delete button action");

        boolean result = false;
        try {
            CallableStatement cs = null;
            String q = "{Call DeleteCustomer(?)}";
            Connection conn = DBConnection.getConnection();
            cs = conn.prepareCall(q);
            cs.setString(1, String.valueOf(customerIdTextField.getText()));

            cs.executeQuery();  // could change this
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }

        //Clears old customerTableView
        Master.deleteAllCustomers();

        boolean isAdded = false;
        try {
            String sql = "Call CustomerTableView";
            isAdded = new MYSQL().addCustomersFromQuery(sql);
            System.out.println(isAdded);
        } catch (Exception ex) {
            Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error");
        }

    }

    @FXML
    private void clearButtonAction() {
        ArrayList customerFieldList = new ArrayList();
        customerFieldList.add(customerIdTextField);
        customerFieldList.add(customerNameTextField);
        customerFieldList.add(addressTextField);
        customerFieldList.add(address2TextField);
        customerFieldList.add(cityTextField);
        customerFieldList.add(countryTextField);
        customerFieldList.add(postalCodeTextField);
        customerFieldList.add(phoneTextField);

        //Lambda expression
        // I used this Lambda expression because I was doing the same operation
        // for each field in the form.  I used a method and passed in 
        // each TextField so that I could perform the operations without
        // extra work.
        customerFieldList.forEach(f -> setNull((TextField) f));
        activeRadioButton.setSelected(false);

        ObservableList<ArrayList> result = null;
        try {
            String sql = "SELECT Auto_increment FROM information_schema.tables WHERE table_name='customer'";
            result = new MYSQL().query(sql);

            String cId = result.get(0).get(0).toString();
            customerIdTextField.setText(cId);

            System.out.println(result);
        } catch (Exception ex) {
            Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error");
        }

    }

    private void setNull(TextField t) {
        t.setText(null);
    }

}
