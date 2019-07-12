/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.MYSQL;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bscha
 */
public class LoginScreenController implements Initializable {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordPasswordField;

    @FXML
    private Button loginButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void loginButtonAction(ActionEvent event) throws IOException, SQLException {
        // Get UTC_Timestamp
        // 

        // Get Username
        String username = usernameTextField.getText();

        // Get Password
        String password = passwordPasswordField.getText();
        
        // Match on username
        ObservableList<ArrayList> result = null;
        try {
            String sql = "select userId, userName, password, active from user \n"
                + "where username = '" + username + "'\n"
                + "limit 1;";
            result = new MYSQL().query(sql);
            System.out.println(result);
        } catch (Exception ex) {
            Logger.getLogger(AppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error");
        }
               
        System.out.println(result.size());
        
        // Switch scene to MainScreen
        Stage stage;
        Parent root;
        stage = (Stage) loginButton.getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/View_Controller/MainScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
