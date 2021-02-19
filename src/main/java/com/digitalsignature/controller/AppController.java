/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.controller;

import com.digitalsignature.controller.sign.DigitalSignatureController;
import com.digitalsignature.controller.user.UserController;
import com.digitalsignature.gui.GUI;

/**
 *
 * @author Admin
 */
public class AppController {

    protected GUI gui = new GUI();

    public AppController(GUI gui) {
        this.gui = gui;
    }

    // Init UserController and SignatureController
    public void initChildController() {

        UserController uc = new UserController(this.gui);
        uc.initUserController();

        DigitalSignatureController dsc = new DigitalSignatureController(this.gui);
        dsc.initSignatureController();

    }

}
