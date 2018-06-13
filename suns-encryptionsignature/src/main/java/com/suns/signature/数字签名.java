package com.suns.signature;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.suns.utils.ParamUtil;
import com.suns.utils.RSAUtil;

/**
 * client提取消息m的消息摘要h(m)，并使用自己的私钥对摘要h(m)进行加密，生成签名s。
 * client将签名s和消息m一起，使用server发过来的公钥进行加密，获得密文c，发送给server。
 * 
 * @author suns suntion@yeah.net
 * @since 2017年11月14日上午10:37:53
 */
public class 数字签名 {
    /** 数字签名算法。JDK只提供了MD2withRSA, MD5withRSA, SHA1withRSA，其他的算法需要第三方包才能支持 */
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    public static final String clientPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPQGsexvGLWgqkgwz/aAdDnYe39bEoIul1SAMChA3qLqpjzsStaSIOnHfAnqLWUm/SrfXSvvxCbV/SmAbD1favghOSNUQ33aTtQyOF5hg4m1+4nQ4uBIfDdKaQe+yFg4QPCnPCGbwKt/ge9GkOm8yckuWQBDeoTNLOChaIzUcdhQIDAQAB";
    public static final String clientPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAI9Aax7G8YtaCqSDDP9oB0Odh7f1sSgi6XVIAwKEDeouqmPOxK1pIg6cd8CeotZSb9Kt9dK+/EJtX9KYBsPV9q+CE5I1RDfdpO1DI4XmGDibX7idDi4Eh8N0ppB77IWDhA8Kc8IZvAq3+B70aQ6bzJyS5ZAEN6hM0s4KFojNRx2FAgMBAAECgYBQyRRf+/O2EaZU1m8bDjCiihFCxjUNHHsqH91Kquwp9/0xYib6YILLWKQUJaJRWZYkauRp4DjkwUe7vO57vItr24x7n7KX1CAwxKBnxQASdFsIchvEbu6rIiNKFSiS5lPF8Rtrl+RX/GFRfh6XpFECFXQICtVf7l/PUlp0oJ8EAQJBAO8N0TjqEel42YnyQqiN5mSSUh7H7OootDTj7G7m8RwWShXP5OvY4lMKDrJa0o7dUs1FHFKpPdFoh/5QCaKkYoUCQQCZaAxGzfphaxUDVWALl/9+ZGpUYYN9VLQvvHX1TPNwBSCffSyR2G+vRyPwvJ7DFfaoFYPORyuuLpNN2YY0FD8BAkEAucmtT2RrY15vA0zrDYCrDILDm/SZZisYUvrI6DBINyIDlgwqyDmZPBxW67q2Zh4QmkQaDQxHZRauirG9q0/C2QJAXJkgp/CXTQx9D5kOM5FZSGrGgA6odxOU/CePMDV10E1YBs1hBNwXdj7D5AGG940faRArkm0FYct9YQWHa5I5AQJBAK9dh3V+hbaE4DyMSRbnIksQa5s9d6/3NyJmjQYWy4gPYWlSEGFRtGiH2r1d5THQt9C9rAt6XwHEV+x8Xx7EcSE=";

    public static final String serverPublickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCM0ILe+eJycpKMXTbhzkbNLkMDAsgz7GJUhutW/sAa+udiP9FHlxKlnVBevimwGgw6l17VIdz2jThDgMxcpwdRB4MV1boN/X7m7aplvrFEsVa49N2Sx6JaSQGdRO+PjKMikxusdtCbp9uW8vmW9ybb1UwkKazxxy3t8Zh/7mK27QIDAQAB";
    public static final String serverPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIzQgt754nJykoxdNuHORs0uQwMCyDPsYlSG61b+wBr652I/0UeXEqWdUF6+KbAaDDqXXtUh3PaNOEOAzFynB1EHgxXVug39fubtqmW+sUSxVrj03ZLHolpJAZ1E74+MoyKTG6x20Jun25by+Zb3JtvVTCQprPHHLe3xmH/uYrbtAgMBAAECgYB1caL1XezkD3Ly5vvTMTwT/TWe2T8pL0qDvVOjZy3KUuGRLDDcSMAgqShTHL+gRNlWNNG/aVLlv7rhchcAX77VL/lo8FGtS0BIdSQ5oKZLRiqee+Td0iEMWzQtCIBLu3FenoXYXJFIo/tWCJRC0ptD3K/w1o8InBHed3Wl2e5ecQJBAOS/obwvN4jVBu08t9xiZVe7YMkl7tD5yjVYejMso51kSlYRERZgdL7n3DCFH+pi+j4jHC8E13qcRRdNI15q0CMCQQCdlxGDbu9sfPiS8ZwZ8suVYMwyhVTpKBLNKYUZ54ukn3I7e/2DRo6PKP/jb0wZHqxeD1vDuqwI9xLTHmTTd0WvAkEAusbeQNIs/kUzCrLxTbnqFeCh/pxCi8/qE/TSksZ4MiOZrvzwftzzdo3LdLK+hJucyO/SK3JAn5TJtWT+5DFDEQJBAJQIw/feLuSPZIXMlqG9dMZmvoq6uu+mqDq3RQP3gTLsYMdGCvfVQLd0gSOpeieiq7JT5RSw90gYqENPqtEGmpsCQET3DbXEgp83OTG3/axGs+ynZ7WNrpoJoDKC58BIEbsfy52ZpfzjIVkBtPHNWcRiFDqsmbzorDXKc/DV2MHCdDY=";

