/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.controller.sign;

import static com.digitalsignature.controller.sign.DigitalSignatureController.signDao;
import com.digitalsignature.gui.GUI;
import com.digitalsignature.model.ModelKey;
import com.digitalsignature.model.ModelFriendKey;
import com.digitalsignature.model.ModelMyKey;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class KeyManagementController extends DigitalSignatureController {

    private int indexMyKey = 0;
    private int indexFrKey = 0;

    public KeyManagementController(GUI gui) {
        super(gui);
    }

    public void init() {
        initViewKeyManager();
        initEventHandle();
    }

    private void initViewKeyManager() {
        // Set column size of your key table
        this.gui.getTblYourKey().getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        this.gui.getTblYourKey().getTableHeader().setForeground(new Color(149, 165, 166));
        this.gui.getTblYourKey().getColumnModel().getColumn(0).setPreferredWidth(40);
        this.gui.getTblYourKey().getColumnModel().getColumn(1).setPreferredWidth(298);
        this.gui.getTblYourKey().getColumnModel().getColumn(2).setPreferredWidth(80);
        this.gui.getTblYourKey().getColumnModel().getColumn(3).setPreferredWidth(80);

        // Set column size of friend key table
        this.gui.getTblFriendKeys().getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        this.gui.getTblFriendKeys().getTableHeader().setForeground(new Color(149, 165, 166));
        this.gui.getTblFriendKeys().getColumnModel().getColumn(0).setPreferredWidth(40);
        this.gui.getTblFriendKeys().getColumnModel().getColumn(1).setPreferredWidth(248);
        this.gui.getTblFriendKeys().getColumnModel().getColumn(2).setPreferredWidth(210);
    }

    private void initEventHandle() {
        initMyKeyHandle();
        initFriendKeyHandle();
    }

    private void removeItemFromCombobox(JComboBox cbb1, String keyName) {
        cbb1.removeItem(keyName);
    }

    private void addItemToCombobox(JComboBox cbb1, String keyName) {
        cbb1.addItem(keyName);
    }

    private void initMyKeyHandle() {
        // Click button generate key
        this.gui.getBtnGenKey().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                handleGenerateMyKey();
            }
        });

        // Click button remove key
        this.gui.getBtnDelKey().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                handleRemoveMyKey();
            }
        });

        // Click button export key
        this.gui.getBtnExKey().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                handleExportKeyPair();
            }
        });
    }

    private void initFriendKeyHandle() {
        // Click button import friend public key
        this.gui.getBtnImpPubKey().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                handleImportFriendPublicKey();
            }
        });

        // Clicked button del friend key
        this.gui.getBtnDelFriendKey().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                handleRemoveFriendKey();
            }
        });
    }

    private DefaultTableModel getTableModel(JTable table) {
        return (DefaultTableModel) table.getModel();
    }

    // Fill data to my key table or my friend key table
    void fillKeyToTable(JTable table, ModelKey key) {
        DefaultTableModel dtm = getTableModel(table);
        if (key instanceof ModelMyKey) {
            dtm.addRow(new Object[]{++indexMyKey, key.getName(), ((ModelMyKey) key).getAlgorithm(), ((ModelMyKey) key).getKeySize()});
        } else {
            dtm.addRow(new Object[]{++indexFrKey, key.getName(), ((ModelFriendKey) key).getNote()});
        }
    }

    // Get list key name from my key table or my friend key table (key name index = 1 from both tables)
    private List<String> getListKeyNameFromTable(JTable table) {
        List<String> keysName = new ArrayList<>();
        int[] indexSelected = table.getSelectedRows();
        DefaultTableModel dtm = getTableModel(table);
        for (int i = 0; i < indexSelected.length; i++) {
            keysName.add(String.valueOf(dtm.getValueAt(indexSelected[i], 1)));
        }
        return keysName;
    }

    private boolean removeMyKeysSelected() {
        List<String> keysNameSelected = getListKeyNameFromTable(this.gui.getTblYourKey());
        if (keysNameSelected.isEmpty()) {
            return false;
        }
        DefaultTableModel dtm = getTableModel(this.gui.getTblYourKey());
        int index = 0;
        while (index < keysNameSelected.size()) {
            for (int i = 0; i < dtm.getRowCount(); i++) {
                String keyName = keysNameSelected.get(index);
                if (String.valueOf(dtm.getValueAt(i, 1)).equals(keyName)) {
                    boolean rs = signDao.deleteMyKey(keyName);
                    if (!rs) {
                        return false;
                    }
                    dtm.removeRow(i);
                    myKey.remove(keyName);
                    removeItemFromCombobox(gui.getCbbYourKey(), keyName);
                    break;
                }
            }
            index++;
        }
        resetIndexMyKeyTable();
        return true;
    }

    private void resetIndexMyKeyTable() {
        DefaultTableModel dtm = getTableModel(this.gui.getTblYourKey());
        indexMyKey = dtm.getRowCount();
        for (int i = 0; i < indexMyKey; i++) {
            int index = i + 1;
            dtm.setValueAt(index, i, 0);
        }
    }

    private void resetIndexFriendKeyTable() {
        DefaultTableModel dtm = getTableModel(this.gui.getTblFriendKeys());
        indexFrKey = dtm.getRowCount();
        for (int i = 0; i < indexFrKey; i++) {
            int index = i + 1;
            dtm.setValueAt(index, i, 0);
        }
    }

    // Export my key (public key (*.pub) and priavte key (*.pem) to file)
    private boolean exportKeyPair(String folderSave) {
        List<String> keysNameSelected = getListKeyNameFromTable(this.gui.getTblYourKey());

        for (String keyName : keysNameSelected) {
            ModelMyKey key = myKey.get(keyName);
            String algorithm = key.getAlgorithm();
            String pubKey = key.getPublickey();
            String priKey = key.getPrivateKey();
            boolean rs = helper.saveFilesKeyPair(keyName,
                    pubKey,
                    priKey,
                    algorithm,
                    folderSave);

//            try {
//                PublicKey pub = helper.getPublicKeyFromString(pubKey, algorithm);
//                PrivateKey pri = helper.getPrivateKeyFromString(priKey, algorithm);
//                
//            } catch (IOException ex) {
//                Logger.getLogger(KeyManagementController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (GeneralSecurityException ex) {
//                Logger.getLogger(KeyManagementController.class.getName()).log(Level.SEVERE, null, ex);
//            }
            if (!rs) {
                return false;
            }
        }
        return true;
    }

    public ModelKey encryptKey(ModelKey key) {
        String publicKey = helper.encryptKey(key.getPublickey(), AES_KEY);
        String privateKey = helper.encryptKey(key.getPrivateKey(), AES_KEY);
        key.setPublickey(publicKey);
        key.setPrivateKey(privateKey);
        return key;
    }

    public ModelKey decryptKey(ModelKey key) {
        String publicKey = helper.decryptKey(key.getPublickey(), AES_KEY);
        String privateKey = "";
        if (key instanceof ModelMyKey) {
            privateKey = helper.decryptKey(key.getPrivateKey(), AES_KEY);
        }

        key.setPublickey(publicKey);
        key.setPrivateKey(privateKey);
        return key;
    }

    private void handleImportFriendPublicKey() {

        // Open public key file
        String pubFilePath = helper.openPubFile();
        if (pubFilePath.equals("") || pubFilePath == null) {
            return;
        }

        String strPublicKey = helper.getPublicFileToString(pubFilePath);
        if (strPublicKey.equals("")) {
            JOptionPane.showMessageDialog(gui, "Invalid file public key", "Import error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        File f = new File(pubFilePath);
        String algorithm = helper.getAlgorithmFromFileName(f.getName());

        // Name for public key input file
        String name = JOptionPane.showInputDialog(gui,
                "Enter your friend public key name",
                "Friend Public key name",
                JOptionPane.INFORMATION_MESSAGE);

        // Note 
        String note = "";
        JTextArea ta = new JTextArea(20, 20);
        ta.setFont(new Font("SansSerif", Font.PLAIN, 13));
        switch (JOptionPane.showConfirmDialog(gui, new JScrollPane(ta), "Note for public key", JOptionPane.INFORMATION_MESSAGE)) {
            case JOptionPane.OK_OPTION:
                note = ta.getText().trim();
                break;
        }

        ModelFriendKey mfKey = new ModelFriendKey(name, strPublicKey, note);
        mfKey.setAlgorithm(algorithm);

        friendKey.put(name, mfKey);

        ModelFriendKey mfSave = new ModelFriendKey(mfKey);

        boolean rs = signDao.savePubkey((ModelFriendKey) encryptKey(mfSave));
        if (!rs) {
            JOptionPane.showMessageDialog(gui, "Save key failed", "Error", JOptionPane.ERROR_MESSAGE);
            friendKey.remove(name);
            return;
        }
        fillKeyToTable(this.gui.getTblFriendKeys(), mfKey);
        addItemToCombobox(gui.getCbbFriendKeyVerify(), name);
        JOptionPane.showMessageDialog(gui, "Import friend key successfully", "Successfully", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean handleRemoveFriendKeySelected() {
        List<String> keysNameSelected = getListKeyNameFromTable(this.gui.getTblFriendKeys());
        if (keysNameSelected.isEmpty()) {
            return false;
        }
        DefaultTableModel dtm = getTableModel(this.gui.getTblFriendKeys());
        int index = 0;
        while (index < keysNameSelected.size()) {
            for (int i = 0; i < dtm.getRowCount(); i++) {
                String keyName = keysNameSelected.get(index);
                if (String.valueOf(dtm.getValueAt(i, 1)).equals(keyName)) {
                    boolean rs = signDao.deleteFriendKey(keyName);
                    if (!rs) {
                        return false;
                    }
                    dtm.removeRow(i);
                    friendKey.remove(keyName);
                    removeItemFromCombobox(gui.getCbbFriendKeyVerify(), keyName);
                    break;
                }
            }
            index++;
        }
        resetIndexFriendKeyTable();
        return true;
    }

    private void handleGenerateMyKey() {
        int keySize = helper.convertObjectToInt(gui.getCbbKeySize().getSelectedItem());
        String algorithm = String.valueOf(gui.getCbbAlgorithm().getSelectedItem());

        if (keySize == 4096 && algorithm.equals("DSA")) {
            JOptionPane.showMessageDialog(gui.getPanelYourKeys(),
                    "Key size 4096 not support for DSA algorithm",
                    "Invalid Key Size",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String keyName = JOptionPane.showInputDialog(gui.getPanelKeysMana(),
                "Enter your key name", "Input Key Name",
                JOptionPane.QUESTION_MESSAGE);

        if (keyName == null || keyName.trim().equals("") || myKey.containsKey(keyName)) {
            JOptionPane.showMessageDialog(gui.getPanelYourKeys(),
                    "Invalid Key name or Key name existed!",
                    "Invalid",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int rs = JOptionPane.showConfirmDialog(gui.getPanelYourKeys(),
                "Generate key " + keyName + " [Algorithm: " + algorithm + "] [Key size:" + keySize + " bit]",
                "Generate Key Pair", JOptionPane.YES_NO_OPTION);

        if (rs == JOptionPane.YES_OPTION) {
            gui.getLblTimeGenYourKey().setText("Generating the key...");
            // Calculator time generate key
            long startTime = System.nanoTime();

            KeyPair kp = GenerateKey(keySize, algorithm);

            long endTime = System.nanoTime();
            long time = endTime - startTime;
            long duration = (endTime - startTime) / 1000000;  //divide by 1000000 to get milliseconds.
            System.out.println(keyName + " : " + time);
            String strPrivateKey = helper.convertPrivateKeyToString(kp.getPrivate());
            String strPublicKey = helper.convertPublicKeyToString(kp.getPublic());

//                    System.out.println("Pub key: "+kp.getPrivate().getEncoded().length);
//                    System.out.println("Pri key: "+kp.getPublic().getEncoded().length);
            ModelMyKey mkey = new ModelMyKey(keyName,
                    strPrivateKey,
                    strPublicKey,
                    algorithm,
                    keySize);

            // Save key to list key
            myKey.put(keyName, mkey);
            ModelMyKey mkSave = new ModelMyKey(mkey);
            // Storage key into database
            boolean success = signDao.saveMyKey((ModelMyKey) encryptKey(mkSave));
            if (!success) {
                JOptionPane.showMessageDialog(gui.getPanelYourKeys(), "Generate key failed!", "Failed", JOptionPane.ERROR_MESSAGE);
                myKey.remove(keyName);
                return;
            }

            // Update key to My Key Table
            fillKeyToTable(gui.getTblYourKey(), mkey);
            addItemToCombobox(gui.getCbbYourKey(), keyName);
            gui.getLblTimeGenYourKey().setText("Time Generate: " + duration + " ms");
            JOptionPane.showMessageDialog(gui.getPanelYourKeys(), "Generate key successfuly!", "Successfully", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleRemoveMyKey() {
        gui.getLblTimeGenYourKey().setText("Deleting keys...");
        int ok = JOptionPane.showConfirmDialog(gui.getPanelYourKeys(),
                "Are you sure delete keys selected?",
                "Delete Keys Pair", JOptionPane.YES_NO_OPTION);

        if (ok == JOptionPane.YES_OPTION) {
            boolean rs = removeMyKeysSelected();
            if (rs) {
                JOptionPane.showMessageDialog(gui, "Remove key successfuly!", "Successfully", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(gui, "Remove key failed!", "Failed!", JOptionPane.ERROR_MESSAGE);
            }
        }
        gui.getLblTimeGenYourKey().setText("");
    }

    private void handleExportKeyPair() {
        String folderSave = helper.getFolderSave("Choose folder save Private and Public key");
        if (folderSave.equals("")) {
            return;
        }

        boolean rs = exportKeyPair(folderSave);
        if (rs) {
            JOptionPane.showMessageDialog(gui, "Export key pair successfuly!", "Export Successfully", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(gui, "Export key pair failed!", "Export Failed!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRemoveFriendKey() {
        int ok = JOptionPane.showConfirmDialog(gui.getPanelYourKeys(),
                "Are you sure delete friend key selected?",
                "Delete Friend Key", JOptionPane.YES_NO_OPTION);

        if (ok == JOptionPane.YES_OPTION) {
            boolean rs = handleRemoveFriendKeySelected();
            if (rs) {
                JOptionPane.showMessageDialog(gui, "Remove key successfuly!", "Successfully", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(gui, "Remove key failed!", "Failed!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
