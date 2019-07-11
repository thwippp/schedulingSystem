/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bscha
 */
public class AppointmentScreenController implements Initializable {

    @FXML
    private ChoiceBox<?> startChoiceBox;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private ChoiceBox<?> customerChoiceBox;
    @FXML
    private ChoiceBox<?> locationChoiceBox;
    @FXML
    private ChoiceBox<?> typeChoiceBox;
    @FXML
    private ChoiceBox<?> endChoiceBox;
    @FXML
    private ChoiceBox<?> contactChoiceBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private DatePicker dateDatePicker;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        boolean result = false;
        try {
            result = MYSQL.query("Select * from appointment");
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(result);
        
    }    

    @FXML
    private void saveButtonAction(ActionEvent event) throws IOException {

        // INSERT into MySQL
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
    private void cancelButton(ActionEvent event) throws IOException {

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
    
}
