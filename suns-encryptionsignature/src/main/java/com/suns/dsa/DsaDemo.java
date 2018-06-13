package com.suns.dsa;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
算法简述

数字签名算法可以看做是一种带有密钥的消息摘要算法，并且这种密钥包含了公钥和私钥。也就是说，数字签名算法是非对称加密算法和消息摘要算法的结合体。

特点

数字签名算法要求能够验证数据完整性、认证数据来源，并起到抗否认的作用。

原理

数字签名算法包含签名和验证两项操作，遵循私钥签名，公钥验证的方式。

签名时要使用私钥和待签名数据，验证时则需要公钥、签名值和待签名数据，其核心算法主要是消息摘要算法。

常用算法

RSA、DSA、ECDSA
 * @author suns suntion@yeah.net
 * @since 2017年11月14日上午10:28:22
 */
public class DsaDemo {
    public static final String KEY_ALGORITHM = "DSA";

    public enum DsaTypeEn {
        MD5withDSA, SHA1withDSA
    }
    
    /**
     * DSA密钥长度默认1024位。 密钥长度必须是64的整数倍，范围在512~1024之间
     */
    private static final int KEY_SIZE = 1024;
    
    
    private KeyPair keyPair;

    public DsaDemo() throws Exception {
        keyPair = initKey();
    }
    
    
    private KeyPair initKey() throws Exception {
        // 初始化密钥对生成器
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        // 实例化密钥对生成器
        keyPairGen.initialize(KEY_SIZE);
        // 实例化密钥对
        return keyPairGen.genKeyPair();
    }
    
    public byte[] getPublicKey() {
        return keyPair.getPublic().getEncoded();
    }

    public byte[] getPrivateKey() {
        return keyPair.getPrivate().getEncoded();
    }
    
    
    
    public byte[] signature(byte[] data, byte[] privateKey) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey key = keyFactory.generatePrivate(keySpec);

        Signature signature = Signature.getInstance(DsaTypeEn.SHA1withDSA.name());
        signature.initSign(key);
        signature.update(data);
        return signature.sign();
    }

    public boolean verify(byte[] data, byte[] publicKey, byte[] sign) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey key = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(DsaTypeEn.SHA1withDSA.name());
        signature.initVerify(key);
        signature.update(data);
        return signature.verify(sign);
    }

    
}
