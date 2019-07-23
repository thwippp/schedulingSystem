/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author bscha
 */
public class DBConnection {

    // Connection credentials
    private static final String url = "jdbc:mysql://52.206.157.109:3306/U053iG";
    private static final String user = "U053iG";
    private static final String password = "53688415958";
    private static final String driver = "com.mysql.jdbc.Driver";

    // Connection
    public static Connection conn;

    public static Connection getConnection() throws Exception {
        Class.forName(driver);
        conn = (Connection) DriverManager.getConnection(url, user, password);

        System.out.println("Connection Made!");
        return conn;
    }

    public static void closeConnection() throws Exception {
        conn.close();
        System.out.println("Connection closed.");
    }

}
