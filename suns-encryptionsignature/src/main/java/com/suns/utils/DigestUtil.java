package com.suns.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

/**
 * 通过信息得到摘要信息
 * @author suns suntion@yeah.net
 * @since 2017年11月15日上午10:58:17
 */
public class DigestUtil {
    
    /**
     * 根据数据生成摘要信息
     * @param input  需要生成摘要的数据
     * @param algorithm 算法 有 SHA  MD5 SHA-256
     * @param charset 字符集
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String digestBase64(String input, String algorithm, Charset charset) throws NoSuchAlgorithmException {
        MessageDigest shaDigest = MessageDigest.getInstance(algorithm);
        byte[] inputByte = input.getBytes(charset);
        byte[] digestByte = shaDigest.digest(inputByte);
        return Base64.encodeBase64String(digestByte);
    }
    
    /**
     * 根据数据生成摘要信息 字符集
     * @param input  需要生成摘要的数据
     * @param algorithm 算法 有 SHA  MD5 SHA-256
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String digestBase64UTF8(String input, String algorithm) throws NoSuchAlgorithmException {
        return digestBase64(input, algorithm, Charset.forName("UTF-8"));
        
    }
    
    /**
     * 根据数据生成摘要信息 字符集
     * @param input  需要生成摘要的数据
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String digestBase64SHA256UTF8(String input) throws NoSuchAlgorithmException {
        return digestBase64(input, "SHA-256", Charset.forName("UTF-8"));
    }
}
