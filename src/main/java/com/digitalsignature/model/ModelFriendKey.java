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
public class ModelFriendKey extends ModelKey{
    
    private String note;
    
    public ModelFriendKey(String name, String publickey, String note) {
        super(name, "", publickey);
        this.note = note;
    }
    
    public ModelFriendKey(ModelFriendKey mfkey){
        super(mfkey);
        this.note = new String(mfkey.getNote());
    }
    
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    } 
}
