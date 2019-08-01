/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.*;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author bscha
 */
public class MYSQL {

    private Statement stmt;
    private ResultSet rs;

    private ArrayList<String> headers = new ArrayList<String>();

    public boolean addCustomersFromQuery(String query) throws Exception {
        try {
            // opening database connection to MySQL server 
            Connection conn = DBConnection.getConnection();
            // getting Statement object to execute query 
            stmt = conn.createStatement();
            // executing SELECT query 
            rs = stmt.executeQuery(query);

            ResultSetMetaData rsmd = rs.getMetaData();

            int rsCols = getColumnCount(rs);

            while (rs.next()) {

                ArrayList<String> row = new ArrayList<String>();

                for (int i = 0; i < rsCols; i++) {
                    row.add(rs.getString(i + 1));
                }

                Master.addCustomer(new Customer(row));

            }

        } catch (Exception ex) {
            // do nothing
            System.out.println("Catch...");
            return false;
        }
        System.out.println("return...");
        DBConnection.closeConnection();
        return true;

    }

    public boolean addAppointmentsFromQuery(String query) throws Exception {
        try {
            // opening database connection to MySQL server 
            Connection conn = DBConnection.getConnection();
            // getting Statement object to execute query 
            stmt = conn.createStatement();
            // executing SELECT query 
            rs = stmt.executeQuery(query);

            ResultSetMetaData rsmd = rs.getMetaData();

            int rsCols = getColumnCount(rs);

            while (rs.next()) {

                ArrayList<String> row = new ArrayList<>();

                for (int i = 0; i < rsCols; i++) {
                    row.add(rs.getString(i + 1));
                }

                Master.addAppointment(new Appointment(row));
            }

        } catch (Exception ex) {
            // do nothing
            System.out.println("Catch...");
            return false;
        }
        System.out.println("return...");
        DBConnection.closeConnection();
        return true;
    }

    public ObservableList<ArrayList> query(String query) throws Exception {

        ObservableList<ArrayList> table = FXCollections.observableArrayList();

        System.out.println("Starting try...");
        try {
            // opening database connection to MySQL server 
            Connection conn = DBConnection.getConnection();
            // getting Statement object to execute query 
            stmt = conn.createStatement();
            // executing SELECT query 
            rs = stmt.executeQuery(query);

            ResultSetMetaData rsmd = rs.getMetaData();

            int rsCols = getColumnCount(rs);
//            int rsRows = getRowCount(rs);

            for (int i = 0; i < rsCols; i++) {
                String colName = rsmd.getColumnName(i + 1);
                headers.add(colName);
            }
            // Disabled adding headers
//            table.add(headers);

            while (rs.next()) {
                ArrayList<String> row = new ArrayList<String>();

                for (int i = 0; i < rsCols; i++) {
                    row.add(rs.getString(i + 1));
                }
                table.add(row);
            }

        } catch (Exception e) {
            // do nothing
            System.out.println("Catch...");
            System.out.println(e);
            return null;
        }
        System.out.println("return...");
        DBConnection.closeConnection();

        return table;
    }

    public static int getColumnCount(ResultSet resultSet) throws SQLException {
        return resultSet.getMetaData().getColumnCount();
    }

    public static int getRowCount(ResultSet resultSet) {
        int size = 0;

        try {
            resultSet.last();
            size = resultSet.getRow();
            resultSet.beforeFirst();
        } catch (SQLException ex) {
            return 0;
        }
        return size;
    }

}
