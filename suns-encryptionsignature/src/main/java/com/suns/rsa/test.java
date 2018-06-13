package com.suns.rsa;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

/**
 * TODO test
 * @author suns suntion@yeah.net
 * @since 2017年11月14日上午10:18:49
 */
public class test {
    public static void main(String[] args) throws UnsupportedEncodingException, Exception {
        String msg = "Hello World!";
        RSACoder coder = new RSACoder();
        // 私钥加密，公钥解密
        byte[] ciphertext = coder.encryptByPrivateKey(msg.getBytes("UTF8"), coder.keyPair.getPrivate().getEncoded());
        byte[] plaintext = coder.decryptByPublicKey(ciphertext, coder.keyPair.getPublic().getEncoded());

        // 公钥加密，私钥解密
        byte[] ciphertext2 = coder.encryptByPublicKey(msg.getBytes(), coder.keyPair.getPublic().getEncoded());
        byte[] plaintext2 = coder.decryptByPrivateKey(ciphertext2, coder.keyPair.getPrivate().getEncoded());

        System.out.println("原文：" + msg);
        System.out.println("公钥：" + Base64.encodeBase64URLSafeString(coder.keyPair.getPublic().getEncoded()));
        System.out.println("私钥：" + Base64.encodeBase64URLSafeString(coder.keyPair.getPrivate().getEncoded()));

        System.out.println("============== 私钥加密，公钥解密 ==============");
        System.out.println("密文：" + Base64.encodeBase64URLSafeString(ciphertext));
        System.out.println("明文：" + new String(plaintext));

        System.out.println("============== 公钥加密，私钥解密 ==============");
        System.out.println("密文：" + Base64.encodeBase64URLSafeString(ciphertext2));
        System.out.println("明文：" + new String(plaintext2));
    }
}
