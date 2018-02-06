package com.grgbanking.framework.util.rsa;

/**
 * @author wyf
 * 处理加减密公共接口
 */
public interface Encrypto {
    String encrypt(String plainText);
    String decrypt(String encryptStr);
}
