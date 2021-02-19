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
public class ModelKey {

    protected String name;
    protected String privateKey;
    protected String publickey;
    protected String algorithm;

    public ModelKey(String name, String privateKey, String publickey) {
        this.name = name;
        this.privateKey = privateKey;
        this.publickey = publickey;
    }
    
    public ModelKey(ModelKey key){
        this.name = new String(key.getName());
        this.privateKey = new String(key.getPrivateKey());
        this.publickey = new String(key.getPublickey());
        this.algorithm = new String(key.getAlgorithm());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}
