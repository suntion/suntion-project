package com.suns.signature;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.suns.utils.RSAUtil;
import com.suns.utils.ParamUtil;

/**
 * client提取消息m的消息摘要h(m)，并使用自己的私钥对摘要h(m)进行加密，生成签名s。
 * client将签名s和消息m一起，使用server发过来的公钥进行加密，获得密文c，发送给server。
 * 
 * @author suns suntion@yeah.net
 * @since 2017年11月14日上午10:37:53
 */
public class 双方都有密钥 {
    /** 数字签名算法。JDK只提供了MD2withRSA, MD5withRSA, SHA1withRSA，其他的算法需要第三方包才能支持 */
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    public static final String clientPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPQGsexvGLWgqkgwz/aAdDnYe39bEoIul1SAMChA3qLqpjzsStaSIOnHfAnqLWUm/SrfXSvvxCbV/SmAbD1favghOSNUQ33aTtQyOF5hg4m1+4nQ4uBIfDdKaQe+yFg4QPCnPCGbwKt/ge9GkOm8yckuWQBDeoTNLOChaIzUcdhQIDAQAB";
    public static final String clientPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAI9Aax7G8YtaCqSDDP9oB0Odh7f1sSgi6XVIAwKEDeouqmPOxK1pIg6cd8CeotZSb9Kt9dK+/EJtX9KYBsPV9q+CE5I1RDfdpO1DI4XmGDibX7idDi4Eh8N0ppB77IWDhA8Kc8IZvAq3+B70aQ6bzJyS5ZAEN6hM0s4KFojNRx2FAgMBAAECgYBQyRRf+/O2EaZU1m8bDjCiihFCxjUNHHsqH91Kquwp9/0xYib6YILLWKQUJaJRWZYkauRp4DjkwUe7vO57vItr24x7n7KX1CAwxKBnxQASdFsIchvEbu6rIiNKFSiS5lPF8Rtrl+RX/GFRfh6XpFECFXQICtVf7l/PUlp0oJ8EAQJBAO8N0TjqEel42YnyQqiN5mSSUh7H7OootDTj7G7m8RwWShXP5OvY4lMKDrJa0o7dUs1FHFKpPdFoh/5QCaKkYoUCQQCZaAxGzfphaxUDVWALl/9+ZGpUYYN9VLQvvHX1TPNwBSCffSyR2G+vRyPwvJ7DFfaoFYPORyuuLpNN2YY0FD8BAkEAucmtT2RrY15vA0zrDYCrDILDm/SZZisYUvrI6DBINyIDlgwqyDmZPBxW67q2Zh4QmkQaDQxHZRauirG9q0/C2QJAXJkgp/CXTQx9D5kOM5FZSGrGgA6odxOU/CePMDV10E1YBs1hBNwXdj7D5AGG940faRArkm0FYct9YQWHa5I5AQJBAK9dh3V+hbaE4DyMSRbnIksQa5s9d6/3NyJmjQYWy4gPYWlSEGFRtGiH2r1d5THQt9C9rAt6XwHEV+x8Xx7EcSE=";

