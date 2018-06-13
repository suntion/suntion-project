package com.suns.dsa;

import org.apache.commons.codec.binary.Base64;

/**
 * TODO test
 * @author suns suntion@yeah.net
 * @since 2017年11月14日上午10:30:15
 */
public class test {
    public static void main(String[] args) throws Exception {
        String msg = "Hello World";
        DsaDemo dsa = new DsaDemo();
        byte[] sign = dsa.signature(msg.getBytes(), dsa.getPrivateKey());
        boolean flag = dsa.verify(msg.getBytes(), dsa.getPublicKey(), sign);
        String result = flag ? "数字签名匹配" : "数字签名不匹配";
        System.out.println("数字签名：" + Base64.encodeBase64URLSafeString(sign));
        System.out.println("验证结果：" + result);
    }
}
