/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import Model.DBConnection;
import Model.MYSQL;
import Model.Master;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
public class AppointmentScreenController implements Initializable {

    @FXML
    private ChoiceBox<String> startChoiceBox;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private ChoiceBox<String> customerChoiceBox;
    @FXML
    private ChoiceBox<String> locationChoiceBox;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private DatePicker dateDatePicker;
    @FXML
    private ChoiceBox<String> endChoiceBox;
    @FXML
    private ChoiceBox<String> contactChoiceBox;
    @FXML
    private TextField urlTextField;
    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private TableColumn<Appointment, String> titleTableColumn;
    @FXML
    private TableColumn<Appointment, String> descriptionTableColumn;
    @FXML
    private TableColumn<Appointment, String> typeTableColumn;
    @FXML
    private TableColumn<Appointment, String> customerTableColumn;
    @FXML
    private TableColumn<Appointment, String> contactTableColumn;
    @FXML
    private TableColumn<Appointment, String> locationTableColumn;
    @FXML
    private TableColumn<Appointment, String> dateTableColumn;
    @FXML
    private TableColumn<Appointment, String> startTableColumn;
    @FXML
    private TableColumn<Appointment, String> endTableColumn;
    @FXML
    private TableColumn<Appointment, String> urlTableColumn;

    @FXML
    private Label appointmentIdLabel;
    @FXML
    private DatePicker weekDatePicker;
    @FXML
    private DatePicker monthDatePicker;

    @FXML
    private Button weekViewButton;
    @FXML
    private Button monthViewButton;
    @FXML
    private Button resetButton;

    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button cancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Gets current application user
        String currentUser = Master.getUser();
        System.out.println("Current user: " + currentUser);

        // Cleans out appointment list so that the query can re-populate it
        Master.deleteAllAppointments();

        // Get list of appointments in CustomerScreen
        appointmentTableView.setItems(Master.getAllAppointments());
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        customerTableColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
        contactTableColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        locationTableColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTableColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endTableColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        urlTableColumn.setCellValueFactory(new PropertyValueFactory<>("url"));

