/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.common;

import com.digitalsignature.model.ModelAES;
import com.digitalsignature.model.ModelInputVerify;
import com.digitalsignature.model.ModelZipper;
import java.awt.Desktop;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Admin
 */
public class Helper {

    private final String EXTENSION_PUBLIC_KEY_FILE = ".pub";
    private final String EXTENSION_PRIVATE_KEY_FILE = ".pem";
    private final String secretZIPKey = "D1s1t4al_S1gn4tur3_43s!!!@*?-+.";

    public int convertObjectToInt(Object obj) {
        int rs = -1;
        try {
            rs = Integer.parseInt(String.valueOf(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public String getFileName(String filePath) {
        File f = new File(filePath);
        if (!f.isFile()) {
            return "File not found";
        }
        return f.getName();
    }

    public List<File> openFiles() {
        JFileChooser jfc;
        jfc = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setMultiSelectionEnabled(true);
        List<File> arr = null;
        File[] selectedFile = null;
        int result = jfc.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = jfc.getSelectedFiles();
        }
        if (selectedFile != null) {
            arr = new ArrayList<>(Arrays.asList(selectedFile));
        } else {
            arr = new ArrayList<>();
        }
        return arr;
    }

    public String openPubFile() {
        JFileChooser jfc;
        jfc = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setMultiSelectionEnabled(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Public Key *.pub", "pub");
        jfc.setFileFilter(filter);
        jfc.setAcceptAllFileFilterUsed(false);
        int result = jfc.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return "";
    }

    public String convertPrivateKeyToString(PrivateKey pri) {
        if (pri == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Base64.getEncoder().encodeToString(pri.getEncoded()));
        return sb.toString();
    }

    public String convertPublicKeyToString(PublicKey pub) {
        if (pub == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Base64.getEncoder().encodeToString(pub.getEncoded()));
        return sb.toString();
    }

    public PrivateKey getPrivateKeyFromString(String key, String algorithm) throws IOException, GeneralSecurityException {
        String privateKeyPEM = key;

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM.getBytes("UTF-8"));
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        PrivateKey privKey = kf.generatePrivate(keySpec);
        return privKey;
    }

    public PublicKey getPublicKeyFromString(String key, String algorithm) throws IOException, GeneralSecurityException {
        String publicKeyPEM = key;
        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM.getBytes("UTF-8"));
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        PublicKey pubKey = kf.generatePublic(new X509EncodedKeySpec(encoded));
        return pubKey;
    }

    public boolean saveFilesKeyPair(String keyPairName, String strPublicKey, String strPrivateKey, String algorithm, String folderSave) {
        try {

            File f = new File(folderSave);
            if (!f.isDirectory() && !f.exists()) {
                return false;
            }

            try {
                PublicKey pubKey = getPublicKeyFromString(strPublicKey, algorithm);
                PrivateKey priKey = getPrivateKeyFromString(strPrivateKey, algorithm);

                byte[] pubData = pubKey.getEncoded();
                byte[] priData = priKey.getEncoded();

                String timeStamp = getDateTime();
                String pubKeyFileName = f.getAbsolutePath() + "\\" + algorithm + "_" + keyPairName + "_" + timeStamp + EXTENSION_PUBLIC_KEY_FILE;
                String priKeyFileName = f.getAbsolutePath() + "\\" + algorithm + "_" + keyPairName + "_" + timeStamp + EXTENSION_PRIVATE_KEY_FILE;

                boolean rs1 = writeBinaryFile(pubKeyFileName, pubData);
                boolean rs2 = writeBinaryFile(priKeyFileName, priData);

                return rs1 && rs2;

            } catch (GeneralSecurityException ex) {
                Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean saveKeys(String fileName, BigInteger mod, BigInteger exp) throws IOException {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            System.out.println("Generating " + fileName + "...");
            fos = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(new BufferedOutputStream(fos));

            oos.writeObject(mod);
            oos.writeObject(exp);

            System.out.println(fileName + " generated successfully");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                oos.close();

                if (fos != null) {
                    fos.close();
                }
            }
        }
        return false;
    }
    
    public String getPublicFileToString(String pubPath) {
        try {
            File f = new File(pubPath);
            FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis);
            byte[] keyBytes = new byte[(int) f.length()];
            dis.readFully(keyBytes);
            dis.close();

            String algorithm = getAlgorithmFromFileName(f.getName());
            if (algorithm.equals("")) {
                return "";
            }

            KeyFactory kf = KeyFactory.getInstance(algorithm);
            PublicKey pubKey = kf.generatePublic(new X509EncodedKeySpec(keyBytes));

            return convertPublicKeyToString(pubKey);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getFolderSave(String title) {
        JFileChooser jfc;
        jfc = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
        //JFileChooser jfc = 
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setMultiSelectionEnabled(false);
        jfc.setDialogTitle(title);
        //fileChooser.
        int result = jfc.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return "";
    }

    private String getDateTime() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        return timeStamp;
    }

    public boolean writeBinaryFile(String fpath, byte[] data) {

        try {
            File f = new File(fpath);
            FileOutputStream fos;
            fos = new FileOutputStream(f, false);
            try {

                fos.write(data, 0, data.length);
                fos.close();
                fos.flush();
                return true;
            } catch (IOException ex) {
                Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private byte[] readBinaryFile(String fPath) {
        try {
            File f = new File(fPath);
            if (!f.isFile() || !f.exists()) {
                return null;
            }
            InputStream fis = new FileInputStream(f);

            byte[] b = new byte[fis.available()];
            fis.read(b);
            fis.close();

            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAlgorithmFromFileName(String fname) {
        String algorithm = fname.substring(0, 3);
        if (algorithm.equals("DSA") || algorithm.equals("RSA")) {
            return algorithm;
        }
        return "";
    }

    public String encryptKey(String keyBase64, String aesKey) {
        try {
            //key format base64 
            byte[] encrypted = ModelAES.encrypt(keyBase64, aesKey);
            String strKeyEncrypted = Base64.getEncoder().encodeToString(encrypted);
            return strKeyEncrypted;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public String decryptKey(String keyEncBase64, String aesKey) {
        try {
            byte[] decrypted = ModelAES.decrypt(keyEncBase64, aesKey);
            return new String(decrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String rsaEncrypt(String plaintext, PublicKey pubKey) {
        try {
            byte[] encryptedString = plaintext.getBytes();
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, pubKey);
            byte encryptOut[] = c.doFinal(encryptedString);
            String strEncrypt = Base64.getEncoder().encodeToString(encryptOut);
            return strEncrypt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String rsaDecrypt(String encrypted, PrivateKey priKey) {
        try {
            byte[] encryptedData = Base64.getMimeDecoder().decode(encrypted);
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, priKey);
            byte[] decryptOut = c.doFinal(encryptedData);
            String rsaRs = new String(decryptOut);
            return rsaRs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // Load public key from database to encrypt data
    // @param pubName: pubName = publicDoubleSign.rsa using for double sign
    //                 pubName = public.rsa using for encrypted AES key
    public PublicKey loadPublicKey(String pubName) {
        InputStream fis = null;
        ObjectInputStream ois = null;
        try {
//            fis = new FileInputStream(new File(ROOT_PATH + "\\public.rsa"));
            fis = this.getClass().getResourceAsStream("/keys/"+pubName);
            ois = new ObjectInputStream(fis);

            BigInteger modulus = (BigInteger) ois.readObject();
            BigInteger exponent = (BigInteger) ois.readObject();

            //Get Public Key
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            PublicKey publicKey = fact.generatePublic(rsaPublicKeySpec);

            return publicKey;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException ex) {
                    Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return null;

    }

    // Load private key from file in /main/resoure/keys/private.rsa to decrypt data
     // @param pubName: priName = privateDoubleSign.rsa using for double sign
    //                 priName = private.rsa using for decrypted AES key
    public PrivateKey loadPrivateKey(String priName) {

        InputStream is = null;
        
        ObjectInputStream ois = null;
        try { 
            is = this.getClass().getResourceAsStream("/keys/"+priName);
            ois = new ObjectInputStream(is);

            BigInteger modulus = (BigInteger) ois.readObject();
            BigInteger exponent = (BigInteger) ois.readObject();

            //Get Private Key
            RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, exponent);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = fact.generatePrivate(rsaPrivateKeySpec);

            return privateKey;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException ex) {
                    Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return null;
    }

    public byte[] getBinaryFile(String fPath) {
        File f = new File(fPath);
        if (f.exists() && f.isFile()) {
            try {
                return Files.readAllBytes(f.toPath());
            } catch (IOException ex) {
                Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public String getFileNameNoExtension(String fileName) {
        if (!fileName.contains(".")) {
            return fileName;
        }
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    public boolean makeDir(String dir) {
        File f = new File(dir);
        if (!f.exists()) {
            return f.mkdirs();
        }
        return false;
    }

    public boolean zipFiles(List<File> listFileToZip, String folderSave, String saveName, boolean isPassword) {

        ModelZipper zip = new ModelZipper(secretZIPKey);
        String rs = "";
        try {
            if (isPassword) {
                rs = zip.pack(listFileToZip, folderSave, saveName);
            } else {
                rs = zip.packNoPassword(listFileToZip, folderSave, saveName);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean unZip(String fileZip, String folderSave) {
        File f = new File(folderSave);
        if (f.exists()) {
            deleteDirectory(f);
        }
        ModelZipper zip = new ModelZipper(secretZIPKey);
        boolean rs1 = unZipWithPassword(zip, fileZip, folderSave);
        if (rs1) {
            return true;
        }
        boolean rs2 = unZipNoPassword(zip, fileZip, folderSave);
        return rs2;
    }

    private boolean unZipWithPassword(ModelZipper zip, String fileZip, String folderSave) {
        try {
            zip.unpack(fileZip, folderSave);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean unZipNoPassword(ModelZipper zip, String fileZip, String folderSave) {
        try {
            zip.unpackNoPassword(fileZip, folderSave);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Read files in folder extracted: signature, [public key], file
    public ModelInputVerify readInputVerify(String folderPath) {
        ModelInputVerify miv = new ModelInputVerify();
        File f = new File(folderPath);
        if (!f.exists() || !f.isDirectory()) {
            return null;
        }
        List<File> listFile = Arrays.asList(f.listFiles());
        // Allow maximum 3 files: signature, [public key], file to verify
        if (listFile.size() > 3) {
            return null;
        }

        for (File file : listFile) {
            // Get public key and algorithm if exist
            String fName = file.getName();
            String fPath = file.getAbsolutePath();
            if (fName.endsWith(".pub")) {
                String strPub = getPublicFileToString(fPath);
                String pubAlgorithm = getAlgorithmFromFileName(fName);
                if (strPub.isEmpty() || pubAlgorithm.isEmpty()
                        || (!pubAlgorithm.equals("DSA")
                        && !pubAlgorithm.equals("RSA"))) {
                    return null;
                }
                miv.setPublickey(strPub);
                miv.setPublickeyAlgorithm(pubAlgorithm);
            } else if (fName.endsWith(".signature")) {
                String hashAlgorithm = "";
                try {
                    hashAlgorithm = fName.substring(0, fName.indexOf("_"));
                    if (!hashAlgorithm.equals("SHA512") && !hashAlgorithm.equals("SHA256")) {
                        return null;
                    }
                } catch (Exception e) {
                }
                byte[] signature = readBinaryFile(fPath);
                if (hashAlgorithm.isEmpty() || signature == null) {
                    return null;
                }
                miv.setSignature(signature);
                miv.setSignatureHashAlgorithm(hashAlgorithm);
            } else {
                miv.setFilePath(fPath);
            }
        }
        return miv;
    }

    public boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (null != files) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    } else {
                        files[i].delete();
                    }
                }
            }
        }
        return (directory.delete());
    }

    public void openExpolorer(String folder){
        try {
            Desktop.getDesktop().open(new File(folder));
        } catch (IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
