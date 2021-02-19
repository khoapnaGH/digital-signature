/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.model;

import java.io.File;
import java.util.List;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.EncryptionMethod;

/**
 *
 * @author Admin
 */
public class ModelZipper {

    private String password;
    private static final String EXTENSION = "zip";

    public ModelZipper(String password) {
        this.password = password;
    }

    public String packNoPassword(List<File> filesToAdd, String folderSave, String nameSave) throws ZipException {
        File f = new File(folderSave + "\\" + nameSave + "." + EXTENSION);
        ZipFile zipFile = new ZipFile(f);
        zipFile.addFiles(filesToAdd);
        return f.getAbsolutePath();
    }

    public void unpackNoPassword(String sourceZipFilePath, String extractedZipFilePath) throws ZipException {
        ZipFile zipFile = new ZipFile(sourceZipFilePath);
        zipFile.extractAll(extractedZipFilePath);
    }
    
    public String pack(List<File> filesToAdd, String folderSave, String nameSave) throws ZipException {
        File f = new File(folderSave + "\\" + nameSave + "." + EXTENSION);
        ZipFile zipFile = new ZipFile(f, this.password.toCharArray());
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setEncryptFiles(true);
        zipParameters.setEncryptionMethod(EncryptionMethod.AES);
        // Below line is optional. AES 256 is used by default. You can override it to use AES 128. AES 192 is supported only for extracting.
        zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);

        zipFile.addFiles(filesToAdd, zipParameters);
        return f.getAbsolutePath();
    }

    public void unpack(String sourceZipFilePath, String extractedZipFilePath) throws ZipException {
        ZipFile zipFile = new ZipFile(sourceZipFilePath, this.password.toCharArray());
        zipFile.extractAll(extractedZipFilePath);
    }
}
