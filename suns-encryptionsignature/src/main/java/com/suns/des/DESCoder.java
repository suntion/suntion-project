package com.suns.des;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

/**
对称加密要求加密与解密使用同一个密钥，解密是加密的逆运算。由于加密、解密使用同一个密钥，这要求通信双方必须在通信前商定该密钥，并妥善保存该密钥。

对称加密体制分为两种：

一种是对明文的单个位（或字节）进行运算，称为流密码，也称为序列密码；

一种是把明文信息划分为不同的组（或块）结构，分别对每个组（或块）进行加密、解密，称为分组密码。
 * @author suns suntion@yeah.net
 * @since 2017年11月14日上午10:00:48
 */
public class DESCoder {

    public static final String KEY_ALGORITHM_DES = "DES";
    public static final String CIPHER_DES_DEFAULT = "DES";
    private static final String SEED = "%%%i lovejava***"; // 用于生成随机数的种子

    public static final String CIPHER_DES_ECB_PKCS5PADDING = "DES/ECB/PKCS5Padding"; // 算法/模式/补码方式
    public static final String CIPHER_DES_CBC_PKCS5PADDING = "DES/CBC/PKCS5Padding";
    public static final String CIPHER_DES_CBC_NOPADDING = "DES/CBC/NoPadding";

    private Key key;
    private Cipher cipher;
    private String transformation;

    /**
     * @param transformation
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public DESCoder() throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.key = initKey();
        this.cipher = Cipher.getInstance(CIPHER_DES_DEFAULT);
        this.transformation = CIPHER_DES_DEFAULT;
    }

    /**
     * @param transformation
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public DESCoder(String transformation) throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.key = initKey();
        this.cipher = Cipher.getInstance(transformation);
        this.transformation = transformation;
    }

    private Key initKey() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom(SEED.getBytes());
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM_DES);
        keyGenerator.init(secureRandom);
        return keyGenerator.generateKey();
    }

    private byte[] getIV() {
        String iv = "01234567"; // IV length: must be 8 bytes long
        return iv.getBytes();
    }

    public byte[] encrypt(byte[] input) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        if (transformation.equals(CIPHER_DES_CBC_PKCS5PADDING) || transformation.equals(CIPHER_DES_CBC_NOPADDING)) {
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(getIV()));
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        }
        return cipher.doFinal(input);
    }

    public byte[] decrypt(byte[] input) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        if (transformation.equals(CIPHER_DES_CBC_PKCS5PADDING) || transformation.equals(CIPHER_DES_CBC_NOPADDING)) {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(getIV()));
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key);
        }
        return cipher.doFinal(input);
    }
}
