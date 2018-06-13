package com.suns.msgdigest;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

/**
消息摘要算法包括MD(Message Digest，消息摘要算法)、SHA(Secure Hash Algorithm，安全散列算法)、MAC(Message Authentication Code，消息认证码算法)共3大系列，常用于验证数据的完整性，是数字签名算法的核心算法。

MD5和SHA1分别是MD、SHA算法系列中最有代表性的算法。

如今，MD5已被发现有许多漏洞，从而不再安全。SHA算法比MD算法的摘要长度更长，也更加安全。
 * @author suns suntion@yeah.net
 * @since 2017年11月14日上午10:24:50
 */
public class MsgDigestDemo {
    public static void main(String args[]) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String msg = "Hello World!";

        MessageDigest md5Digest = MessageDigest.getInstance("MD5");
        // 更新要计算的内容
        md5Digest.update(msg.getBytes());
        // 完成哈希计算，得到摘要
        byte[] md5Encoded = md5Digest.digest();

        MessageDigest shaDigest = MessageDigest.getInstance("SHA");
        // 更新要计算的内容
        shaDigest.update(msg.getBytes());
        // 完成哈希计算，得到摘要
        byte[] shaEncoded = shaDigest.digest();

        System.out.println("原文: " + msg);
        System.out.println("MD5摘要: " + Base64.encodeBase64URLSafeString(md5Encoded));
        System.out.println("SHA摘要: " + Base64.encodeBase64URLSafeString(shaEncoded));
    }
}
