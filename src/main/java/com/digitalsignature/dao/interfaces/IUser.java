/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.dao.interfaces;

import com.digitalsignature.model.ModelUserInfomation;

/**
 *
 * @author Admin
 */
public interface IUser {
    String getPassword();
    ModelUserInfomation getUserInfomation();
    boolean savePassword(String passwordBcrypted, String serial);
}
