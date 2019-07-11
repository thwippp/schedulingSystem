/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Customer;
import Model.Master;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author bscha
 */


// TODO-- DOES THIS HAVE TO BE STATIC?

public class MYSQL {

    // Connection credentials
    private static final String url = "jdbc:mysql://52.206.157.109:3306/U053iG";
    private static final String user = "U053iG";
    private static final String password = "53688415958";

    // Connection
    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;

    private static ArrayList<String> headers = new ArrayList<String>();

//    private static ArrayList<String> row = new ArrayList<String>();
    private static ObservableList<ArrayList> table = FXCollections.observableArrayList();

    public static boolean addCustomersFromQuery(String query) throws SQLException {
        try {
            // opening database connection to MySQL server 
            conn = DriverManager.getConnection(url, user, password);
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

        } catch (SQLException e) {
            // do nothing
            System.out.println("Catch...");
            return false;
        }
        System.out.println("return...");
        conn.close();
        return true;

    }

    public static boolean query(String query) throws SQLException {
        System.out.println("Starting try...");
        try {
            // opening database connection to MySQL server 
            conn = DriverManager.getConnection(url, user, password);
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

        } catch (SQLException e) {
            // do nothing
            System.out.println("Catch...");
            System.out.println(e);
            return false;
        }
        System.out.println("return...");
        conn.close();

        return true;

    }

    public static Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return con;
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

    public static ObservableList<ArrayList> getTable() {
        return table;
    }

    public static void setTable(ObservableList<ArrayList> table) {
        MYSQL.table = table;
    }
    
    

}
