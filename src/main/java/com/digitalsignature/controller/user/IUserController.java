/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.controller.user;

import com.digitalsignature.model.ModelUserInfomation;

/**
 *
 * @author Admin
 */
public interface IUserController {
    void displayUserInfomation(ModelUserInfomation uinfo);
    void authenticationUser(String password);
    void changePassword(String passwordBcrypted, String serial);
    void generateFormForgetPassword();
}
