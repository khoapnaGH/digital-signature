/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.dao.concrete;

import com.digitalsignature.dao.factory.DaoFactory;
import com.digitalsignature.dao.interfaces.IUser;
import com.digitalsignature.model.ModelUserInfomation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class UserDAO implements IUser {

    private static final String GET_PASS = "SELECT password FROM users";

    private static final String GET_INFO = "SELECT *FROM users";
    
    private static final String UPDATE_PASSWORD = "UPDATE users SET password = ? WHERE serial = ?";

    private DaoFactory dao;

    public UserDAO() {
        dao = new DaoFactory();
    }

    @Override
    public ModelUserInfomation getUserInfomation() {
        ModelUserInfomation ui = null;
        try {
            Connection conn = dao.getConnection();

            PreparedStatement stm = conn.prepareStatement(GET_INFO);

            ResultSet rset = stm.executeQuery();

            ui = getInfo(rset);

            stm.close();
            conn.close();
            
            return ui;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ui;
    }

    @Override
    public String getPassword() {
        try {
            Connection conn = dao.getConnection();

            PreparedStatement stm = conn.prepareStatement(GET_PASS);

            ResultSet rset = stm.executeQuery();

            String password = "";
            password = rset.getString("password");

            stm.close();
            conn.close();

            return password;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean savePassword(String passwordBcrypted, String serial) {
        try {
            Connection conn = dao.getConnection();
            
            PreparedStatement stm = conn.prepareStatement(UPDATE_PASSWORD);
            
            stm.setString(1, passwordBcrypted);
            stm.setString(2, serial);

            stm.executeUpdate();
            
            stm.close();
            conn.close();
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private ModelUserInfomation getInfo(ResultSet rset) {
        ModelUserInfomation ui = null;
        try {
            ui = new ModelUserInfomation(rset.getString("serial"),
                    rset.getString("name"),
                    rset.getString("email"),
                    rset.getString("organization"),
                    rset.getString("address"),
                    rset.getString("active"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ui;
    }

}
