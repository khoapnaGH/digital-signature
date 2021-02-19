/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.controller.sign;

import static com.digitalsignature.controller.sign.DigitalSignatureController.helper;
import static com.digitalsignature.controller.sign.DigitalSignatureController.myKey;
import com.digitalsignature.gui.GUI;
import com.digitalsignature.model.ModelMyKey;
import com.digitalsignature.model.ModelResult;
import java.awt.Color;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class SignController extends DigitalSignatureController {

    private static List<ModelResult> listSignResult = new ArrayList<>();

    private int indexSignTable = 0;
    private String keyNameUsingSigned = "";
    private boolean isPasswordZip = false;
    
    public SignController(GUI gui) {
        super(gui);
    }

    public void init() {
        initViewer();
        initEventHandle();
    }

    private void initViewer() {
        // Set column size
        this.gui.getTblSign().getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        this.gui.getTblSign().getTableHeader().setForeground(new Color(149, 165, 166));
        this.gui.getTblSign().getColumnModel().getColumn(0).setPreferredWidth(50);
        this.gui.getTblSign().getColumnModel().getColumn(1).setPreferredWidth(205);
        this.gui.getTblSign().getColumnModel().getColumn(2).setPreferredWidth(163);
        this.gui.getTblSign().getColumnModel().getColumn(3).setPreferredWidth(80);
    }

    private void initEventHandle(){
                // Right Click to Show Export Popup 
        this.gui.getTblSign().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                if (SwingUtilities.isRightMouseButton(evt)) {
                    gui.getPopupExSign().show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
        });

        // Option1: Export Signature, File, My Public Key
        this.gui.getMenuItemExSign_Option1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportResultToZip((short) 1);
            }
        });

        // Option3: Export Signature, File
        this.gui.getMenuItemExSign_Option3().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportResultToZip((short) 2);
            }
        });

        // Option4: Delete
        this.gui.getMenuItemExSign_Option4().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeFiles();
            }
        });

        // Handle get prepare files to sign
        // Drag and Drop files to get prepare sign list files
        this.gui.getLblSelFileToSign().setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    loadFiles(droppedFiles);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Clicked open files to get prepare sign list files
        this.gui.getLblSelFileToSign().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                List<File> filesOpened = helper.openFiles();
                loadFiles(filesOpened);
            }
        });

        // Clicked button sign
        this.gui.getBtnSign().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                handleSign(false);
            }
        });

        // CLicked button duoble sign
        this.gui.getBtnDoubleSign().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                handleSign(true);
            }
        });

        // Clicked open folder
        this.gui.getMenuItemSignOpenFolder().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                List<Integer> listIDSlected = getIDSlected();
                if (listIDSlected.isEmpty()) {
                    return;
                }
                for (ModelResult ms : listSignResult) {
                    if (listIDSlected.contains(ms.getId())) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                helper.openExpolorer(ms.getFolderRS());
                            }
                        }).start();
                    }
                }
            }
        });
    }
    
    private DefaultTableModel getSignTableModel() {
        return (DefaultTableModel) this.gui.getTblSign().getModel();
    }

    private void loadFiles(List<File> files) {
        for (File f : files) {
            ModelResult msr = new ModelResult();
            msr.setId(indexSignTable);
            String filePath = f.getAbsolutePath();
            msr.setFilePath(filePath);
            msr.setFileName(helper.getFileName(filePath));
            fillDataToTable(msr);
            indexSignTable++;
            listSignResult.add(msr);
        }
    }

    private void removeFiles() {
        int rs = JOptionPane.showConfirmDialog(gui, "Do you wanna remove files selected?", "Remove files", JOptionPane.YES_NO_OPTION);
        if (rs == JOptionPane.NO_OPTION) {
            return;
        }
        List<Integer> listIDSelected = getIDSlected();
        if (listIDSelected.isEmpty()) {
            return;
        }
        listSignResult.removeIf(f -> (listIDSelected.contains(f.getId())));
        removeAllFiles();
        for (ModelResult msr : listSignResult) {
            msr.setId(indexSignTable);
            fillDataToTable(msr);
            indexSignTable++;
        }
        JOptionPane.showMessageDialog(gui, "Remove file successfully", "Remove files", JOptionPane.INFORMATION_MESSAGE);
    }

    private void removeAllFiles() {
        DefaultTableModel dtm = getSignTableModel();
        dtm.setRowCount(0);
//        listSignResult.clear();
        indexSignTable = 0;
    }

    private void fillDataToTable(ModelResult msr) {
        DefaultTableModel dtm = getSignTableModel();
        dtm.addRow(new Object[]{msr.getId(), msr.getFileName(), msr.getStatus(), msr.getSpeed()});
    }

    private void updateDataToTable(ModelResult nmrs) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DefaultTableModel dtm = getSignTableModel();
                for (int i = 0; i < dtm.getRowCount(); i++) {
                    if (helper.convertObjectToInt(dtm.getValueAt(i, 0)) == nmrs.getId()) {
                        dtm.setValueAt(nmrs.getFileName(), i, 1);
                        dtm.setValueAt(nmrs.getStatus(), i, 2);
                        dtm.setValueAt(nmrs.getSpeed() + " ms", i, 3);
                    }
                }
            }

        });

    }

    private void updateExportFileStatus(String status, int eventTime, int id) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DefaultTableModel dtm = getSignTableModel();
                for (int i = 0; i < dtm.getRowCount(); i++) {
                    if (helper.convertObjectToInt(dtm.getValueAt(i, 0)) == id) {
                        dtm.setValueAt(status, i, 2);
                        dtm.setValueAt(eventTime + " ms", i, 3);
                        return;
                    }
                }
            }

        });
    }

    private List<Integer> getIDSlected() {
        int[] selectedRows = this.gui.getTblSign().getSelectedRows();
        DefaultTableModel dtm = getSignTableModel();
        List<Integer> listID = new ArrayList<>();
        for (int i = 0; i < selectedRows.length; i++) {
            listID.add(Integer.parseInt(String.valueOf(dtm.getValueAt(selectedRows[i], 0))));
        }
        return listID;
    }

    private void handleSign(boolean isDuobleSign) {

        if (listSignResult.isEmpty()) {
            JOptionPane.showMessageDialog(gui,
                    "Files not found!",
                    "Not found",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Filter files signed
        List<ModelResult> listSigned = listSignResult.stream().filter(sr -> (sr.getSignature() != null)).collect(Collectors.toList());
        if (!listSigned.isEmpty()) {
            JOptionPane.showMessageDialog(gui, ""
                    + "Please remove all files Signed!",
                    "Remove files",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String myKeyNameSelected = String.valueOf(gui.getCbbYourKey().getSelectedItem());
        System.out.println(myKeyNameSelected);
        if (!myKey.containsKey(myKeyNameSelected)) {
            JOptionPane.showMessageDialog(gui,
                    "Key not found!\nPlease wait keys loaded or generate new keys",
                    "Not found",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        ModelMyKey mk = myKey.get(myKeyNameSelected);

        String hash = String.valueOf(gui.getCbbHash().getSelectedItem());
        if (!hash.equals("SHA256") && !hash.equals("SHA512")) {
            JOptionPane.showMessageDialog(gui, ""
                    + "Hash function not support!",
                    "Not support",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cryptoAlgorithm = mk.getAlgorithm();
        String algorithm = hash + "with" + cryptoAlgorithm;

        if (hash.equals("SHA512") && cryptoAlgorithm.equals("DSA")) {
            JOptionPane.showMessageDialog(gui, ""
                    + cryptoAlgorithm + " is Not support " + hash + " algorithm",
                    "Not support",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String msg = "Do you wanna sign all files below with"
                + "\nKey : " + myKeyNameSelected
                + "\nAlgorithm: " + cryptoAlgorithm
                + "\nHash: " + hash;

        int choose = JOptionPane.showConfirmDialog(gui, msg, "Sign", JOptionPane.YES_NO_OPTION);
        if (choose == JOptionPane.NO_OPTION) {
            return;
        }

        keyNameUsingSigned = myKeyNameSelected;
//        List<Integer> listID = getIDSlected();

        for (ModelResult signrs : listSignResult) {
//            if(!listID.contains(signrs.getId())) 
//                continue;
            updateExportFileStatus("Signing...", 0, signrs.getId());
            long startTime = System.nanoTime();

            byte[] signature = null;
            if (!isDuobleSign) {
                signature = Sign(signrs.getFilePath(), mk, algorithm);
            } else {
                signature = DoubleSign(signrs.getFilePath(), mk, algorithm);
            }

            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;  //divide by 1000000 to get milliseconds.

            if (signature != null) {
//                System.out.println(signature.length);
                signrs.setSignature(signature);
                signrs.setStatus("Sign Successfully");
                signrs.setSpeed((int) duration);
                signrs.setHashAlgorithm(hash);
            } else {
                signrs.setStatus("Sign Error");
            }
            updateDataToTable(signrs);

        }
    }
    
    // option [1]: signature, file, public key
    // option [2]: signature, file
    private void exportResultToZip(short option) {

        String folderExport = helper.getFolderSave("Choose folder to export");
        if (folderExport == null || folderExport.isEmpty()) {
            return;
        }

        String msgZip = "Do you wanna enable password for result zip file?"
                + "\nExport Zip with password to slower than without password"
                + "\nBut this way is more secure for your file"
                + "\n[YES] Enable password"
                + "\n[NO] I will set my own password latter";
        int rs = JOptionPane.showConfirmDialog(gui, msgZip, "Zip password", JOptionPane.YES_NO_OPTION);
        if (rs == JOptionPane.YES_OPTION) {
            isPasswordZip = true;
        }
        for (ModelResult msr : listSignResult) {
            if (msr.getStatus().equals("Sign Successfully")) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        int idFile = msr.getId();
                        if (msr.getSignature() == null) {
                            updateExportFileStatus("File not yet signed", 0, idFile);
                            return;
                        }

                        long startTime = System.nanoTime();

                        updateExportFileStatus("Exporting result...", 0, idFile);
                        String rawFileName = helper.getFileNameNoExtension(msr.getFileName());
                        String dirForSave = folderExport + "\\" + rawFileName;
                        File f = new File(dirForSave);
                        if (f.exists()) {
                            helper.deleteDirectory(f);
                        }
                        msr.setFolderRS(dirForSave);
                        boolean rsMkDir = helper.makeDir(dirForSave);
                        if (!rsMkDir) {
                            updateExportFileStatus("Make dir failed", 0, idFile);
                            return;
                        }
                        // Save signature format hashAlgorithm_filename.signature
                        String signatureFilePath = dirForSave + "\\" + msr.getHashAlgorithm() + "_" + rawFileName + ".signature";
                        boolean rsSaveSign = helper.writeBinaryFile(signatureFilePath, msr.getSignature());
                        if (!rsSaveSign) {
                            updateExportFileStatus("Save signature failed", 0, idFile);
                            return;
                        }

                        // Export public key with algorithm name format keyAlgorithm_filename.pub
                        String strPubkey = "";
                        boolean rsSaveKey = false;
                        strPubkey = myKey.get(keyNameUsingSigned).getPublickey();
                        String pubKeyPath = dirForSave + "\\"
                                + myKey.get(keyNameUsingSigned).getAlgorithm() + "_"
                                + rawFileName + ".pub";
                        if (option == 1) {
                            try {
                                PublicKey pub = helper.getPublicKeyFromString(strPubkey, myKey.get(keyNameUsingSigned).getAlgorithm());
                                byte[] pubData = pub.getEncoded();

                                rsSaveKey = helper.writeBinaryFile(pubKeyPath, pubData);
                            } catch (IOException ex) {
                                Logger.getLogger(SignController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (GeneralSecurityException ex) {
                                Logger.getLogger(SignController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            rsSaveKey = true;
                        }

                        if (!rsSaveKey) {
                            updateExportFileStatus("Save public key failed", 0, idFile);
                            return;
                        }

                        String rawFilePath = msr.getFilePath();
                        List<File> listFileToZip = null;
                        if (option == 2) {
                            listFileToZip = Arrays.asList(
                                    new File(signatureFilePath),
                                    new File(rawFilePath)
                            );
                        } else {
                            listFileToZip = Arrays.asList(
                                    new File(signatureFilePath),
                                    new File(rawFilePath),
                                    new File(pubKeyPath)
                            );
                        }
                        boolean rsZIP = helper.zipFiles(listFileToZip, dirForSave, rawFileName, isPasswordZip);
                        long endTime = System.nanoTime();
                        long duration = (endTime - startTime) / 1000000;  //divide by 1000000 to get milliseconds.
                        if (rsZIP) {
                            updateExportFileStatus("Export file successfully", (int) duration, idFile);
                        } else {
                            updateExportFileStatus("Zip file failed", (int) duration, idFile);
                        }
                    }
                }).start();
            }
        }
    }
}