        // Populate Appointment TableView from Query
        boolean isAdded = false;
        try {
            String offset = Master.getOffset();
            String sql = "select appointmentId, title, description, type, customerName, contact, location,  date(convert_tz(start,'+0:00','" + offset + "')) date, DATE_FORMAT(convert_tz(start,'+0:00','" + offset + "'), '%H:%i') start, DATE_FORMAT(convert_tz(end,'+0:00','" + offset + "'), '%H:%i') end, url from appointment\n"
                    + "join customer on appointment.customerId = customer.customerId";
            /*
            String sql = "select appointmentId, title, description, type, customerName, contact, location,  date(start) date, DATE_FORMAT(start, '%H:%i') start, DATE_FORMAT(end, '%H:%i') end, url from appointment\n"
                    + "join customer on appointment.customerId = customer.customerId";
             */
            isAdded = new MYSQL().addAppointmentsFromQuery(sql);
            System.out.println(isAdded);
        } catch (Exception ex) {
            Logger.getLogger(AppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error");
        }

        // Customer Choice Box
        ObservableList<ArrayList> customers = FXCollections.observableArrayList();
        try {
            String sql = "select customerName from customer";
            customers = new MYSQL().query(sql);
            int c = 0;

            while (c < customers.size()) {
                customerChoiceBox.getItems().add(customers.get(c).get(0).toString());
                c++;
            }
            System.out.println(customers);
        } catch (Exception ex) {
            Logger.getLogger(AppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error");
        }

        // Type
        typeChoiceBox.getItems().addAll("Kickoff", "Follow Up", "Closing");

        //Contact
        ObservableList<ArrayList> users = FXCollections.observableArrayList();
        try {
            String sql = "select userName from user";
            users = new MYSQL().query(sql);
            int u = 0;

            while (u < users.size()) {
                contactChoiceBox.getItems().add(users.get(u).get(0).toString());
                u++;
            }
            System.out.println(users);
        } catch (Exception ex) {
            Logger.getLogger(AppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error");
        }

        //Location
        ObservableList<ArrayList> addresses = FXCollections.observableArrayList();
        try {
            String sql = "select city from city";
//            String sql = "select concat_ws(' ',address, address2) from address";
            addresses = new MYSQL().query(sql);
            int a = 0;

            while (a < addresses.size()) {
                locationChoiceBox.getItems().add(addresses.get(a).get(0).toString());
                a++;
            }
            System.out.println(addresses);
        } catch (Exception ex) {
            Logger.getLogger(AppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error");
        }

        // Generate times for choiceboxes
        ArrayList<String> times = new ArrayList();
        for (int i = 0; i < 24; i++) {
            String hour = null;
            String zMinute = ":00";
            String tMinute = ":30";
            if (i < 10) {
                hour = "0" + String.valueOf(i);
            } else {
                hour = String.valueOf(i);
            }
            times.add(hour + zMinute);
            times.add(hour + tMinute);
        }

        // Lambda expression to add times to the choiceboxes.
        /*
        Here you can see two different ways to populate the start time choicebox
        and the end time choicebox.  When done "literally", each choicebox takes several lines of code
        or requires quite a bit of scrolling.
        By generating the times with a for loop and adding them with Lambda expressions,
        I was able to populate both choiceboxes in a much more succinct fashion.
         */
        times.forEach(t -> startChoiceBox.getItems().add(t));
        times.forEach(t -> endChoiceBox.getItems().add(t));

        // Start  // 7/25
//        startChoiceBox.getItems().addAll("00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30");
        // End
//        endChoiceBox.getItems().addAll("00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30");
    }

    @FXML
    private void weekViewButtonAction() {
        System.out.println("week view button action.");
        // get date
        LocalDate week = weekDatePicker.getValue();
        if (week != null) {

            // get start week
            LocalDate startWeek = monday(week);

            System.out.println(startWeek.toString());
            // get end week
            LocalDate endWeek = friday(week);
            System.out.println(endWeek.toString());

            // clear appointments
            Master.deleteAllAppointments();

            // execute query / update tableview
            // Populate Appointment TableView from Query
            boolean isAdded = false;
            try {
                String sql = "select appointmentId, title, description, type, customerName, contact, location,  date(start) date, DATE_FORMAT(start, '%H:%i') start, DATE_FORMAT(end, '%H:%i') end, url from appointment\n"
                        + "join customer on appointment.customerId = customer.customerId\n"
                        + "where date(start) >= '" + startWeek + "' and date(start) <= '" + endWeek + "'";
                isAdded = new MYSQL().addAppointmentsFromQuery(sql);
                System.out.println(isAdded);
            } catch (Exception ex) {
                Logger.getLogger(AppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error");
            }
        }

    }

    @FXML
    private void monthViewButtonAction() {
        System.out.println("month view button action.");
        // get date
        try {
            int month = monthDatePicker.getValue().getMonthValue();

            // clear appointments
            Master.deleteAllAppointments();

            // execute query / update tableview
            // Populate Appointment TableView from Query
            boolean isAdded = false;
            try {
                String offset = Master.getOffset();
                String sql = "select appointmentId, title, description, type, customerName, contact, location,  date(convert_tz(start,'+0:00','" + offset + "')) date, DATE_FORMAT(convert_tz(start,'+0:00','" + offset + "'), '%H:%i') start, DATE_FORMAT(convert_tz(end,'+0:00','" + offset + "'), '%H:%i') end, url from appointment\n"
                        + "join customer on appointment.customerId = customer.customerId\n"
                        + "where month(start) = " + month;
                isAdded = new MYSQL().addAppointmentsFromQuery(sql);
                System.out.println(isAdded);
            } catch (Exception ex) {
                Logger.getLogger(AppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error");
            }
        } catch (Exception e) {
            // Do nothing
        }
    }

    @FXML
    private void resetButtonAction() {

        // Resets week and month date pickers
        weekDatePicker.setValue(null);
        monthDatePicker.setValue(null);

        // Cleans out appointment list so that the query can re-populate it
        Master.deleteAllAppointments();

        // Populate Appointment TableView from Query
        boolean isAdded = false;
        try {
            String offset = Master.getOffset();
            String sql = "select appointmentId, title, description, type, customerName, contact, location,  date(convert_tz(start,'+0:00','" + offset + "')) date, DATE_FORMAT(convert_tz(start,'+0:00','" + offset + "'), '%H:%i') start, DATE_FORMAT(convert_tz(end,'+0:00','" + offset + "'), '%H:%i') end, url from appointment\n"
                    + "join customer on appointment.customerId = customer.customerId";
            isAdded = new MYSQL().addAppointmentsFromQuery(sql);
            System.out.println(isAdded);
        } catch (Exception ex) {
            Logger.getLogger(AppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error");
        }

    }

    @FXML
    private void tableViewSelectionAction() {
        try {
            Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();

            String title = selectedAppointment.getTitle();
            String description = selectedAppointment.getDescription();

            String customer = selectedAppointment.getCustomer();
            String type = selectedAppointment.getType();

            String contact = selectedAppointment.getContact();
            String location = selectedAppointment.getLocation();

            // Date down below... more complicated
            String start = selectedAppointment.getStart();
            String end = selectedAppointment.getEnd();

            String url = selectedAppointment.getUrl();

            titleTextField.setText(title);
            descriptionTextArea.setText(description);
            typeChoiceBox.setValue(type);
            customerChoiceBox.setValue(customer);
            contactChoiceBox.setValue(contact);
            locationChoiceBox.setValue(location);
            LocalDate ld = LocalDate.parse(selectedAppointment.getDate());
            dateDatePicker.setValue(ld);
            startChoiceBox.setValue(start);
            endChoiceBox.setValue(end);
            urlTextField.setText(url);

            ObservableList<ArrayList> result = null;
            try {
                String sql = "select appointmentId from appointment"
                        + " where title = '" + title
                        + "' and description = '" + description
                        + "' and type = '" + type
                        //                    + "' and customer = '" + customer 
                        + "' and contact = '" + contact
                        + "' and location = '" + location
                        + "' and url = '" + url + "'";

                result = new MYSQL().query(sql);

                String aId = (String) result.get(0).get(0).toString();
                appointmentIdLabel.setText(aId);

                System.out.println(result);
            } catch (Exception ex) {
                Logger.getLogger(AppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error");
            }
        } // Null selectedAppointment exception
        catch (Exception e) {
            // ALERT
            String ti = "Error";
            String header = "No Appointment Selected";
            String content = "Please select a valid appointment from the table on the right.";

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ti);
            alert.setHeaderText(header);
            alert.setContentText(content);

            Image image = new Image("/Model/calendarWarning.png");
            ImageView imageView = new ImageView(image);
            alert.setGraphic(imageView);
            alert.showAndWait();
            System.out.println("Invalid Appointment Selected");
        }
    }

    @FXML
    private void addButtonAction(ActionEvent event) throws IOException, Exception {

        String title = titleTextField.getText();
        String description = descriptionTextArea.getText();

        String customer = customerChoiceBox.getValue();
        String type = typeChoiceBox.getValue();

        String contact = contactChoiceBox.getValue();
        String location = locationChoiceBox.getValue();

        String dateString;
        try {
            dateString = dateDatePicker.getValue().toString();
        } catch (Exception e) {
            dateString = "";
        }
        String startString = startChoiceBox.getValue();
        String endString = endChoiceBox.getValue();

        String url = urlTextField.getText();

        ArrayList<String> appointmentFields = new ArrayList();
        appointmentFields.add(title);
        appointmentFields.add(description);
        appointmentFields.add(customer);
        appointmentFields.add(type);
        appointmentFields.add(contact);
        appointmentFields.add(location);
        appointmentFields.add(dateString);
        appointmentFields.add(startString);
        appointmentFields.add(endString);
        appointmentFields.add(url);

        if (appointmentFields.contains("")) {
            // ALERT
            String ti = "Error";
            String header = "Invalid Data";
            String content = "Please enter a value for each field on the left.";

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ti);
            alert.setHeaderText(header);
            alert.setContentText(content);

            Image image = new Image("/Model/calendarWarning.png");
            ImageView imageView = new ImageView(image);
            alert.setGraphic(imageView);
            alert.showAndWait();
            System.out.println("Invalid Appointment Data");
        } else {

            String start = dateString + " " + startString + ":00";
            String end = dateString + " " + endString + ":00";

            DateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Date beginningOfDay = timeFormat.parse("08:00");
            Date endOfDay = timeFormat.parse("17:00");
            Date s = timeFormat.parse(startString);
            Date e = timeFormat.parse(endString);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // Selected Start and End Dates
            Date sd = dateFormat.parse(start);
            Date ed = dateFormat.parse(end);

            // Selected Start and End Instant
            Instant iStart = sd.toInstant();
            Instant iEnd = ed.toInstant();

            // Overlapping test
            boolean isOverlappingAppointment = false;
            String overlappingAppointmentTitle = null;
            int a = 0;
            while (a < Master.getAllAppointments().size()) {
                String cStartDateString = Master.getAppointment(a).getDate();
                String cStartTimeString = Master.getAppointment(a).getStart();
                String cEndTimeString = Master.getAppointment(a).getEnd();

                String cStartString = cStartDateString + " " + cStartTimeString + ":00";
                String cEndString = cStartDateString + " " + cEndTimeString + ":00";

                // Current start and end dates
                Date cStart = dateFormat.parse(cStartString);
                Date cEnd = dateFormat.parse(cEndString);

                // Instant
                Instant ciStart = cStart.toInstant();
                Instant ciEnd = cEnd.toInstant();

                if (title.equals(Master.getAppointment(a).getTitle())) {
                    System.out.println("Same, Michael.");
                } else if (!((iStart.isBefore(ciStart) && (iEnd.isBefore(ciStart) || iEnd.equals(ciStart))) // B
                        || ((iStart.isAfter(ciEnd) || iStart.equals(ciEnd)) && iEnd.isAfter(ciEnd)) // C
                        )) {

                    /*
                        [ciStart -A-  ciEnd]
        [iStart -B- iEnd]                  [iStart  -C-  iEnd]  
                
                     */
                    isOverlappingAppointment = true;
                    overlappingAppointmentTitle = Master.getAppointment(a).getTitle();
                    System.out.println("Appointment overlaps with ... " + overlappingAppointmentTitle + " " + Master.getAppointment(a).getStart() + " - " + Master.getAppointment(a).getEnd());

                }
                a++;

            } // while

            if (isOverlappingAppointment) {
                // ALERT
                String ti = "Warning";
                String header = "Overlapping Appointment";
                String content = "That appointment overlaps with another appointment " + overlappingAppointmentTitle;

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ti);
                alert.setHeaderText(header);
                alert.setContentText(content);

                Image image = new Image("/Model/calendarWarning.png");
                ImageView imageView = new ImageView(image);
                alert.setGraphic(imageView);
                alert.showAndWait();
                System.out.println("Invalid Appointment");

            } else {

                // Outside business hours warning
                if (((((!s.equals(beginningOfDay) && !s.after(beginningOfDay) || !s.before(endOfDay)) || (!e.equals(beginningOfDay) && !e.after(beginningOfDay))) || (!e.equals(endOfDay) && !e.before(endOfDay))) || s.equals(e)) || e.before(s)) {
                    // ALERT
                    String t = "Warning";
                    String header = "Check Appointment Time";
                    String content = "That appointment is outside business hours or an invalid time.";

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(t);
                    alert.setHeaderText(header);
                    alert.setContentText(content);

                    Image image = new Image("/Model/calendarWarning.png");
                    ImageView imageView = new ImageView(image);
                    alert.setGraphic(imageView);
                    alert.showAndWait();
                    System.out.println("Invalid Appointment");
                } else {

                    // INSERT into MySQL
                    try {
                        CallableStatement cs = null;
                        String q = "{call insertappointment(?,?,?,?,?,?,?,?,?,?)}";
                        Connection conn = DBConnection.getConnection();
                        cs = conn.prepareCall(q);
                        cs.setString(1, title);
                        cs.setString(2, description);
                        cs.setString(3, type);
                        cs.setString(4, customer);  // could change this to Master.getUser();
                        cs.setString(5, contact);
                        cs.setString(6, location);
                        cs.setString(7, start);
                        cs.setString(8, end);
                        cs.setString(9, url);
                        cs.setString(10, Master.getOffset());  // Changed 7/25 in DB and here

                        cs.executeQuery();
                        conn.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(AppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println(ex);
                    }

                    System.out.println(title + description + customer + type + contact + location + dateString + start + end + url);

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

                    System.out.println("Valid Appointment");
                }// end else is during business hours
            }// end else is overlapping
        }// end else if null values
    }// end add method

    @FXML
    private void updateButtonAction(ActionEvent event) throws IOException, Exception {
        String title = titleTextField.getText();
        String description = descriptionTextArea.getText();

        String customer = customerChoiceBox.getValue();
        String type = typeChoiceBox.getValue();

        String contact = contactChoiceBox.getValue();
        String location = locationChoiceBox.getValue();

        String dateString;
        try {
            dateString = dateDatePicker.getValue().toString();
        } catch (Exception e) {
            dateString = "";
        }
        String startString = startChoiceBox.getValue();
        String endString = endChoiceBox.getValue();

        String url = urlTextField.getText();

        ArrayList<String> appointmentFields = new ArrayList();
        appointmentFields.add(title);
        appointmentFields.add(description);
        appointmentFields.add(customer);
        appointmentFields.add(type);
        appointmentFields.add(contact);
        appointmentFields.add(location);
        appointmentFields.add(dateString);
        appointmentFields.add(startString);
        appointmentFields.add(endString);
        appointmentFields.add(url);

        if (appointmentFields.contains("")) {
            // ALERT
            String ti = "Error";
            String header = "Invalid Data";
            String content = "Please enter a value for each field on the left.";

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ti);
            alert.setHeaderText(header);
            alert.setContentText(content);

            Image image = new Image("/Model/calendarWarning.png");
            ImageView imageView = new ImageView(image);
            alert.setGraphic(imageView);
            alert.showAndWait();
            System.out.println("Invalid Appointment Data");
        } else {

//        String title = titleTextField.getText();
//        String description = descriptionTextArea.getText();
//
//        String customer = customerChoiceBox.getValue();
//        String type = typeChoiceBox.getValue();
//
//        String contact = contactChoiceBox.getValue();
//        String location = locationChoiceBox.getValue();
//
//        String dateString = dateDatePicker.getValue().toString();
//        String startString = startChoiceBox.getValue();
//        String endString = endChoiceBox.getValue();
            String start = dateString + " " + startString + ":00";
            String end = dateString + " " + endString + ":00";

//        String url = urlTextField.getText();
            int appointmentId = Integer.valueOf(appointmentIdLabel.getText());

            DateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Date beginningOfDay = timeFormat.parse("08:00");
            Date endOfDay = timeFormat.parse("17:00");
            Date s = timeFormat.parse(startString);
            Date e = timeFormat.parse(endString);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // Selected Start and End Dates
            Date sd = dateFormat.parse(start);
            Date ed = dateFormat.parse(end);

            // Selected Start and End Instant
            Instant iStart = sd.toInstant();
            Instant iEnd = ed.toInstant();

            // Overlapping test
            boolean isOverlappingAppointment = false;
            String overlappingAppointmentTitle = null;
            int a = 0;
            while (a < Master.getAllAppointments().size()) {
                String cStartDateString = Master.getAppointment(a).getDate();
                String cStartTimeString = Master.getAppointment(a).getStart();
                String cEndTimeString = Master.getAppointment(a).getEnd();

                String cStartString = cStartDateString + " " + cStartTimeString + ":00";
                String cEndString = cStartDateString + " " + cEndTimeString + ":00";

                // Current start and end dates
                Date cStart = dateFormat.parse(cStartString);
                Date cEnd = dateFormat.parse(cEndString);

                // Instant
                Instant ciStart = cStart.toInstant();
                Instant ciEnd = cEnd.toInstant();

                if (title.equals(Master.getAppointment(a).getTitle())) {
                    System.out.println("Same, Michael.");
                } else if (!((iStart.isBefore(ciStart) && (iEnd.isBefore(ciStart) || iEnd.equals(ciStart))) // B
                        || ((iStart.isAfter(ciEnd) || iStart.equals(ciEnd)) && iEnd.isAfter(ciEnd)) // C
                        )) {

                    /*
                        [ciStart -A-  ciEnd]
        [iStart -B- iEnd]                  [iStart  -C-  iEnd]  
                
                     */
                    isOverlappingAppointment = true;
                    overlappingAppointmentTitle = Master.getAppointment(a).getTitle();
                    System.out.println("Appointment overlaps with ... " + overlappingAppointmentTitle + " " + Master.getAppointment(a).getStart() + " - " + Master.getAppointment(a).getEnd());

                }
                a++;

            } // while

            if (isOverlappingAppointment) {
                // ALERT
                String ti = "Warning";
                String header = "Overlapping Appointment";
                String content = "That appointment overlaps with another appointment " + overlappingAppointmentTitle;

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ti);
                alert.setHeaderText(header);
                alert.setContentText(content);

                Image image = new Image("/Model/calendarWarning.png");
                ImageView imageView = new ImageView(image);
                alert.setGraphic(imageView);
                alert.showAndWait();
                System.out.println("Invalid Appointment");

            } else {

                // Outside business hours warning
                if (((((!s.equals(beginningOfDay) && !s.after(beginningOfDay) || !s.before(endOfDay)) || (!e.equals(beginningOfDay) && !e.after(beginningOfDay))) || (!e.equals(endOfDay) && !e.before(endOfDay))) || s.equals(e)) || e.before(s)) {
                    // ALERT
                    String t = "Warning";
                    String header = "Check Appointment Time";
                    String content = "That appointment is outside business hours or an invalid time.";

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(t);
                    alert.setHeaderText(header);
                    alert.setContentText(content);

                    Image image = new Image("/Model/calendarWarning.png");
                    ImageView imageView = new ImageView(image);
                    alert.setGraphic(imageView);
                    alert.showAndWait();
                    System.out.println("Invalid Appointment");
                } else {

                    // INSERT into MySQL
                    boolean result = false;

                    try {
                        CallableStatement cs = null;
                        String q = "{call UpdateAppointment(?,?,?,?,?,?,?,?,?,?,?)}";
                        Connection conn = DBConnection.getConnection();
                        cs = conn.prepareCall(q);
                        cs.setString(1, title);
                        cs.setString(2, description);
                        cs.setString(3, type);
                        cs.setString(4, customer);
                        cs.setString(5, contact);  // could change this to Master.getUser();
                        cs.setString(6, location);
                        cs.setString(7, start);
                        cs.setString(8, end);
                        cs.setString(9, url);
                        cs.setInt(10, appointmentId);
                        cs.setString(11, Master.getOffset());  // updated 7/24 in DB and here.

                        cs.executeQuery();
                        conn.close();

                        System.out.println("Query Complete");

                    } catch (SQLException ex) {
                        Logger.getLogger(AppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println(ex);
                    }

                    //Clears old appointmentTableView
                    Master.deleteAllAppointments();

                    // Populate Appointment TableView from Query
                    boolean isAdded = false;
                    try {
                        String offset = Master.getOffset();
                        String sql = "select appointmentId, title, description, type, customerName, contact, location,  date(convert_tz(start,'+0:00','" + offset + "')) date, DATE_FORMAT(convert_tz(start,'+0:00','" + offset + "'), '%H:%i') start, DATE_FORMAT(convert_tz(end,'+0:00','" + offset + "'), '%H:%i') end, url from appointment\n"
                                + "join customer on appointment.customerId = customer.customerId";
                        isAdded = new MYSQL().addAppointmentsFromQuery(sql);
                        System.out.println(isAdded);
                    } catch (Exception ex) {
                        Logger.getLogger(AppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("Error");
                    }// end catch

                    System.out.println("Valid Appointment");
                }// end else is during business hours
            }// end else is overlapping appointment
        }// end else if blank values
    }

    @FXML
    private void deleteButtonAction(ActionEvent event) throws IOException, Exception {

        System.out.println("delete button action");

        String appointmentId = String.valueOf(appointmentIdLabel.getText());
        if (appointmentId.equals("") || appointmentId == null || appointmentId.equals("appointmentIdLabel")) {
            // Do nothing
        } else {

            boolean result = false;
            try {
                CallableStatement cs = null;
                String q = "{Call DeleteAppointment(?)}";
                Connection conn = DBConnection.getConnection();
                cs = conn.prepareCall(q);
                cs.setString(1, appointmentId);

                cs.executeQuery();
                conn.close();

            } catch (SQLException ex) {
                Logger.getLogger(AppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
            }

            //Clears old customerTableView
            Master.deleteAllAppointments();

            // Populate Appointment TableView from Query
            boolean isAdded = false;
            try {
                String offset = Master.getOffset();
                String sql = "select appointmentId, title, description, type, customerName, contact, location,  date(convert_tz(start,'+0:00','" + offset + "')) date, DATE_FORMAT(convert_tz(start,'+0:00','" + offset + "'), '%H:%i') start, DATE_FORMAT(convert_tz(end,'+0:00','" + offset + "'), '%H:%i') end, url from appointment\n"
                        + "join customer on appointment.customerId = customer.customerId";
                isAdded = new MYSQL().addAppointmentsFromQuery(sql);
                System.out.println(isAdded);
            } catch (Exception ex) {
                Logger.getLogger(AppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error");
            }
        } // end if appointmentId is null
    }

    @FXML
    private void clearButtonAction(ActionEvent event) throws IOException {
        appointmentIdLabel.setText(null);
        titleTextField.setText(null);
        descriptionTextArea.setText(null);
        typeChoiceBox.setValue(null);
        customerChoiceBox.setValue(null);
        contactChoiceBox.setValue(null);
        locationChoiceBox.setValue(null);
        dateDatePicker.setValue(null);
        startChoiceBox.setValue(null);
        endChoiceBox.setValue(null);
        urlTextField.setText(null);
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

    // Helper functions
    // https://stackoverflow.com/questions/22890644/get-current-week-start-and-end-date-in-java-monday-to-sunday/22890763
    private LocalDate monday(LocalDate today) {
        // Go backward to get Monday
        LocalDate monday = today;
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }
        return monday;
    }

    private LocalDate friday(LocalDate today) {
        // Go forward to get Sunday
        LocalDate friday = today;
        while (friday.getDayOfWeek() != DayOfWeek.FRIDAY) {
            friday = friday.plusDays(1);
        }
        return friday;
    }

}
