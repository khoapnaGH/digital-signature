/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.model;

/**
 *
 * @author Admin
 * Prepare input file ready to sign and out put this after signed
 */
public class ModelResult {
    private byte[] signature;
    private String filePath;
    private String fileName;
    private String status;
    private int speed;
    private int id;
    private String folderRS;
    private String hashAlgorithm;

    public ModelResult() {
        this.signature = null;
        this.filePath = "";
        this.fileName = "";
        this.status = "Ready to Sign";
        this.speed = 0;
        this.id = -1;
        this.folderRS = "";
    }
    
    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFolderRS() {
        return folderRS;
    }

    public void setFolderRS(String folderRS) {
        this.folderRS = folderRS;
    }

    
    
    @Override
    public String toString() {
        return "ModelResult{" + "signature=" + signature + ", filePath=" + filePath + ", fileName=" + fileName + ", status=" + status + ", speed=" + speed + ", id=" + id + ", folderVerifyRS=" + folderRS + "}";
    }

    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    public void setHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

}
