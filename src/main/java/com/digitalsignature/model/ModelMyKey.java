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
public class ModelMyKey extends ModelKey{
    
    private int keySize;
    
    public ModelMyKey(String name, String privateKey, String publickey, String algorithm, int keySize) {
        super(name, privateKey, publickey);
        this.algorithm = algorithm;
        this.keySize = keySize;
    }
    
    public ModelMyKey(ModelMyKey mMykey){
        super(mMykey);
        this.keySize = new Integer(String.valueOf(mMykey.getKeySize()));
    }
    
    public int getKeySize() {
        return keySize;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

}
