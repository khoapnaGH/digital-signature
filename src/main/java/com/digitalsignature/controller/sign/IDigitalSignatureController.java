/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.controller.sign;

import com.digitalsignature.model.ModelFriendKey;
import com.digitalsignature.model.ModelMyKey;
import java.security.KeyPair;

/**
 *
 * @author Admin
 */
public interface IDigitalSignatureController {
    KeyPair GenerateKey(int keySize, String algorithm);
    byte[] Sign(String filePath, ModelMyKey mykey, String hashAgorithm);
    boolean Verify(String filePath, byte[] digitalSignature, ModelFriendKey frKey, String hashAgorithm);
    byte[] DoubleSign(String filePath, ModelMyKey mykey, String hashAgorithm);
    boolean VerifyDoubleSign(String filePath, byte[] digitalSignature, ModelFriendKey mykey, String hashAgorithm);
}
