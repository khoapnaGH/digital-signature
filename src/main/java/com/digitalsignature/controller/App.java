/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.controller;

import com.digitalsignature.gui.GUI;

/**
 *
 * @author Admin
 */
public class App {
    public static void main(String[] args) {
        GUI gui = new GUI();
        AppController controller = new AppController(gui);
        controller.initChildController();
        gui.setVisible(true);
//        System.out.println(System.getProperty("user.dir"));
    }
}
