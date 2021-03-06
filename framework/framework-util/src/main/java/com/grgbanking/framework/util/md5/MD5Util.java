package com.grgbanking.framework.util.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author wyf
 */
public class MD5Util {

    /**
     * MD5加密
     * @param data
     * @return
     * @throws Exception
     */
    public synchronized static final String MD5(String data) throws Exception{
        MessageDigest digest = MessageDigest.getInstance("MD5");
        if (digest == null) {
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException nsae) {
                throw nsae;
            }
        }
        if (digest != null) {
            digest.update(data.getBytes());
            return encodeHex(digest.digest()).toUpperCase();
        }
        return null;
    }

    /**
     * 将数组转换成16进制字符串
     *
     * @param bytes
     * @return
     */
    public static final String encodeHex(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buf.toString();
    }
}
