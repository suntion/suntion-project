package com.suns.base64;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

/**
Base64编码可用于在HTTP环境下传递较长的标识信息。在其他应用程序中，也常常需要把二进制数据编码为适合放在URL(包括隐藏表单域)中的形式。
此时，采用Base64编码具有不可读性，即所编码的数据不会被人用肉眼所直接看到，算是起到一个加密的作用。
然而，标准的Base64并不适合直接放在URL里传输，因为URL编码器会把标准Base64中的“/”和“+”字符变为形如“%XX”的形式，而这些“%”号在存入数据库时还需要再进行转换，因为ANSI SQL中已将“%”号用作通配符。

为解决此问题，可采用一种用于URL的改进Base64编码，它不仅在末尾填充‘=‘号，并将标准Base64中的“+”和“/”分别改成了“-”和“_”，这样就免去了在URL编解码和数据库存储时所要作的转换，
避免了编码信息长度在此过程中的增加，并统一了数据库、表单等处对象标识符的格式。
另有一种用于正则表达式的改进Base64变种，它将“+”和“/”改成了“!”和“-”，因为“+”,“*”以及前面在IRCu中用到的“[”和“]”在正则表达式中都可能具有特殊含义。
此外还有一些变种，它们将“+/”改为“_-”或“._”（用作编程语言中的标识符名称）或“.-”（用于XML中的Nmtoken）甚至“_:”（用于XML中的Name）。
 * @author suns suntion@yeah.net
 * @since 2017年11月14日上午9:32:06
 */
public class Base64Demo {
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "https://www.baidu.com/s?wd=Base64&rsv_spt=1&rsv_iqid=0xa9188d560005131f&issp=1&f=3&rsv_bp=0&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=1&rsv_sug1=1&rsv_sug7=001&rsv_sug2=1&rsp=0&rsv_sug9=es_2_1&rsv_sug4=2153&rsv_sug=9";
        
        byte[] baseEncoded = Base64.encodeBase64(url.getBytes("UTF-8"));
        byte[] baseDecoded = Base64.decodeBase64(baseEncoded);
        
        System.out.println("baseEncoded:" + new String(baseEncoded));
        System.out.println("baseDecoded:" + new String(baseDecoded));
    
    }
}