    public static final String serverPublickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCM0ILe+eJycpKMXTbhzkbNLkMDAsgz7GJUhutW/sAa+udiP9FHlxKlnVBevimwGgw6l17VIdz2jThDgMxcpwdRB4MV1boN/X7m7aplvrFEsVa49N2Sx6JaSQGdRO+PjKMikxusdtCbp9uW8vmW9ybb1UwkKazxxy3t8Zh/7mK27QIDAQAB";
    public static final String serverPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIzQgt754nJykoxdNuHORs0uQwMCyDPsYlSG61b+wBr652I/0UeXEqWdUF6+KbAaDDqXXtUh3PaNOEOAzFynB1EHgxXVug39fubtqmW+sUSxVrj03ZLHolpJAZ1E74+MoyKTG6x20Jun25by+Zb3JtvVTCQprPHHLe3xmH/uYrbtAgMBAAECgYB1caL1XezkD3Ly5vvTMTwT/TWe2T8pL0qDvVOjZy3KUuGRLDDcSMAgqShTHL+gRNlWNNG/aVLlv7rhchcAX77VL/lo8FGtS0BIdSQ5oKZLRiqee+Td0iEMWzQtCIBLu3FenoXYXJFIo/tWCJRC0ptD3K/w1o8InBHed3Wl2e5ecQJBAOS/obwvN4jVBu08t9xiZVe7YMkl7tD5yjVYejMso51kSlYRERZgdL7n3DCFH+pi+j4jHC8E13qcRRdNI15q0CMCQQCdlxGDbu9sfPiS8ZwZ8suVYMwyhVTpKBLNKYUZ54ukn3I7e/2DRo6PKP/jb0wZHqxeD1vDuqwI9xLTHmTTd0WvAkEAusbeQNIs/kUzCrLxTbnqFeCh/pxCi8/qE/TSksZ4MiOZrvzwftzzdo3LdLK+hJucyO/SK3JAn5TJtWT+5DFDEQJBAJQIw/feLuSPZIXMlqG9dMZmvoq6uu+mqDq3RQP3gTLsYMdGCvfVQLd0gSOpeieiq7JT5RSw90gYqENPqtEGmpsCQET3DbXEgp83OTG3/axGs+ynZ7WNrpoJoDKC58BIEbsfy52ZpfzjIVkBtPHNWcRiFDqsmbzorDXKc/DV2MHCdDY=";

    public static void main(String[] args) throws Exception {
        Map<String, String> param = client();
        System.out.println("");
        System.out.println("");
        System.out.println("");
        server(param);
    }

    public static String server(Map<String, String> param) throws Exception {
        String sign = param.get("sign");
        String encrypt = param.get("encrypt");
        String digest = param.get("digest");

        // 使用客户公钥解密签名 得到摘要
        byte[] digestByte = RSAUtil.decryptByPublicKey(Base64.decodeBase64(digest), clientPublicKey);
        System.out.println("解密摘要\t" + new String(digestByte));

        byte[] encryptByte = RSAUtil.decryptByPrivateKey(Base64.decodeBase64(encrypt), serverPrivateKey);
        System.out.println("明文:" + new String(encryptByte));

        MessageDigest shaDigest = MessageDigest.getInstance("SHA");
        shaDigest.update(encryptByte);
        digestByte = shaDigest.digest();
        String digest2 = Base64.encodeBase64String(digestByte);
        System.out.println("生成摘要:" + digest2);

        return null;
    }

    /**
     * @return
     * @throws Exception
     */
    public static Map<String, String> client() throws Exception {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("k1", "value1");
        paramMap.put("k2", "value2");
        String param = ParamUtil.mapToSortStr(paramMap, null);
        System.out.println("参数:" + param);

        /**
         * 生成消息摘要digest 并且放入参数中
         */
        MessageDigest shaDigest = MessageDigest.getInstance("SHA");
        shaDigest.update(param.getBytes());
        byte[] digestByte = shaDigest.digest();
        String digest = Base64.encodeBase64String(digestByte);
        System.out.println("digest摘要:" + digest);

        // 摘要加密
        byte[] signbyte = RSAUtil.sign(digest.getBytes(), clientPrivateKey);
        String sign = Base64.encodeBase64String(signbyte);
        System.out.println("sign签名:" + sign);

        /**
         * 服务端 公钥加密
         */
        byte[] encryptByte = RSAUtil.encryptByPublicKey(param.getBytes(), serverPublickey);
        String encrypt = Base64.encodeBase64String(encryptByte);
        System.out.println("encrypt明文加密:" + encrypt);

        Map<String, String> urlMap = new HashMap<String, String>();
        urlMap.put("sign", sign);
        urlMap.put("digest", digest);
        urlMap.put("encrypt", encrypt);
        return urlMap;
    }

}
