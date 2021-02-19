/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.dao.concrete;

import com.digitalsignature.dao.factory.DaoFactory;
import com.digitalsignature.dao.interfaces.ISign;
import com.digitalsignature.model.ModelFriendKey;
import com.digitalsignature.model.ModelMyKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SignDAO implements ISign{

    private static final String 
    INSERT_MY_KEY = "INSERT INTO mykeys (name, private_key, public_key, algorithm, key_size) VALUES (?, ?, ?, ?, ?)";
    
    private static final String
    INSERT_FRIEND_KEY = "INSERT INTO friendkey (name, public_key,  note, algorithm) VALUES (?, ?, ?, ?)";
    
    private static final String
    GET_ALL_MY_KEY = "SELECT * from mykeys";
    
    private static final String
    GET_ALL_FRIEND_KEY = "SELECT * from friendkey";
    
    private static final String
    DEL_MY_KEY = "DELETE FROM mykeys WHERE name = ?";
    
    private static final String
    DEL_FRIEND_KEY = "DELETE FROM friendkey WHERE name = ?";
    
    private static final String
    GET_AES_KEY = "SELECT * FROM secure LIMIT 1";
    
    private DaoFactory dao;
    
    public SignDAO(){
        dao = new DaoFactory();
    }
    
    @Override
    public boolean saveMyKey(ModelMyKey myKey) {
        try {
            Connection conn = dao.getConnection();
            
            PreparedStatement stm = conn.prepareStatement(INSERT_MY_KEY);
            
            stm.setString(1, myKey.getName());
            stm.setString(2, myKey.getPrivateKey());
            stm.setString(3, myKey.getPublickey());
            stm.setString(4, myKey.getAlgorithm());
            stm.setInt(5, myKey.getKeySize());

            stm.executeUpdate();
            
            stm.close();
            conn.close();
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean savePubkey(ModelFriendKey frKey) {
        try {
            Connection conn = dao.getConnection();
            
            PreparedStatement stm = conn.prepareStatement(INSERT_FRIEND_KEY);
            
            stm.setString(1, frKey.getName());
            stm.setString(2, frKey.getPublickey());
            stm.setString(3, frKey.getNote());
            stm.setString(4, frKey.getAlgorithm());
            
            stm.executeUpdate();
            
            stm.close();
            conn.close();
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<ModelMyKey> getAllMyKeys() {
        
        List<ModelMyKey> listMyKeys = new ArrayList<>();
        
        try {
            Connection conn = dao.getConnection();
            
            PreparedStatement stm = conn.prepareStatement(GET_ALL_MY_KEY);
            
            ResultSet rset = stm.executeQuery();
            
            while(rset.next()){
                listMyKeys.add(createMyKey(rset));
            }
            
            stm.close();
            conn.close();       
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return listMyKeys;
    }

    @Override
    public List<ModelFriendKey> getAllFriendKeys() {
                
        List<ModelFriendKey> listFrKey = new ArrayList<>();
        
        try {
            Connection conn = dao.getConnection();
            
            PreparedStatement stm = conn.prepareStatement(GET_ALL_FRIEND_KEY);
            
            ResultSet rset = stm.executeQuery();
            
            while(rset.next()){
                listFrKey.add(createFriendKey(rset));
            }
            
            stm.close();
            conn.close();       
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return listFrKey;
    }

    @Override
    public boolean deleteMyKey(String keyName) {
        try {
            Connection conn = dao.getConnection();
            
            PreparedStatement stm = conn.prepareStatement(DEL_MY_KEY);
            
            stm.setString(1, keyName);
            
            stm.executeUpdate();
            
            stm.close();
            conn.close();
            
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteFriendKey(String keyName) {
       try {
            Connection conn = dao.getConnection();
            
            PreparedStatement stm = conn.prepareStatement(DEL_FRIEND_KEY);
            
            stm.setString(1, keyName);
            
            stm.executeUpdate();
            
            stm.close();
            conn.close();
            
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private ModelMyKey createMyKey(ResultSet rset){
        ModelMyKey myKey = null;
        try {
            myKey = new ModelMyKey(rset.getString("name"), 
                    rset.getString("private_key"), 
                    rset.getString("public_key"), 
                    rset.getString("algorithm"), 
                    rset.getInt("key_size"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myKey;
    }
    
    private ModelFriendKey createFriendKey(ResultSet rset){
        ModelFriendKey frKey = null;
        try {
            frKey = new ModelFriendKey(rset.getString("name"), 
                    rset.getString("public_key"), 
                    rset.getString("note"));
            frKey.setAlgorithm(rset.getString("algorithm"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return frKey;
    }
    
    public String getAESKey(){
        String key = "";
        try {
            Connection conn = dao.getConnection();
            
            PreparedStatement stm = conn.prepareStatement(GET_AES_KEY);
            
            ResultSet rset = stm.executeQuery();
            
            while(rset.next()){
                key = rset.getString("key");
            }

            stm.close();
            conn.close();       
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return key;
    }
}
