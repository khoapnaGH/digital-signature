/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.dao.interfaces;

import com.digitalsignature.model.ModelFriendKey;
import com.digitalsignature.model.ModelMyKey;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface ISign {
    boolean saveMyKey(ModelMyKey myKey);
    boolean savePubkey(ModelFriendKey frKey);
    List<ModelMyKey> getAllMyKeys();
    List<ModelFriendKey> getAllFriendKeys();
    boolean deleteMyKey(String keyName);
    boolean deleteFriendKey(String keyName);
}
