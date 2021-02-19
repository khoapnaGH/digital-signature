/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.controller.user;

import com.digitalsignature.dao.concrete.UserDAO;
import com.digitalsignature.gui.GUI;
import com.digitalsignature.model.ModelUserInfomation;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Admin
 */
public class UserController implements IUserController {

    private GUI gui;
    private static UserDAO userDao = new UserDAO();
    private static String salt = "qU4n6_.Kh04";

    public UserController(GUI gui) {
        this.gui = gui;
    }

    public void initUserController() {
        initView();
        initEventHandle();
    }

    private void initView() {
        // Get User Infomation
        ModelUserInfomation uinfo = userDao.getUserInfomation();
        displayUserInfomation(uinfo);


        // disable panel until authentication success
        enableComponents(this.gui.getPanelYourKeys(), false);
        enableComponents(this.gui.getPanelYourFriendKeys(), false);
        enableComponents(this.gui.getPanelSignature(), false);
        enableComponents(this.gui.getPanelVerify(), false);
    }

    private void initEventHandle() {
        emptyTextField(this.gui.getTxtPassword());
        emptyTextField(this.gui.getTxtPassToChange());
        emptyTextField(this.gui.getTxtPassWannaChange());

        this.gui.getBtnLogin().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                authenticationUser(String.valueOf(gui.getTxtPassword().getPassword()));
            }
        });
        
        this.gui.getTxtPassword().addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt){
               if(evt.getKeyCode() == KeyEvent.VK_ENTER){
                    authenticationUser(String.valueOf(gui.getTxtPassword().getPassword()));
                }
            }
        });
        
        this.gui.getBtnChangePass().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt){
                String newPassword = new String(gui.getTxtPassToChange().getPassword());
                String serial = gui.getLblSerial().getText();
                if(newPassword.trim().isEmpty() || serial.isEmpty())
                {
                    JOptionPane.showMessageDialog(gui, 
                            "Password or Serial shouldn't empty!",
                            "Warning!",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                changePassword(newPassword, serial);
            }
        });
        
        this.gui.getBtnGenForm().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt){
                generateFormForgetPassword();
            }
        });
    }

    private void emptyTextField(JTextField txt) {
        txt.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                txt.setText("");
            }
        });
    }

    @Override
    public void displayUserInfomation(ModelUserInfomation uinfo) {
        this.gui.getLblAccName().setText(uinfo.getUserFullName());
        this.gui.getLblMail().setText(uinfo.getEmailContact());
        this.gui.getLblOrag().setText(uinfo.getOrganization());
        this.gui.getLblAddress().setText(uinfo.getAddress());
        this.gui.getLblActivateDate().setText(uinfo.getActiveDate());
        this.gui.getLblSerial().setText(uinfo.getSerial());
    }

    @Override
    public void authenticationUser(String passwordInput) {
//        String bcryptPwdInput = BCrypt.hashpw(passwordInput, BCrypt.gensalt(saltRounds));
        //System.out.println(passwordInput + salt);
        String passwordFromDB = userDao.getPassword();
        boolean valuate = BCrypt.checkpw(passwordInput + salt, passwordFromDB);
        String msgError = "Invalid password! \nIf you forget password please follow the instructions\nIn Setting -> Change Password tab";

        if (!valuate) {
            JOptionPane.showMessageDialog(gui, msgError, "Authentication Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        enableComponents(this.gui.getPanelYourKeys(), valuate);
        enableComponents(this.gui.getPanelYourFriendKeys(), valuate);
        enableComponents(this.gui.getPanelSignature(), valuate);
        enableComponents(this.gui.getPanelVerify(), valuate);
        this.gui.enterSignatureTab();
    }

    @Override
    public void changePassword(String passwordBcrypted, String serial) {
        boolean rsSavePass = userDao.savePassword(passwordBcrypted, serial);
        String msg = "Update new password successfully!";
        if(!rsSavePass){
            msg = "Update new password error";
            JOptionPane.showMessageDialog(gui, msg, "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(gui, msg, "Successfully", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }

    @Override
    public void generateFormForgetPassword() {
        String serial = this.gui.getLblSerial().getText();
        String fullName = this.gui.getLblAccName().getText();
        if(serial.isEmpty() || fullName.isEmpty()){
            JOptionPane.showMessageDialog(gui, 
                    "Serial or Fullname shouldn't empty!", 
                    "Warning!", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String passwordWannaChange = new String(this.gui.getTxtPassWannaChange().getPassword());
        if(passwordWannaChange.trim().isEmpty()){
            JOptionPane.showMessageDialog(gui, 
                    "Please enter password wanna change!", 
                    "Warning!", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String textContent = "My name is: "+fullName+"\n"
                            +"I wanna change my new password is\n"
                            +passwordWannaChange;
        this.gui.getTxtSubject().setText("Digial Signature Forgot Password - Case: "+serial);
        this.gui.getTxtContent().setText(textContent);
        this.gui.getFormForgetPass().setLocationRelativeTo(null);
        this.gui.getFormForgetPass().setPreferredSize(new Dimension(400, 280));
        this.gui.getFormForgetPass().setSize(new Dimension(400, 280));
        this.gui.getFormForgetPass().setVisible(true);
    }

    private void enableComponents(Container container, boolean enable) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setVisible(enable);
            if (component instanceof Container) {
                enableComponents((Container) component, enable);
            }
        }
    }

}
