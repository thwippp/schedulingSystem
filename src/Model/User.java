/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.MYSQL;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author bscha
 */
public class User {

    private int userId;
    private String username;
    private String password;
    private boolean active;

    public User(int userId, String username, String password, boolean active) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.active = active;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ObservableList<ArrayList> matchUserOnUsername(String username) throws Exception {

        String sql = "select userId, userName, password, active from user \n"
                + "where username = '" + username + "'\n"
                + "limit 1;";

        ObservableList<ArrayList> result = (ObservableList<ArrayList>) new MYSQL().query(sql);
        System.out.println(result);
        return result;
    }

}
