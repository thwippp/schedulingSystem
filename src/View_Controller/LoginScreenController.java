/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.DateAndTime;
import Model.MYSQL;
import Model.Master;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        // TODO delete dummy values
        usernameTextField.setText("test");
        passwordPasswordField.setText("test");
    }

    @FXML
    private void loginButtonAction(ActionEvent event) throws IOException, SQLException, Exception {
        java.util.logging.Logger log = java.util.logging.Logger.getLogger("log.txt");

        try {
            FileHandler fh = new FileHandler("log.txt", true);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            log.addHandler(fh);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
        }
        log.setLevel(Level.INFO);  // adjust logging level

        // Set timezone for user based on timezone in OS
        DateAndTime dt = new DateAndTime();
        dt.setLocalZoneId(ZoneId.systemDefault());
        ZoneId localZoneId = dt.getLocalZoneId();
        ZoneOffset offset = localZoneId.getRules().getOffset(Instant.now());

        // Set offset in Master class
        Master.setOffset(offset.toString());

        // NOTES
        /*
               ... convert_tz(start | end ,'+0:00', '" + Master.getOffset() + "' ), ...  gettting information from the DB
               ... convert_tz(start | end , '" + Master.getOffset() + "''+0:00', ), ...  putting information into the DB

                
         */
        //Temp query test
//        ObservableList<ArrayList> res = null;
//        try {
//            String sql = "select title, convert_tz(start,'+0:00', '" + Master.getOffset() + "' ), start from appointment";
//            res = new MYSQL().query(sql);
//            System.out.println(res);
//        } catch (Exception ex) {
//            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("Error");
//        }
        System.out.println("Master offset: " + Master.getOffset());
        System.out.println("Local ZoneId: " + localZoneId);
        System.out.println("Offset: " + offset);

        // Get Username
        String username = usernameTextField.getText();

        // Get Password
        String password = passwordPasswordField.getText();

        // Match on username
        ObservableList<ArrayList> result = null;
        try {
            String sql = "SELECT userId, userName, password, active\n"
                    + "FROM user WHERE username = '" + username + "' AND password = '" + password + "' LIMIT 1;";
            result = new MYSQL().query(sql);
            System.out.println(result);
        } catch (Exception ex) {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error");
        }

        boolean isValidUser;
        isValidUser = result.size() > 0;

        System.out.println("User is valid? " + isValidUser);

        if (!isValidUser) {
            // ALERT
            String title = "Error";
            String header = "Invalid User";
            String content = "Sorry. I can't find that user in the database.  Please try again.";

            // TODO-- Change error text... 2 languages... based on location
            //"Sorry. The Username and Password don't match."
            // "Lo siento. El nombre de usuario y la contraseña no coinciden."
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);

            Image image = new Image("/Model/invisible.png");
            ImageView imageView = new ImageView(image);
            alert.setGraphic(imageView);
            alert.showAndWait();

        } // end if invald user
        else {
            boolean isFifteenMinuteWarningGood = true;

            ObservableList<ArrayList> fifteenMin = null;
            try {
                String sql = "call FifteenMinuteWarning";
                fifteenMin = new MYSQL().query(sql);
                int fM = Integer.valueOf(fifteenMin.get(0).get(0).toString());
                isFifteenMinuteWarningGood = fM > 15;
                System.out.println(fM);
            } catch (Exception ex) {
                Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error");
            }
            if (!isFifteenMinuteWarningGood) {
                // ALERT
                String title = "Warning";
                String header = "15 Minute Warning";
                String content = null;
                try {
                    content = "There is a " + fifteenMin.get(0).get(1).toString() + " appointment with " + fifteenMin.get(0).get(2).toString() + " called " + fifteenMin.get(0).get(3).toString() + " in " + fifteenMin.get(0).get(0).toString() + " minute(s).";
                } catch (Exception e) {
                    content = "There are no appointments within 15 minutes.";
                }

                // TODO-- Change error text... 2 languages... based on location
                //"Sorry. The Username and Password don't match."
                // "Lo siento. El nombre de usuario y la contraseña no coinciden."
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(title);
                alert.setHeaderText(header);
                alert.setContentText(content);

                Image image = new Image("/Model/calendarWarning.png");
                ImageView imageView = new ImageView(image);
                alert.setGraphic(imageView);
                alert.showAndWait();
            }/* end if appointment is scheduled within 15 minutes*/  //else {
            
            
            // Set Master User
            Master.setUser(username);

            // Log User
            log.log(Level.INFO, "User \"{0}\" has logged in.", usernameTextField.getText());

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
        }// end else change scenes
        // }  // end else valid user
    } // end loginbuttonaction

}  // end class