    public static void main(String[] args) throws Exception {
        客户端私钥加密摘要服务端公钥解密();
        
//        客户端公钥加密摘要服务端私钥解密();
        
        /**
         * sign_type  签名类型
         * sign_data  签名数据
         * encrypt_data 加密数据
         * encrypt_type 加密类型
         */
    }


    private static void 客户端公钥加密摘要服务端私钥解密() throws Exception {
        System.err.println("------------------客户端公钥加密摘要服务端私钥解密");
        
        System.err.println("------客户端------");
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("k1", "value1");
        paramMap.put("k2", "value2");
        String param = ParamUtil.mapToSortStr(paramMap, null);
        System.out.println("参数:\n\t" + param);

        //生成消息摘要digest
        MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
        byte[] digestByte = shaDigest.digest(param.getBytes());
        System.out.println("生成摘要信息:\n\t" + Base64.encodeBase64String(digestByte));

        // 摘要加密
        byte[] encryptDigesByte = RSAUtil.encryptByPublicKey(digestByte, serverPublickey);
        System.out.println("使用服务器公钥加密摘要:\n\t" + Base64.encodeBase64String(encryptDigesByte));

        //客户端签名数据加入参数
        paramMap.put("sign_data", Base64.encodeBase64String(encryptDigesByte));
        System.out.println("加密后的摘要放入参数中:\n\t" + ParamUtil.mapToSortStr(paramMap, null));
        
        
        System.err.println("-----发送服务器------");
        
        
        byte[] digestsign = Base64.decodeBase64(paramMap.get("sign_data"));
        
        byte[] ssSignByte = RSAUtil.decryptByPrivateKey(digestsign, serverPrivateKey);
        System.out.println("服务器使用私钥解密摘要:\n\t" + Base64.encodeBase64String(ssSignByte));
        
        String serverParam = ParamUtil.mapToSortStr(paramMap, null);
        shaDigest.update(serverParam.getBytes());
        byte[] serverDigestByte = shaDigest.digest();
        System.out.println("服务器取出sign信息后，生成摘要信息:\n\t" + Base64.encodeBase64String(serverDigestByte));
        if (Base64.encodeBase64String(ssSignByte).equals(Base64.encodeBase64String(serverDigestByte))) {
            System.out.println("摘要相等验证通过，参数:\n\t" + serverParam);
        }
        
    }


    /**
     * 签名认证是对非对称加密技术与数字摘要技术的综合运用，指的是将通信内容的摘要信息使用发送者的私钥进行加密，然后将密文与原文一起传输给信息的接收者，
     * 接收者通过发送者的公钥信息来解密被加密的摘要作息，然后使用与发送者相同的摘要算法，对接收到的内容采用相同的方式方式产生摘要串，与解密的摘要串进行对比，
     * 如果相同，则说明接收到的内容是完整的，在传输过程中没有受到第三方的篡改，否则说明通信内容已被第三方修改
     */
    public static void 客户端私钥加密摘要服务端公钥解密() throws Exception {
        System.err.println("------------------客户端私钥加密摘要服务端公钥解密");
        
        System.err.println("------客户端------");
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("k1", "value1");
        paramMap.put("k2", "value2");
        String param = ParamUtil.mapToSortStr(paramMap, null);
        System.out.println("参数:\n\t" + param);

        //生成消息摘要digest
        MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
        byte[] digestByte = shaDigest.digest(param.getBytes());
        System.out.println("生成摘要信息:\n\t" + Base64.encodeBase64String(digestByte));

        // 摘要加密
        byte[] signbyte = RSAUtil.encryptByPrivateKey(digestByte, clientPrivateKey);
        System.out.println("使用客户端私钥加密摘要:\n\t" + Base64.encodeBase64String(signbyte));

        //客户端签名数据加入参数
        paramMap.put("sign_data", Base64.encodeBase64String(signbyte));
        System.out.println("加密后的摘要放入参数中:\n\t" + ParamUtil.mapToSortStr(paramMap, null));
        
        
        System.err.println("-----发送服务器------");
        
        
        byte[] digestsign = Base64.decodeBase64(paramMap.get("sign_data"));
        
        byte[] ssSignByte = RSAUtil.decryptByPublicKey(digestsign, clientPublicKey);
        System.out.println("服务器使用客户端共钥解密摘要:\n\t" + Base64.encodeBase64String(ssSignByte));
        
        String serverParam = ParamUtil.mapToSortStr(paramMap, null);
        System.out.println(serverParam);
        byte[] serverDigestByte = shaDigest.digest(serverParam.getBytes());
        System.out.println("服务器取出sign信息后，生成摘要信息:\n\t" + Base64.encodeBase64String(serverDigestByte));
        
        if (Base64.encodeBase64String(ssSignByte).equals(Base64.encodeBase64String(serverDigestByte))) {
            System.out.println("摘要相等验证通过，参数:\n\t" + serverParam);
        }
    }

}
