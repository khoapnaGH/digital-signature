/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.model;

/**
 *
 * @author Admin
 */
public class ModelInputVerify {
    private byte[] signature;
    private String signatureHashAlgorithm;
    private String publickey;
    private String publickeyAlgorithm;
    private String filePath;

    public ModelInputVerify(){
        this.signature = null;
        this.signatureHashAlgorithm = "";
        this.publickey = "";
        this.publickeyAlgorithm = "";
        this.filePath = "";
    }
    
    public ModelInputVerify(byte[] signature, String signatureHashAlgorithm, String publickey, String publickeyAlgorithm, String filePath) {
        this.signature = signature;
        this.signatureHashAlgorithm = signatureHashAlgorithm;
        this.publickey = publickey;
        this.publickeyAlgorithm = publickeyAlgorithm;
        this.filePath = filePath;
    }
    
    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getSignatureHashAlgorithm() {
        return signatureHashAlgorithm;
    }

    public void setSignatureHashAlgorithm(String signatureHashAlgorithm) {
        this.signatureHashAlgorithm = signatureHashAlgorithm;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public String getPublickeyAlgorithm() {
        return publickeyAlgorithm;
    }

    public void setPublickeyAlgorithm(String publickeyAlgorithm) {
        this.publickeyAlgorithm = publickeyAlgorithm;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "ModelInputVerify{" + "signature=" + signature + ", signatureHashAlgorithm=" + signatureHashAlgorithm + ", publickey=" + publickey + ", publickeyAlgorithm=" + publickeyAlgorithm + ", filePath=" + filePath + '}';
    }
    
    
}
