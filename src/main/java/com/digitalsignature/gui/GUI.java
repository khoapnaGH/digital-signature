/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.gui;

import com.digitalsignature.common.ViewHelper;
import java.awt.Color;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Admin
 */
public class GUI extends javax.swing.JFrame {

    private ViewHelper viewHelper = new ViewHelper();
    private Map<JButton, JPanel> mapBtnToPanel = new HashMap<JButton, JPanel>();

    public GUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        UIManager.put("PopupMenu.border", BorderFactory.createEmptyBorder());
        initComponents();
        URL iconURL = getClass().getResource("/images/logopng.png");

        // iconURL is null when not found
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());

        initCustomButton();
        initMapBtnToPanel();
        viewHelper.addJPanelToAnotherJPanel(panelChildContent, panelLogin);
    }

    public void enterSignatureTab() {
        viewHelper.addJPanelToAnotherJPanel(panelChildContent, panelSignature);
    }

    public void initCustomButton() {
        List<JButton> btnsFuncs = viewHelper.getJButtonFromPanel(panelFunctions);
        List<JButton> btnsLogin = viewHelper.getJButtonFromPanel(panelLogin);
        List<JButton> btnsYourKeys = viewHelper.getJButtonFromPanel(panelYourKeys);
        List<JButton> btnsYourFrKeys = viewHelper.getJButtonFromPanel(panelYourFriendKeys);
        List<JButton> btnsSignature = viewHelper.getJButtonFromPanel(panelSignature);
        List<JButton> btnsVerify = viewHelper.getJButtonFromPanel(panelVerify);
        List<JButton> btnsChangePass = viewHelper.getJButtonFromPanel(panelChangePass);

        viewHelper.setEffectButton(btnsFuncs, new Color(0, 102, 204), new Color(255, 255, 255), new Color(51, 102, 255));
        viewHelper.setEffectButton(btnsLogin, new Color(0, 102, 204), new Color(51, 105, 225), new Color(0, 153, 255));
        viewHelper.setEffectButton(btnsYourKeys, new Color(0, 153, 204), new Color(0, 155, 209), new Color(102, 153, 255));
        viewHelper.setEffectButton(btnsYourFrKeys, new Color(0, 153, 204), new Color(0, 155, 209), new Color(102, 153, 255));
        viewHelper.setEffectButton(btnsSignature, new Color(153, 153, 255), new Color(150, 150, 254), new Color(51, 102, 255));
        viewHelper.setEffectButton(btnsVerify, new Color(153, 153, 255), new Color(150, 150, 254), new Color(51, 102, 255));
        viewHelper.setEffectButton(btnsChangePass, new Color(153, 153, 255), new Color(150, 150, 254), new Color(51, 102, 255));
    }

    public void initMapBtnToPanel() {
        mapBtnToPanel.put(btnFuncLogin, panelLogin);
        mapBtnToPanel.put(btnFuncKeyManagement, panelKeysMana);
        mapBtnToPanel.put(btnFuncSign, panelSignature);
        mapBtnToPanel.put(btnFuncVerify, panelVerify);
        mapBtnToPanel.put(btnFuncSetting, panelSetting);

        // Trigger set content panel when click to button
        viewHelper.mapBtnToPanel(mapBtnToPanel, panelChildContent);
    }

    public Map<JButton, JPanel> getMapBtnToPanel() {
        return mapBtnToPanel;
    }

    public void setMapBtnToPanel(Map<JButton, JPanel> mapBtnToPanel) {
        this.mapBtnToPanel = mapBtnToPanel;
    }

    public JButton getBtnDoubleSign() {
        return bntDoubleSign;
    }

    public JButton getBtnDoubleVerify() {
        return bntDoubleVerify;
    }

    public JButton getBtnChangePass() {
        return btnChangePass;
    }

    public JButton getBtnDelFriendKey() {
        return btnDelFriendKey;
    }

    public JButton getBtnDelKey() {
        return btnDelKey;
    }

    public JButton getBtnExKey() {
        return btnExKey;
    }

    public JButton getBtnFuncKeyManagement() {
        return btnFuncKeyManagement;
    }

    public JButton getBtnFuncLogin() {
        return btnFuncLogin;
    }

    public JButton getBtnFuncSetting() {
        return btnFuncSetting;
    }

    public JButton getBtnFuncSign() {
        return btnFuncSign;
    }

    public JButton getBtnFuncVerify() {
        return btnFuncVerify;
    }

    public JButton getBtnGenForm() {
        return btnGenForm;
    }

    public JButton getBtnGenKey() {
        return btnGenKey;
    }

    public JButton getBtnImpPubKey() {
        return btnImpPubKey;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public JButton getBtnSign() {
        return btnSign;
    }

    public JButton getBtnVerify() {
        return btnVerify;
    }

    public JComboBox<String> getCbbAlgorithm() {
        return cbbAlgorithm;
    }

    public JComboBox<String> getCbbFriendKeyVerify() {
        return cbbFriendKeyVerify;
    }

    public JComboBox<String> getCbbHash() {
        return cbbHash;
    }

    public JCheckBox getCheckboxVerify() {
        return checkboxVerify;
    }

    public void setCheckboxVerify(JCheckBox checkboxVerify) {
        this.checkboxVerify = checkboxVerify;
    }

    public JComboBox<String> getCbbKeySize() {
        return cbbKeySize;
    }

    public JComboBox<String> getCbbYourKey() {
        return cbbYourKey;
    }

    public JLabel getLblAboutUsBg() {
        return lblAboutUsBg;
    }

    public void setLblAboutUsBg(JLabel lblAboutUsBg) {
        this.lblAboutUsBg = lblAboutUsBg;
    }

    public JLabel getLblAccName() {
        return lblAccName;
    }

    public void setLblAccName(JLabel lblAccName) {
        this.lblAccName = lblAccName;
    }

    public JLabel getLblActivateDate() {
        return lblActivateDate;
    }

    public JLabel getLblAddress() {
        return lblAddress;
    }

    public JLabel getLblChangePassBg() {
        return lblChangePassBg;
    }

    public JLabel getLblMail() {
        return lblMail;
    }

    public JLabel getLblOrag() {
        return lblOrag;
    }

    public void setLblOrag(JLabel lblOrag) {
        this.lblOrag = lblOrag;
    }

    public JLabel getLblSelFileToSign() {
        return lblSelFileToSign;
    }

    public JLabel getLblSelFileToVerify() {
        return lblSelFileToVerify;
    }

    public JLabel getLblSerial() {
        return lblSerial;
    }

    public JLabel getLblTimeGenYourKey() {
        return lblTimeGenYourKey;
    }

    public JLabel getLblaccInfoBg() {
        return lblaccInfoBg;
    }

    public JPanel getPanelAboutUs() {
        return panelAboutUs;
    }

    public JPanel getPanelAccInfo() {
        return panelAccInfo;
    }

    public JPanel getPanelChangePass() {
        return panelChangePass;
    }

    public JPanel getPanelChildContent() {
        return panelChildContent;
    }

    public JPanel getPanelContents() {
        return panelContents;
    }

    public JPanel getPanelFunctions() {
        return panelFunctions;
    }

    public JPanel getPanelKeysMana() {
        return panelKeysMana;
    }

    public JPanel getPanelLogin() {
        return panelLogin;
    }

    public JPanel getPanelRoot() {
        return panelRoot;
    }

    public JPanel getPanelSetting() {
        return panelSetting;
    }

    public JPanel getPanelSignature() {
        return panelSignature;
    }

    public JPanel getPanelVerify() {
        return panelVerify;
    }

    public JPanel getPanelYourFriendKeys() {
        return panelYourFriendKeys;
    }

    public JPanel getPanelYourKeys() {
        return panelYourKeys;
    }

    public JPopupMenu getPopupExSign() {
        return popupExSign;
    }

    public JTabbedPane getTabpaneSetting() {
        return tabpaneSetting;
    }

    public JTable getTblFriendKeys() {
        return tblFriendKeys;
    }

    public JTable getTblSign() {
        return tblSign;
    }

    public JTable getTblVerify() {
        return tblVerify;
    }

    public JTable getTblYourKey() {
        return tblYourKey;
    }

    public JPasswordField getTxtPassToChange() {
        return txtPassToChange;
    }

    public JPasswordField getTxtPassWannaChange() {
        return txtPassWannaChange;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public JMenuItem getMenuItemExSign_Option1() {
        return menuItemExSign_Option1;
    }

    public JMenuItem getMenuItemExSign_Option3() {
        return menuItemExSign_Option3;
    }

    public JTabbedPane getTabbedPaneKeyManagenment() {
        return tabbedPaneKeyManagenment;
    }

    public JMenuItem getMenuItemExSign_Option4() {
        return menuItemExSign_Option4;
    }

    public JMenuItem getMenuItemVerifyOpenFolder() {
        return menuItemVerifyOpenFolder;
    }

    public JPopupMenu getPopupVerifySign() {
        return popupVerifySign;
    }

    public JMenuItem getMenuItemVerifyRemoveFile() {
        return menuItemVerifyRemoveFile;
    }

    public JMenuItem getMenuItemSignOpenFolder() {
        return menuItemSignOpenFolder;
    }

    public JTextArea getTxtContent() {
        return txtContent;
    }

    public JTextField getTxtSubject() {
        return txtSubject;
    }

    public JDialog getFormForgetPass() {
        return formForgetPass;
    }

    // Init generate code GUI
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLogin = new javax.swing.JPanel();
        btnLogin = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        panelKeysMana = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        tabbedPaneKeyManagenment = new javax.swing.JTabbedPane();
        panelYourKeys = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cbbAlgorithm = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cbbKeySize = new javax.swing.JComboBox<>();
        btnExKey = new javax.swing.JButton();
        btnGenKey = new javax.swing.JButton();
        btnDelKey = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblYourKey = new javax.swing.JTable();
        lblTimeGenYourKey = new javax.swing.JLabel();
        panelYourFriendKeys = new javax.swing.JPanel();
        btnDelFriendKey = new javax.swing.JButton();
        btnImpPubKey = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblFriendKeys = new javax.swing.JTable();
        panelSignature = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        lblSelFileToSign = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbbHash = new javax.swing.JComboBox<>();
        bntDoubleSign = new javax.swing.JButton();
        btnSign = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSign = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        cbbYourKey = new javax.swing.JComboBox<>();
        panelVerify = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        lblSelFileToVerify = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cbbFriendKeyVerify = new javax.swing.JComboBox<>();
        bntDoubleVerify = new javax.swing.JButton();
        btnVerify = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblVerify = new javax.swing.JTable();
        checkboxVerify = new javax.swing.JCheckBox();
        panelSetting = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        tabpaneSetting = new javax.swing.JTabbedPane();
        panelAccInfo = new javax.swing.JPanel();
        lblActivateDate = new javax.swing.JLabel();
        lblAccName = new javax.swing.JLabel();
        lblMail = new javax.swing.JLabel();
        lblOrag = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        lblaccInfoBg = new javax.swing.JLabel();
        panelChangePass = new javax.swing.JPanel();
        btnGenForm = new javax.swing.JButton();
        btnChangePass = new javax.swing.JButton();
        txtPassWannaChange = new javax.swing.JPasswordField();
        txtPassToChange = new javax.swing.JPasswordField();
        lblChangePassBg = new javax.swing.JLabel();
        lblSerial = new javax.swing.JLabel();
        panelAboutUs = new javax.swing.JPanel();
        lblAboutUsBg = new javax.swing.JLabel();
        popupExSign = new javax.swing.JPopupMenu();
        menuItemExSign_Option1 = new javax.swing.JMenuItem();
        menuItemExSign_Option3 = new javax.swing.JMenuItem();
        menuItemExSign_Option4 = new javax.swing.JMenuItem();
        menuItemSignOpenFolder = new javax.swing.JMenuItem();
        popupVerifySign = new javax.swing.JPopupMenu();
        menuItemVerifyOpenFolder = new javax.swing.JMenuItem();
        menuItemVerifyRemoveFile = new javax.swing.JMenuItem();
        formForgetPass = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtSubject = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtContent = new javax.swing.JTextArea();
        panelRoot = new javax.swing.JPanel();
        panelFunctions = new javax.swing.JPanel();
        btnFuncLogin = new javax.swing.JButton();
        btnFuncKeyManagement = new javax.swing.JButton();
        btnFuncSign = new javax.swing.JButton();
        btnFuncVerify = new javax.swing.JButton();
        btnFuncSetting = new javax.swing.JButton();
        panelContents = new javax.swing.JPanel();
        panelChildContent = new javax.swing.JPanel();

        panelLogin.setBackground(new java.awt.Color(255, 255, 255));
        panelLogin.setMaximumSize(new java.awt.Dimension(500, 400));
        panelLogin.setMinimumSize(new java.awt.Dimension(500, 400));
        panelLogin.setPreferredSize(new java.awt.Dimension(500, 400));
        panelLogin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnLogin.setBackground(new java.awt.Color(0, 153, 255));
        btnLogin.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/key_2_20px.png"))); // NOI18N
        btnLogin.setText("Login");
        btnLogin.setBorderPainted(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setMaximumSize(new java.awt.Dimension(170, 40));
        btnLogin.setMinimumSize(new java.awt.Dimension(170, 40));
        btnLogin.setOpaque(true);
        btnLogin.setPreferredSize(new java.awt.Dimension(170, 40));
        panelLogin.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 330, -1, -1));

        txtPassword.setForeground(new java.awt.Color(0, 102, 204));
        txtPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPassword.setToolTipText("Enter Your Password");
        txtPassword.setBorder(null);
        txtPassword.setMaximumSize(new java.awt.Dimension(220, 40));
        txtPassword.setMinimumSize(new java.awt.Dimension(220, 40));
        txtPassword.setPreferredSize(new java.awt.Dimension(220, 40));
        panelLogin.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 255, 200, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Login.png"))); // NOI18N
        jLabel1.setAlignmentX(0.5F);
        panelLogin.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        panelLogin.getAccessibleContext().setAccessibleParent(panelChildContent);

        panelKeysMana.setBackground(new java.awt.Color(255, 255, 255));
        panelKeysMana.setMaximumSize(new java.awt.Dimension(500, 400));
        panelKeysMana.setMinimumSize(new java.awt.Dimension(500, 400));
        panelKeysMana.setPreferredSize(new java.awt.Dimension(500, 400));
        panelKeysMana.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/key_20px.png"))); // NOI18N
        jLabel2.setText("KEYS MANAGEMENT");
        jLabel2.setMaximumSize(new java.awt.Dimension(500, 30));
        jLabel2.setMinimumSize(new java.awt.Dimension(500, 30));
        jLabel2.setPreferredSize(new java.awt.Dimension(500, 30));
        panelKeysMana.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jSeparator1.setForeground(new java.awt.Color(0, 102, 255));
        panelKeysMana.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 230, -1));

        tabbedPaneKeyManagenment.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        tabbedPaneKeyManagenment.setMaximumSize(new java.awt.Dimension(500, 360));
        tabbedPaneKeyManagenment.setMinimumSize(new java.awt.Dimension(500, 360));
        tabbedPaneKeyManagenment.setPreferredSize(new java.awt.Dimension(500, 360));

        panelYourKeys.setBackground(new java.awt.Color(255, 255, 255));
        panelYourKeys.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel6.setText("Algorithm");
        panelYourKeys.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 60, 20));

        cbbAlgorithm.setEditable(true);
        cbbAlgorithm.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        cbbAlgorithm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DSA", "RSA" }));
        panelYourKeys.add(cbbAlgorithm, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 60, -1));

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel7.setText("Key Size");
        panelYourKeys.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 60, 20));

        cbbKeySize.setEditable(true);
        cbbKeySize.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        cbbKeySize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1024", "2048", "512" }));
        panelYourKeys.add(cbbKeySize, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 60, -1));

        btnExKey.setBackground(new java.awt.Color(51, 102, 255));
        btnExKey.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnExKey.setForeground(new java.awt.Color(255, 255, 255));
        btnExKey.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lease_15px.png"))); // NOI18N
        btnExKey.setText("Export Key Pair");
        btnExKey.setContentAreaFilled(false);
        btnExKey.setOpaque(true);
        panelYourKeys.add(btnExKey, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, 140, 30));

        btnGenKey.setBackground(new java.awt.Color(51, 102, 255));
        btnGenKey.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnGenKey.setForeground(new java.awt.Color(255, 255, 255));
        btnGenKey.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/key_2_15px.png"))); // NOI18N
        btnGenKey.setText("Generate Key Pair");
        btnGenKey.setContentAreaFilled(false);
        btnGenKey.setOpaque(true);
        panelYourKeys.add(btnGenKey, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 160, 30));

        btnDelKey.setBackground(new java.awt.Color(51, 102, 255));
        btnDelKey.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnDelKey.setForeground(new java.awt.Color(255, 255, 255));
        btnDelKey.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remove_key_15px.png"))); // NOI18N
        btnDelKey.setText("Delete Key Pair");
        btnDelKey.setContentAreaFilled(false);
        btnDelKey.setOpaque(true);
        panelYourKeys.add(btnDelKey, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 150, 30));

        tblYourKey.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        tblYourKey.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Name", "Algorithm", "Key Size"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblYourKey.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblYourKey.setRowHeight(20);
        jScrollPane1.setViewportView(tblYourKey);

        panelYourKeys.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 500, 230));

        lblTimeGenYourKey.setFont(new java.awt.Font("SansSerif", 3, 12)); // NOI18N
        lblTimeGenYourKey.setForeground(new java.awt.Color(0, 102, 255));
        lblTimeGenYourKey.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelYourKeys.add(lblTimeGenYourKey, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 150, 20));

        tabbedPaneKeyManagenment.addTab("Your Keys", new javax.swing.ImageIcon(getClass().getResource("/images/user_15px.png")), panelYourKeys, "Manage Your Public and Private Keys"); // NOI18N

        panelYourFriendKeys.setBackground(new java.awt.Color(255, 255, 255));
        panelYourFriendKeys.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnDelFriendKey.setBackground(new java.awt.Color(51, 102, 255));
        btnDelFriendKey.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnDelFriendKey.setForeground(new java.awt.Color(255, 255, 255));
        btnDelFriendKey.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remove_key_15px.png"))); // NOI18N
        btnDelFriendKey.setText("Delete Key");
        btnDelFriendKey.setContentAreaFilled(false);
        btnDelFriendKey.setOpaque(true);
        panelYourFriendKeys.add(btnDelFriendKey, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, -1, 30));

        btnImpPubKey.setBackground(new java.awt.Color(51, 102, 255));
        btnImpPubKey.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnImpPubKey.setForeground(new java.awt.Color(255, 255, 255));
        btnImpPubKey.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/import_15px.png"))); // NOI18N
        btnImpPubKey.setText("Import Public Key");
        btnImpPubKey.setContentAreaFilled(false);
        btnImpPubKey.setOpaque(true);
        panelYourFriendKeys.add(btnImpPubKey, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, 30));

        tblFriendKeys.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        tblFriendKeys.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Name", "Note"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblFriendKeys.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblFriendKeys.setRowHeight(20);
        jScrollPane2.setViewportView(tblFriendKeys);

        panelYourFriendKeys.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 500, 270));

        tabbedPaneKeyManagenment.addTab("Your Friend Keys", new javax.swing.ImageIcon(getClass().getResource("/images/group_15px.png")), panelYourFriendKeys); // NOI18N

        panelKeysMana.add(tabbedPaneKeyManagenment, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, -1, -1));

        panelSignature.setBackground(new java.awt.Color(255, 255, 255));
        panelSignature.setMaximumSize(new java.awt.Dimension(500, 400));
        panelSignature.setMinimumSize(new java.awt.Dimension(500, 400));
        panelSignature.setPreferredSize(new java.awt.Dimension(500, 400));
        panelSignature.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hand_with_pen_20px.png"))); // NOI18N
        jLabel3.setText("SIGN");
        jLabel3.setMaximumSize(new java.awt.Dimension(500, 30));
        jLabel3.setMinimumSize(new java.awt.Dimension(500, 30));
        jLabel3.setPreferredSize(new java.awt.Dimension(500, 30));
        panelSignature.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jSeparator2.setForeground(new java.awt.Color(0, 102, 255));
        panelSignature.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 80, -1));

        lblSelFileToSign.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Component 1 – 1.png"))); // NOI18N
        lblSelFileToSign.setToolTipText("Select or Drag drop files here");
        lblSelFileToSign.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSignature.add(lblSelFileToSign, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 44, 500, 90));

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel11.setText("Hash Algorithm");
        panelSignature.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, 100, 30));

        cbbHash.setEditable(true);
        cbbHash.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        cbbHash.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SHA512", "SHA256" }));
        panelSignature.add(cbbHash, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 150, 80, 30));

        bntDoubleSign.setBackground(new java.awt.Color(51, 102, 255));
        bntDoubleSign.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        bntDoubleSign.setForeground(new java.awt.Color(255, 255, 255));
        bntDoubleSign.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hand_with_pen_color_20px.png"))); // NOI18N
        bntDoubleSign.setText("Double Sign");
        bntDoubleSign.setContentAreaFilled(false);
        bntDoubleSign.setOpaque(true);
        panelSignature.add(bntDoubleSign, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 130, 30));

        btnSign.setBackground(new java.awt.Color(51, 102, 255));
        btnSign.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnSign.setForeground(new java.awt.Color(255, 255, 255));
        btnSign.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hand_with_pen_color_20px.png"))); // NOI18N
        btnSign.setText("Sign");
        btnSign.setContentAreaFilled(false);
        btnSign.setOpaque(true);
        panelSignature.add(btnSign, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 110, 30));

        tblSign.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        tblSign.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "File name", "Event Status", "Speed"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSign.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblSign.setRowHeight(25);
        jScrollPane3.setViewportView(tblSign);

        panelSignature.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 500, 170));

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel12.setText("Your Keys");
        panelSignature.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 70, 30));

        cbbYourKey.setEditable(true);
        cbbYourKey.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        panelSignature.add(cbbYourKey, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 160, 30));

        panelVerify.setBackground(new java.awt.Color(255, 255, 255));
        panelVerify.setMaximumSize(new java.awt.Dimension(500, 400));
        panelVerify.setMinimumSize(new java.awt.Dimension(500, 400));
        panelVerify.setPreferredSize(new java.awt.Dimension(500, 400));
        panelVerify.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/check_file_20px.png"))); // NOI18N
        jLabel4.setText("Verify Signature");
        jLabel4.setMaximumSize(new java.awt.Dimension(500, 30));
        jLabel4.setMinimumSize(new java.awt.Dimension(500, 30));
        jLabel4.setPreferredSize(new java.awt.Dimension(500, 30));
        panelVerify.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jSeparator3.setForeground(new java.awt.Color(0, 102, 255));
        panelVerify.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 160, -1));

        lblSelFileToVerify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Component 1 – 1.png"))); // NOI18N
        lblSelFileToVerify.setToolTipText("Select or Drag drop files here");
        lblSelFileToVerify.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelVerify.add(lblSelFileToVerify, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 44, 500, 90));

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel13.setText("Friend Key");
        panelVerify.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 60, 30));

        cbbFriendKeyVerify.setEditable(true);
        cbbFriendKeyVerify.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        panelVerify.add(cbbFriendKeyVerify, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 160, 30));

        bntDoubleVerify.setBackground(new java.awt.Color(51, 102, 255));
        bntDoubleVerify.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        bntDoubleVerify.setForeground(new java.awt.Color(255, 255, 255));
        bntDoubleVerify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/certificate_20px.png"))); // NOI18N
        bntDoubleVerify.setText("Double Verify");
        bntDoubleVerify.setContentAreaFilled(false);
        bntDoubleVerify.setOpaque(true);
        panelVerify.add(bntDoubleVerify, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 140, 30));

        btnVerify.setBackground(new java.awt.Color(51, 102, 255));
        btnVerify.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnVerify.setForeground(new java.awt.Color(255, 255, 255));
        btnVerify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/certificate_20px.png"))); // NOI18N
        btnVerify.setText("Signle Verify");
        btnVerify.setContentAreaFilled(false);
        btnVerify.setOpaque(true);
        panelVerify.add(btnVerify, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 130, 30));

        tblVerify.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        tblVerify.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "File name", "Event Status", "Speed"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVerify.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblVerify.setRowHeight(25);
        jScrollPane4.setViewportView(tblVerify);

        panelVerify.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 500, 170));

        checkboxVerify.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        checkboxVerify.setText("Using public key in input");
        checkboxVerify.setContentAreaFilled(false);
        checkboxVerify.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        checkboxVerify.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelVerify.add(checkboxVerify, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, 190, 30));

        panelSetting.setBackground(new java.awt.Color(255, 255, 255));
        panelSetting.setMaximumSize(new java.awt.Dimension(500, 400));
        panelSetting.setMinimumSize(new java.awt.Dimension(500, 400));
        panelSetting.setPreferredSize(new java.awt.Dimension(500, 400));
        panelSetting.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/check_file_20px.png"))); // NOI18N
        jLabel8.setText("Setting");
        jLabel8.setMaximumSize(new java.awt.Dimension(500, 30));
        jLabel8.setMinimumSize(new java.awt.Dimension(500, 30));
        jLabel8.setPreferredSize(new java.awt.Dimension(500, 30));
        panelSetting.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jSeparator4.setForeground(new java.awt.Color(0, 102, 255));
        panelSetting.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 160, -1));

        tabpaneSetting.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        tabpaneSetting.setMaximumSize(new java.awt.Dimension(500, 350));
        tabpaneSetting.setMinimumSize(new java.awt.Dimension(500, 350));
        tabpaneSetting.setPreferredSize(new java.awt.Dimension(500, 350));

        panelAccInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelAccInfo.setMaximumSize(new java.awt.Dimension(500, 350));
        panelAccInfo.setMinimumSize(new java.awt.Dimension(500, 350));
        panelAccInfo.setPreferredSize(new java.awt.Dimension(500, 350));
        panelAccInfo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblActivateDate.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lblActivateDate.setText("14 / 04 / 2020");
        lblActivateDate.setAlignmentX(0.5F);
        panelAccInfo.add(lblActivateDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 295, 320, 20));

        lblAccName.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lblAccName.setText("Bùi Minh Quang");
        panelAccInfo.add(lblAccName, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 34, 320, 20));

        lblMail.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lblMail.setText("quangbui14041999@gmail.com");
        panelAccInfo.add(lblMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 320, 20));

        lblOrag.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lblOrag.setText("Trường Đại học Công Nghiệp Thực Phẩm TPHCM");
        panelAccInfo.add(lblOrag, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 165, 320, 20));

        lblAddress.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lblAddress.setText("140 Lê Trọng Tấn, Tây Thạnh, Tân Phú, TPHCM");
        panelAccInfo.add(lblAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, 320, 20));

        lblaccInfoBg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblaccInfoBg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/accountinfo.png"))); // NOI18N
        lblaccInfoBg.setAlignmentX(0.5F);
        panelAccInfo.add(lblaccInfoBg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 322));

        tabpaneSetting.addTab("Account Infomation", panelAccInfo);

        panelChangePass.setBackground(new java.awt.Color(255, 255, 255));
        panelChangePass.setMaximumSize(new java.awt.Dimension(500, 350));
        panelChangePass.setMinimumSize(new java.awt.Dimension(500, 350));
        panelChangePass.setPreferredSize(new java.awt.Dimension(500, 350));
        panelChangePass.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGenForm.setBackground(new java.awt.Color(51, 102, 255));
        btnGenForm.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnGenForm.setForeground(new java.awt.Color(255, 255, 255));
        btnGenForm.setText("Generate Form");
        btnGenForm.setContentAreaFilled(false);
        btnGenForm.setOpaque(true);
        panelChangePass.add(btnGenForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 83, 140, 30));

        btnChangePass.setBackground(new java.awt.Color(51, 102, 255));
        btnChangePass.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnChangePass.setForeground(new java.awt.Color(255, 255, 255));
        btnChangePass.setText("Change");
        btnChangePass.setContentAreaFilled(false);
        btnChangePass.setOpaque(true);
        panelChangePass.add(btnChangePass, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 23, 140, 30));

        txtPassWannaChange.setForeground(new java.awt.Color(0, 153, 255));
        txtPassWannaChange.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPassWannaChange.setBorder(null);
        txtPassWannaChange.setMaximumSize(new java.awt.Dimension(200, 23));
        txtPassWannaChange.setMinimumSize(new java.awt.Dimension(200, 23));
        txtPassWannaChange.setPreferredSize(new java.awt.Dimension(200, 23));
        panelChangePass.add(txtPassWannaChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 85, -1, -1));

        txtPassToChange.setForeground(new java.awt.Color(0, 153, 255));
        txtPassToChange.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPassToChange.setBorder(null);
        txtPassToChange.setMaximumSize(new java.awt.Dimension(200, 23));
        txtPassToChange.setMinimumSize(new java.awt.Dimension(200, 23));
        txtPassToChange.setPreferredSize(new java.awt.Dimension(200, 23));
        panelChangePass.add(txtPassToChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 27, -1, -1));

        lblChangePassBg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/change password.png"))); // NOI18N
        lblChangePassBg.setText("jLabel5");
        lblChangePassBg.setMaximumSize(new java.awt.Dimension(495, 350));
        lblChangePassBg.setMinimumSize(new java.awt.Dimension(495, 350));
        lblChangePassBg.setPreferredSize(new java.awt.Dimension(495, 350));
        panelChangePass.add(lblChangePassBg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 320));

        lblSerial.setText("jLabel5");
        lblSerial.setMaximumSize(new java.awt.Dimension(400, 20));
        lblSerial.setMinimumSize(new java.awt.Dimension(400, 20));
        lblSerial.setPreferredSize(new java.awt.Dimension(400, 20));
        panelChangePass.add(lblSerial, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, -1, -1));

        tabpaneSetting.addTab("Change Password", panelChangePass);

        panelAboutUs.setBackground(new java.awt.Color(255, 255, 255));
        panelAboutUs.setMaximumSize(new java.awt.Dimension(500, 350));
        panelAboutUs.setMinimumSize(new java.awt.Dimension(500, 350));
        panelAboutUs.setPreferredSize(new java.awt.Dimension(500, 350));
        panelAboutUs.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAboutUsBg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/about us.png"))); // NOI18N
        panelAboutUs.add(lblAboutUsBg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, -1));

        tabpaneSetting.addTab("About Us", panelAboutUs);

        panelSetting.add(tabpaneSetting, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, -1, -1));

        popupExSign.setBackground(new java.awt.Color(255, 255, 255));

        menuItemExSign_Option1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        menuItemExSign_Option1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/export_20px.png"))); // NOI18N
        menuItemExSign_Option1.setText("Export Signature, File, Public Key");
        menuItemExSign_Option1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        popupExSign.add(menuItemExSign_Option1);

        menuItemExSign_Option3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        menuItemExSign_Option3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/export_20px.png"))); // NOI18N
        menuItemExSign_Option3.setText("Export Signature, File");
        menuItemExSign_Option3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        popupExSign.add(menuItemExSign_Option3);

        menuItemExSign_Option4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        menuItemExSign_Option4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remove_20px.png"))); // NOI18N
        menuItemExSign_Option4.setText("Remove file");
        menuItemExSign_Option4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        popupExSign.add(menuItemExSign_Option4);

        menuItemSignOpenFolder.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        menuItemSignOpenFolder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/folder_20px.png"))); // NOI18N
        menuItemSignOpenFolder.setText("Open Folder");
        menuItemSignOpenFolder.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        popupExSign.add(menuItemSignOpenFolder);

        menuItemVerifyOpenFolder.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        menuItemVerifyOpenFolder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/folder_20px.png"))); // NOI18N
        menuItemVerifyOpenFolder.setText("Open Folder");
        menuItemVerifyOpenFolder.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        popupVerifySign.add(menuItemVerifyOpenFolder);

        menuItemVerifyRemoveFile.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        menuItemVerifyRemoveFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remove_20px.png"))); // NOI18N
        menuItemVerifyRemoveFile.setText("Remove file");
        menuItemVerifyRemoveFile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        popupVerifySign.add(menuItemVerifyRemoveFile);

        formForgetPass.setTitle("Form Forgot Password");
        formForgetPass.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        formForgetPass.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        formForgetPass.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setMaximumSize(new java.awt.Dimension(400, 260));
        jPanel1.setMinimumSize(new java.awt.Dimension(400, 260));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 260));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jLabel5.setText("Content");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, 20));

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel9.setText("Subject");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        txtSubject.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        jPanel1.add(txtSubject, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 370, 30));

        txtContent.setColumns(20);
        txtContent.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtContent.setRows(5);
        jScrollPane5.setViewportView(txtContent);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 370, 120));

        formForgetPass.getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Digital Signature Software");
        setMinimumSize(new java.awt.Dimension(600, 400));
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        panelRoot.setBackground(new java.awt.Color(255, 255, 255));
        panelRoot.setForeground(new java.awt.Color(255, 255, 255));
        panelRoot.setMaximumSize(new java.awt.Dimension(600, 400));
        panelRoot.setMinimumSize(new java.awt.Dimension(600, 400));
        panelRoot.setLayout(new java.awt.BorderLayout());

        panelFunctions.setBackground(new java.awt.Color(0, 153, 255));
        panelFunctions.setMaximumSize(new java.awt.Dimension(100, 400));
        panelFunctions.setMinimumSize(new java.awt.Dimension(100, 400));
        panelFunctions.setPreferredSize(new java.awt.Dimension(100, 400));
        panelFunctions.setLayout(new javax.swing.BoxLayout(panelFunctions, javax.swing.BoxLayout.PAGE_AXIS));

        btnFuncLogin.setBackground(new java.awt.Color(51, 102, 255));
        btnFuncLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user_shield_40px.png"))); // NOI18N
        btnFuncLogin.setToolTipText("Login");
        btnFuncLogin.setContentAreaFilled(false);
        btnFuncLogin.setMaximumSize(new java.awt.Dimension(100, 80));
        btnFuncLogin.setMinimumSize(new java.awt.Dimension(100, 80));
        btnFuncLogin.setOpaque(true);
        btnFuncLogin.setPreferredSize(new java.awt.Dimension(100, 80));
        panelFunctions.add(btnFuncLogin);

        btnFuncKeyManagement.setBackground(new java.awt.Color(51, 102, 255));
        btnFuncKeyManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/data_encryption_40px.png"))); // NOI18N
        btnFuncKeyManagement.setToolTipText("Keys Management");
        btnFuncKeyManagement.setContentAreaFilled(false);
        btnFuncKeyManagement.setMaximumSize(new java.awt.Dimension(100, 80));
        btnFuncKeyManagement.setMinimumSize(new java.awt.Dimension(100, 80));
        btnFuncKeyManagement.setOpaque(true);
        btnFuncKeyManagement.setPreferredSize(new java.awt.Dimension(100, 80));
        panelFunctions.add(btnFuncKeyManagement);

        btnFuncSign.setBackground(new java.awt.Color(51, 102, 255));
        btnFuncSign.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hand_with_pen_40px.png"))); // NOI18N
        btnFuncSign.setToolTipText("Signature");
        btnFuncSign.setContentAreaFilled(false);
        btnFuncSign.setMaximumSize(new java.awt.Dimension(100, 80));
        btnFuncSign.setMinimumSize(new java.awt.Dimension(100, 80));
        btnFuncSign.setOpaque(true);
        btnFuncSign.setPreferredSize(new java.awt.Dimension(100, 80));
        panelFunctions.add(btnFuncSign);

        btnFuncVerify.setBackground(new java.awt.Color(51, 102, 255));
        btnFuncVerify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/check_file_40px.png"))); // NOI18N
        btnFuncVerify.setToolTipText("Verify");
        btnFuncVerify.setContentAreaFilled(false);
        btnFuncVerify.setMaximumSize(new java.awt.Dimension(100, 80));
        btnFuncVerify.setMinimumSize(new java.awt.Dimension(100, 80));
        btnFuncVerify.setOpaque(true);
        btnFuncVerify.setPreferredSize(new java.awt.Dimension(100, 80));
        panelFunctions.add(btnFuncVerify);

        btnFuncSetting.setBackground(new java.awt.Color(51, 102, 255));
        btnFuncSetting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/settings_40px.png"))); // NOI18N
        btnFuncSetting.setToolTipText("Setting");
        btnFuncSetting.setContentAreaFilled(false);
        btnFuncSetting.setMaximumSize(new java.awt.Dimension(100, 80));
        btnFuncSetting.setMinimumSize(new java.awt.Dimension(100, 80));
        btnFuncSetting.setOpaque(true);
        btnFuncSetting.setPreferredSize(new java.awt.Dimension(100, 80));
        panelFunctions.add(btnFuncSetting);

        panelRoot.add(panelFunctions, java.awt.BorderLayout.WEST);

        panelContents.setBackground(new java.awt.Color(255, 255, 255));
        panelContents.setMaximumSize(new java.awt.Dimension(500, 400));
        panelContents.setMinimumSize(new java.awt.Dimension(500, 400));
        panelContents.setPreferredSize(new java.awt.Dimension(500, 400));
        panelContents.setLayout(new javax.swing.BoxLayout(panelContents, javax.swing.BoxLayout.PAGE_AXIS));

        panelChildContent.setBackground(new java.awt.Color(255, 255, 255));
        panelChildContent.setMaximumSize(new java.awt.Dimension(500, 400));
        panelChildContent.setMinimumSize(new java.awt.Dimension(500, 400));
        panelChildContent.setPreferredSize(new java.awt.Dimension(500, 400));
        panelChildContent.setLayout(new javax.swing.BoxLayout(panelChildContent, javax.swing.BoxLayout.PAGE_AXIS));
        panelContents.add(panelChildContent);

        panelRoot.add(panelContents, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelRoot);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntDoubleSign;
    private javax.swing.JButton bntDoubleVerify;
    private javax.swing.JButton btnChangePass;
    private javax.swing.JButton btnDelFriendKey;
    private javax.swing.JButton btnDelKey;
    private javax.swing.JButton btnExKey;
    private javax.swing.JButton btnFuncKeyManagement;
    private javax.swing.JButton btnFuncLogin;
    private javax.swing.JButton btnFuncSetting;
    private javax.swing.JButton btnFuncSign;
    private javax.swing.JButton btnFuncVerify;
    private javax.swing.JButton btnGenForm;
    private javax.swing.JButton btnGenKey;
    private javax.swing.JButton btnImpPubKey;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnSign;
    private javax.swing.JButton btnVerify;
    private javax.swing.JComboBox<String> cbbAlgorithm;
    private javax.swing.JComboBox<String> cbbFriendKeyVerify;
    private javax.swing.JComboBox<String> cbbHash;
    private javax.swing.JComboBox<String> cbbKeySize;
    private javax.swing.JComboBox<String> cbbYourKey;
    private javax.swing.JCheckBox checkboxVerify;
    private javax.swing.JDialog formForgetPass;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblAboutUsBg;
    private javax.swing.JLabel lblAccName;
    private javax.swing.JLabel lblActivateDate;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblChangePassBg;
    private javax.swing.JLabel lblMail;
    private javax.swing.JLabel lblOrag;
    private javax.swing.JLabel lblSelFileToSign;
    private javax.swing.JLabel lblSelFileToVerify;
    private javax.swing.JLabel lblSerial;
    private javax.swing.JLabel lblTimeGenYourKey;
    private javax.swing.JLabel lblaccInfoBg;
    private javax.swing.JMenuItem menuItemExSign_Option1;
    private javax.swing.JMenuItem menuItemExSign_Option3;
    private javax.swing.JMenuItem menuItemExSign_Option4;
    private javax.swing.JMenuItem menuItemSignOpenFolder;
    private javax.swing.JMenuItem menuItemVerifyOpenFolder;
    private javax.swing.JMenuItem menuItemVerifyRemoveFile;
    private javax.swing.JPanel panelAboutUs;
    private javax.swing.JPanel panelAccInfo;
    private javax.swing.JPanel panelChangePass;
    private javax.swing.JPanel panelChildContent;
    private javax.swing.JPanel panelContents;
    private javax.swing.JPanel panelFunctions;
    private javax.swing.JPanel panelKeysMana;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPanel panelRoot;
    private javax.swing.JPanel panelSetting;
    private javax.swing.JPanel panelSignature;
    private javax.swing.JPanel panelVerify;
    private javax.swing.JPanel panelYourFriendKeys;
    private javax.swing.JPanel panelYourKeys;
    private javax.swing.JPopupMenu popupExSign;
    private javax.swing.JPopupMenu popupVerifySign;
    private javax.swing.JTabbedPane tabbedPaneKeyManagenment;
    private javax.swing.JTabbedPane tabpaneSetting;
    private javax.swing.JTable tblFriendKeys;
    private javax.swing.JTable tblSign;
    private javax.swing.JTable tblVerify;
    private javax.swing.JTable tblYourKey;
    private javax.swing.JTextArea txtContent;
    private javax.swing.JPasswordField txtPassToChange;
    private javax.swing.JPasswordField txtPassWannaChange;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtSubject;
    // End of variables declaration//GEN-END:variables
}
