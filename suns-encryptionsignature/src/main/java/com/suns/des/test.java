package com.suns.des;

import org.apache.commons.codec.binary.Base64;

/**
 * TODO DESCmain
 * @author suns suntion@yeah.net
 * @since 2017年11月14日上午10:12:26
 */
public class test {
    public static void main(String[] args) throws Exception {
        DESCoder aes = new DESCoder("DES/ECB/PKCS5Padding");

        String msg = "Hello World!";
        System.out.println("原文: " + msg);
        byte[] encoded = aes.encrypt(msg.getBytes("UTF8"));
        String encodedBase64 = Base64.encodeBase64String(encoded);
        System.out.println("密文: " + encodedBase64);

        byte[] decodedBase64 = Base64.decodeBase64(encodedBase64);
        byte[] decoded = aes.decrypt(decodedBase64);
        System.out.println("明文: " + new String(decoded));
    }
}