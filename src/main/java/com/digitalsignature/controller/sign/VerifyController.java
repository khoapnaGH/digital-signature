/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.controller.sign;

import com.digitalsignature.gui.GUI;
import com.digitalsignature.model.ModelFriendKey;
import com.digitalsignature.model.ModelInputVerify;
import com.digitalsignature.model.ModelResult;
import java.awt.Color;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class VerifyController extends DigitalSignatureController {

    private static List<ModelResult> listVerifyResult = new ArrayList<>();

    private int indexSignTable = 0;

    public VerifyController(GUI gui) {
        super(gui);
    }

    public void init() {
        initViewer();
        initEventHandle();
    }

    public void initViewer() {
        // Set column size
        this.gui.getTblVerify().getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        this.gui.getTblVerify().getTableHeader().setForeground(new Color(149, 165, 166));
        this.gui.getTblVerify().getColumnModel().getColumn(0).setPreferredWidth(50);
        this.gui.getTblVerify().getColumnModel().getColumn(1).setPreferredWidth(218);
        this.gui.getTblVerify().getColumnModel().getColumn(2).setPreferredWidth(150);
        this.gui.getTblVerify().getColumnModel().getColumn(3).setPreferredWidth(80);

    }

    public void initEventHandle() {

        // Right Click to Show Export Popup 
        this.gui.getTblVerify().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                if (SwingUtilities.isRightMouseButton(evt)) {
                    gui.getPopupVerifySign().show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
        });

        // Handle get prepare files to sign
        // Drag and Drop files to get prepare verify list files
        this.gui.getLblSelFileToVerify().setDropTarget(new DropTarget() {
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

        // Open result folder after verify
        this.gui.getMenuItemVerifyOpenFolder().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {

            }
        });

        // Remove files
        this.gui.getMenuItemVerifyRemoveFile().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                gui.getPopupVerifySign().setVisible(false);
                removeFiles();
            }
        });

        // Single verify
        this.gui.getBtnVerify().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                handleVerify(false);
            }
        });

        this.gui.getBtnDoubleVerify().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                handleVerify(true);
            }
        });

        // Clicked open folder
        this.gui.getMenuItemVerifyOpenFolder().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                handleOpenFolder();
            }
        });
    }

    private DefaultTableModel getVerifyTableModel() {
        return (DefaultTableModel) this.gui.getTblVerify().getModel();
    }

    private void loadFiles(List<File> files) {
        for (File f : files) {
            if (!f.getName().endsWith(".zip")) {
                continue;
            }
            ModelResult msr = new ModelResult();
            msr.setId(indexSignTable);
            String filePath = f.getAbsolutePath();
            msr.setStatus("Ready to verify");
            msr.setFilePath(filePath);
            msr.setFileName(helper.getFileName(filePath));
            fillDataToTable(msr);
            indexSignTable++;
            listVerifyResult.add(msr);
            System.out.println(msr.toString());
        }
    }

    private void fillDataToTable(ModelResult msr) {
        DefaultTableModel dtm = getVerifyTableModel();
        dtm.addRow(new Object[]{msr.getId(), msr.getFileName(), msr.getStatus(), msr.getSpeed()});
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
        listVerifyResult.removeIf(f -> (listIDSelected.contains(f.getId())));
        removeAllFiles();
        for (ModelResult msr : listVerifyResult) {
            msr.setId(indexSignTable);
            fillDataToTable(msr);
            indexSignTable++;
        }
        JOptionPane.showMessageDialog(gui, "Remove file successfully", "Remove files", JOptionPane.INFORMATION_MESSAGE);
    }

    private void removeAllFiles() {
        DefaultTableModel dtm = getVerifyTableModel();
        dtm.setRowCount(0);
//        listVerifyResult.clear();
        indexSignTable = 0;
    }

    private void updateVerifyFileStatus(String status, int eventTime, int id) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DefaultTableModel dtm = getVerifyTableModel();
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
        int[] selectedRows = this.gui.getTblVerify().getSelectedRows();
        DefaultTableModel dtm = getVerifyTableModel();
        List<Integer> listID = new ArrayList<>();
        for (int i = 0; i < selectedRows.length; i++) {
            listID.add(Integer.parseInt(String.valueOf(dtm.getValueAt(selectedRows[i], 0))));
        }
        return listID;
    }

    private void handleOpenFolder() {
        List<Integer> listIDSlected = getIDSlected();
        if (listIDSlected.isEmpty()) {
            return;
        }
        for (ModelResult ms : listVerifyResult) {
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

    private void handleVerify(boolean isDoubleVerify) {
        // Check listVerifyResult is empty?
        if (listVerifyResult.isEmpty()) {
            return;
        }

        // Extract zip file input
        String folderSaveResult = helper.getFolderSave("Choose extract folder zip files");
        if (folderSaveResult.isEmpty() || folderSaveResult == null) {
            return;
        }

        boolean usingInputKey = this.gui.getCheckboxVerify().isSelected();

        for (ModelResult modelResult : listVerifyResult) {
            int id = modelResult.getId();
            long startVerify = System.nanoTime();
            updateVerifyFileStatus("Unzip file...", 0, id);
            String fileNameNoEx = helper.getFileNameNoExtension(modelResult.getFileName());
            // Unzip file
            String unzipedFolder = folderSaveResult + "\\" + fileNameNoEx;
            modelResult.setFolderRS(unzipedFolder);
            boolean unzipRs = helper.unZip(modelResult.getFilePath(), unzipedFolder);

            if (unzipRs) {
                updateVerifyFileStatus("Unzip file successfully", 0, id);
            } else {
                updateVerifyFileStatus("Unzip file failed", 0, id);
                return;
            }

            // Read Input Files from unzip folder
            ModelInputVerify miv = helper.readInputVerify(unzipedFolder);
            if (miv == null || miv.getPublickey().isEmpty() && usingInputKey) {
                updateVerifyFileStatus("Public key from input not found", 0, id);
                return;
            }

            ModelFriendKey mFriendKey = null;
            if (usingInputKey) {
                mFriendKey = new ModelFriendKey("", miv.getPublickey(), "");
                mFriendKey.setAlgorithm(miv.getPublickeyAlgorithm());
            } else {
                String frKeyNameSelected = String.valueOf(gui.getCbbFriendKeyVerify().getSelectedItem());
                mFriendKey = friendKey.get(frKeyNameSelected);

                if (mFriendKey == null) {
                    updateVerifyFileStatus("Friend key  not found", 0, id);
                    return;
                }

            }

            String hash = miv.getSignatureHashAlgorithm();
            String cryptoAlgorithm = mFriendKey.getAlgorithm();
            String algorithm = hash + "with" + cryptoAlgorithm;

            if (hash.equals("SHA512") && cryptoAlgorithm.equals("DSA")) {
                updateVerifyFileStatus(cryptoAlgorithm + " is Not support " + hash + " algorithm", 0, id);
                return;
            }

            boolean verifyRs = false;

            if (!isDoubleVerify) {
                verifyRs = Verify(miv.getFilePath(), miv.getSignature(), mFriendKey, algorithm);
            } else {
                verifyRs = VerifyDoubleSign(miv.getFilePath(), miv.getSignature(), mFriendKey, algorithm);
            }

            long endTime = System.nanoTime();
            long duration = (endTime - startVerify) / 1000000;  //divide by 1000000 to get milliseconds.
            if (verifyRs) {
                updateVerifyFileStatus("File Valid", (int) duration, id);
            } else {
                updateVerifyFileStatus("File Invalid", (int) duration, id);
            }
        }
    }

}
