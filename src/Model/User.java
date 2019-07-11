/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View_Controller.MYSQL;
import java.sql.SQLException;

/**
 *
 * @author bscha
 */
public class User {

    private static int userId;
    private static String username;
    private static String password;
    private static boolean active;

    public User(String username, String password, boolean active) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.active = active;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        User.userId = userId;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    public static boolean isActive() {
        return active;
    }

    public static void setActive(boolean active) {
        User.active = active;
    }

    public static void matchUserOnUsername(String username) throws SQLException {
        
        String sql = "select userId, userName, password, active from user \n"
                + "where username = 'test'\n"
                + "limit 1;";
        boolean result = MYSQL.query(sql);
        
        System.out.println(MYSQL.getTable());
        
    }

}
