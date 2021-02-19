/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.controller.sign;

import com.digitalsignature.common.Helper;
import com.digitalsignature.dao.concrete.SignDAO;
import com.digitalsignature.gui.GUI;
import com.digitalsignature.model.ModelFriendKey;
import com.digitalsignature.model.ModelMyKey;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class DigitalSignatureController implements IDigitalSignatureController {

    protected GUI gui;
    protected static SignDAO signDao = new SignDAO();
    protected static Helper helper = new Helper();

    protected SignController sign = null;
    protected KeyManagementController keyMana = null;
    protected VerifyController verify = null;

    protected static String AES_KEY = "";
    protected static Map<String, ModelMyKey> myKey = new HashMap<String, ModelMyKey>();
    protected static Map<String, ModelFriendKey> friendKey = new HashMap<String, ModelFriendKey>();

    protected static PublicKey pubKeyEncryptData = null;
    protected static PrivateKey privateDecryptData = null;
    
    protected static PublicKey pubKeyDoubleSign = null;
    protected static PrivateKey priKeyDoubleSign = null;

    public DigitalSignatureController(GUI gui) {
        this.gui = gui;
    }

    public void initSignatureController() {
        initKeyManagementController();
        initSign();
        initVerify();
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadKey();
            }
        }).start();
    }

    private void loadKey() {
        this.gui.getLblTimeGenYourKey().setText("Loading keys...");
        this.pubKeyEncryptData = helper.loadPublicKey("public.rsa");
        this.privateDecryptData = helper.loadPrivateKey("private.rsa");
        loadAESKey();
        this.pubKeyDoubleSign = helper.loadPublicKey("publicDoubleSign.rsa");
        this.priKeyDoubleSign = helper.loadPrivateKey("privateDoubleSign.rsa");
//        System.out.println("AES KEY: "+AES_KEY);
        // Load key for sign
        List<ModelMyKey> listMyKey = signDao.getAllMyKeys();
        List<ModelFriendKey> listFriendKey = signDao.getAllFriendKeys();

        // Load key and fill it to Key Management, Sign, Verify
        for (ModelMyKey mkey : listMyKey) {
            String keyName = mkey.getName();
            myKey.put(keyName, (ModelMyKey) keyMana.decryptKey(mkey));

            // Fill to My Key Table
            keyMana.fillKeyToTable(this.gui.getTblYourKey(), mkey);

            // Fill to Sign, Verify My key Combobox
            fillToComboxbox(this.gui.getCbbYourKey(), keyName);
        }

        for (ModelFriendKey fkey : listFriendKey) {
            String keyName = fkey.getName();
            friendKey.put(keyName, (ModelFriendKey) keyMana.decryptKey(fkey));

            // Fill to Friend table
            keyMana.fillKeyToTable(this.gui.getTblFriendKeys(), fkey);

            // Fill to Sign Friend Key combobox
            fillToComboxbox(this.gui.getCbbFriendKeyVerify(), keyName);
        }
        this.gui.getLblTimeGenYourKey().setText("");
    }

    private void loadAESKey() {
        String keyLoadFromDB = signDao.getAESKey();
        AES_KEY = helper.rsaDecrypt(keyLoadFromDB, privateDecryptData);
        if (AES_KEY.isEmpty()) {
            JOptionPane.showMessageDialog(gui, "Database Validate Failed!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
    // Init action listener for tab Key Management
    private void initKeyManagementController() {
        keyMana = new KeyManagementController(this.gui);
        keyMana.init();
    }

    // Init action listener for tab Signature
    private void initSign() {
        sign = new SignController(this.gui);
        sign.init();
    }

    // Init action listener for tab Verify
    private void initVerify() {
        verify = new VerifyController(this.gui);
        verify.init();
    }

    @Override
    public KeyPair GenerateKey(int keySize, String algorithm) {
        KeyPair kp = null;
        try {
            int keylength = helper.convertObjectToInt(keySize);
            SecureRandom sr = new SecureRandom();
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(algorithm);
            kpg.initialize(keylength, sr);
            kp = kpg.genKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kp;
    }

    @Override
    public byte[] Sign(String filePath, ModelMyKey mykey, String hashAgorithm) {
        byte[] filePrepareToSign = helper.getBinaryFile(filePath);
        if (filePrepareToSign == null) {
            return null;
        }
        String signatureAlgorithm = mykey.getAlgorithm();
        PrivateKey pri = null;
        try {
            pri = helper.getPrivateKeyFromString(mykey.getPrivateKey(), signatureAlgorithm);
        } catch (IOException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (pri == null) {
            return null;
        }

        try {
            //SHA256withRSA
            Signature signature = Signature.getInstance(hashAgorithm);
            signature.initSign(pri);
            signature.update(filePrepareToSign);
            return signature.sign();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public boolean Verify(String filePath, byte[] digitalSignature, ModelFriendKey frkey, String hashAgorithm) {
        byte[] filePrepareToVerify = helper.getBinaryFile(filePath);
        if (filePrepareToVerify == null) {
            return false;
        }
        String signatureAlgorithm = frkey.getAlgorithm();
        PublicKey pubKey = null;
        try {
            pubKey = helper.getPublicKeyFromString(frkey.getPublickey(), signatureAlgorithm);
        } catch (IOException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (pubKey == null) {
            return false;
        }
        try {
            Signature signature = Signature.getInstance(hashAgorithm);
            signature.initVerify(pubKey);
            signature.update(filePrepareToVerify);

            return signature.verify(digitalSignature);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /*
    * Using My Private to sign the message after that using public key of tool
    * to encrypt signature
    * @param filePath       - path of file prepare sign
    * @param myKey          - get my private key for sign
    * @param hashAgorithm   - Hash algorithm and Key algorithm (ex: SHA512withDSA)
     */
    @Override
    public byte[] DoubleSign(String filePath, ModelMyKey mykey, String hashAgorithm) {
        byte[] filePrepareToSign = helper.getBinaryFile(filePath);
        if (filePrepareToSign == null) {
            return null;
        }
        String signatureAlgorithm = mykey.getAlgorithm();
        PrivateKey pri = null;
        try {
            pri = helper.getPrivateKeyFromString(mykey.getPrivateKey(), signatureAlgorithm);
        } catch (IOException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (pri == null) {
            return null;
        }

        try {
            Signature signature = Signature.getInstance(hashAgorithm);
            signature.initSign(pri);
            signature.update(filePrepareToSign);
            return rsaEncryptSignature(signature.sign());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public boolean VerifyDoubleSign(String filePath, byte[] digitalSignature, ModelFriendKey frkey, String hashAgorithm) {
        byte[] filePrepareToVerify = helper.getBinaryFile(filePath);
        if (filePrepareToVerify == null) {
            return false;
        }
        String signatureAlgorithm = frkey.getAlgorithm();
        PublicKey pubKey = null;
        try {
            pubKey = helper.getPublicKeyFromString(frkey.getPublickey(), signatureAlgorithm);
        } catch (IOException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (pubKey == null) {
            return false;
        }
        try {
            // Decrypt signature before verify
            System.out.println(digitalSignature.length * 8);
            byte[] signatureDecrypted = rsaDecryptSignature(digitalSignature);
            if (signatureDecrypted == null) {
                return false;
            }

            Signature signature = Signature.getInstance(hashAgorithm);
            signature.initVerify(pubKey);
            signature.update(filePrepareToVerify);

            return signature.verify(signatureDecrypted);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(DigitalSignatureController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void fillToComboxbox(JComboBox cbb1, String keyName) {
        cbb1.addItem(keyName);
    }

    private static byte[] rsaEncryptSignature(byte[] signature) {
        try {
            System.out.println("Before encryp signature: " + signature.length);
            if (pubKeyDoubleSign == null || signature == null) {
                return null;
            }
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, pubKeyDoubleSign);
            byte encryptOut[] = c.doFinal(signature);
            System.out.println("After encryp signature: " + encryptOut.length);
            return encryptOut;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] rsaDecryptSignature(byte[] signature) {
        try {
            System.out.println(privateDecryptData.getEncoded().length);
            if (priKeyDoubleSign == null || signature == null) {
                return null;
            }
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, priKeyDoubleSign);
            byte[] decryptOut = c.doFinal(signature);
            return decryptOut;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
