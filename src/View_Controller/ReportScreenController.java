/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.MYSQL;
import Model.Report;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bscha
 */
public class ReportScreenController implements Initializable {

    @FXML
    private ChoiceBox reportChoiceBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        reportChoiceBox.getItems().addAll(
                "Appointments Per Month",
                "Appointments Per Consultant",
                "Appointments Per Location");
    }

    @FXML
    private void saveButtonAction(ActionEvent event) throws IOException {

        String sql = null;
        ObservableList<ArrayList> headers = FXCollections.observableArrayList();
        ArrayList r = new ArrayList();

        try {
            switch (reportChoiceBox.getValue().toString()) {
                case ("Appointments Per Month"):
                    sql = "select monthname(str_to_date(month(start), '%m')) as 'Month', count(month(start)) as 'Appointments Per Month', type as 'Type' "
                            + "from appointment "
                            + "group by month(start), type;";

                    r.add("Month");
                    r.add("Appointments per Month");
                    r.add("Appointment Type");
                    headers.add(r);

                    break;
                case ("Appointments Per Consultant"):
                    sql = "select contact as 'Consultant', count(contact) as 'Appointments Per Consultant' "
                            + "from appointment "
                            + "group by contact;";

                    r.add("Consultant");
                    r.add("Appointments Per Consultant");
                    headers.add(r);

                    break;
                case ("Appointments Per Location"):
                    sql = "select location as 'Location', count(location) as 'Appointments Per Location' "
                            + "from appointment "
                            + "group by location;";

                    r.add("Location");
                    r.add("Appointments Per Location");
                    headers.add(r);
                    break;
            }
        } catch (Exception e) {
            sql = "select * from AppointmentTableView";
            r.add("Title");
            r.add("Description");
            r.add("Type");
            r.add("Customer Name");
            r.add("Contact");
            r.add("Location");
            r.add("Date");
            r.add("Start");
            r.add("End");
            headers.add(r);
        }

        ObservableList<ArrayList> result = FXCollections.observableArrayList();
        String resultString = "";
        try {
            result = new MYSQL().query(sql);

            for (int i = 0; i < result.size(); i++) {
                headers.add(result.get(i));
            }
            int row = 0;
            while (row < headers.size()) {
                resultString = resultString + Arrays.deepToString(headers.get(row).toArray()) + "\n";
                row++;
            }

            System.out.println(result);
        } catch (Exception ex) {
            Logger.getLogger(ReportScreenController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error");
        }

        // Switch to Report Screen
        Stage stage;
        Parent root;
        stage = (Stage) saveButton.getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/View_Controller/ReportView.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        ReportViewController controller = loader.getController();
        controller.setReport(resultString);

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

    private String rightPad(String text, int i) {
        return String.format("%-" + i + "." + i + "s", text);
    }

}
