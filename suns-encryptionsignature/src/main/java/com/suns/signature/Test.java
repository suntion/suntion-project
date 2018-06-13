package com.suns.signature;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * TODO Test
 * 
 * @author suns suntion@yeah.net
 * @since 2017年12月14日上午9:00:31
 */
public class Test {
    private static final String GOLDWALLET_PUBLICKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt1MECdS/gqXREdGVQEau2XupHzCqQ5MLMumDipD13whtX/zqyLdf9sqx2NdCNvGUKtBWQkU3HMylZvxAstI7IBKNXn7YrGQyHIyycl+o51ygkppxQVVzFrnNOjH2qqgEtpiFiOQ9nd6xKLMSKR05B13OBo2t6RPs8SEt2FBfMdqkModAhulwnufdLTzIEO7255SL0GV8FQvnYu3Xovk/+TCi639BgsxpxmnhtVwBGLhXMq01HSCFMIymRX+I5hg/yif+zrnW6PWsza+Geji1HXq+L/kumOFAO+WorT4LCpGHNHo7lHLvIbIFGnA061caOyZI2QeDKU0+DpTmucWUtwIDAQAB";

    private static final String KEYI_PRIVATEKEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCrhT9MZn0A63BIWzeyX+FxJePq96amt9iANWwfnLga+kFK4Vo5YdVZdbLB37HT2GtjoXiRYUrE+dAgcfE1e7JwWSo51sYAxczLL6TBYyQFcmXiTNTepROdQUUaXiSR5S5CVeGryKhXvZEWXQG03wFFV2Uh6aX7WJCJ0JOt5aa9GyBWQx71RTz165mJafb3DjL0tLx8D4kLTTFgO7bLgK+SWRoiblDiOZQn3Sea4hazdB39g8im2uP9bdXBRtNSeJga95VPhW86UTlvp1cNFLLRlweowWepyeNdwHSz0obcduJIZWay+2k/R8VLY6TyAIF9gS5E791H1549wbvzoMW1AgMBAAECggEAQxtB9blPhRGYf6a4tuBOiv9hGehE2oupeBfusruVSHDLJOnga/z8BSFIjBPiNllBQtzFGFQxdbmrlVYfImbAHLa2D6UZGOpKwuBkHImWaU8lz5Fx455Ae1EzISreMfqxy6BgR96dBp8TaeV/qm1pyOB1n/wTRbCT9GeXr/ze7LNPfMfiwUdg21CnTKKn5Ar07YRek5ATro89MGckhYf4WJw4l8dAvOEgW1fIrRlYENrgs8xtArWv5oVdqc+y0tjYBqgBeQC5xh8Hjv40GmxeIExbVYNAuyj0ccJvZx8jMqS2BIhRnrZESap/lnagK26D1MrIhZqVKQ8QJt5MXdSkLQKBgQDzKo2MoHAW8jNyYxXRghD9G/sQx/jKKK3DKg9kjzjqNZaFgdL2mAX/nK+5mS4KovEMHSmK2oGnHJaYpnXkqrteIjMswUwJafxwHyXJhQX4DPrYy3eITw7GuR0MhXU7ZnBmxj1slNHagDSK0TBd2lT4ajzUQ9bbLGLz4jhk/7JyBwKBgQC0kq487S/xFRYgU+f5a6fTzxFZb5eOkPbfe9klK8B9JGeYIbtJUNJ+Tflc57wVJeDao1lTJIfG9H8jUHTkr4zLl/ElPke/ON2hY2DTBh71RIU5OBjmAnclCuLA/kk5Rbk9U1kG5aTcMWoEUw+UG8lgAgnXlttW4ATIVIsN9+yrYwKBgQDb+QSv0HtV5qhvs/8K1hS0Q9mtZplDl+UJBkWCfKXKCcwu3jCP7xqOD5pP1ah26swQX1kzSVO8bvo1AXDo+SIil+GffQViBsTcUPEIChRKPsO11uo9HveJAfCrYReaMqM/9dmfHklw1kWxxmdwn4/6YXDBvNTi1JZ3dY/6ne05hQKBgQCbGxu7jemOZhR5dFlgb87bK5S1hBtlBLRqR1HAkhJn7CtoDCF52ojUoVm/KfbvG3hHleSWfFx802uaA/REs153S26CFTs/0DLf/CMhYLKa716YRc1tVVTOZslcCZEQNwnk2YExYXT5ByEP0eEh0bRqWZXmr9h8iTTSd3xyZTHQDQKBgB1HitsOQ8yeFgYwn2I5lRkymZcPaV9y871B64EV6mq6FaiFL2rnUOz3u4ausMy7LQW1o91CZcrOoIy2xVgcr/E8Nw/UuiUsbutr6IqmY5pP1KDPPVGVl4mceo5uemLa7AQQU4erlW20ieFhAfz7ZguXTYSUg9NSwiaf8vSgEOj+";
    
    private static final String KEYI_PUBLICKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq4U/TGZ9AOtwSFs3sl/hcSXj6vemprfYgDVsH5y4GvpBSuFaOWHVWXWywd+x09hrY6F4kWFKxPnQIHHxNXuycFkqOdbGAMXMyy+kwWMkBXJl4kzU3qUTnUFFGl4kkeUuQlXhq8ioV72RFl0BtN8BRVdlIeml+1iQidCTreWmvRsgVkMe9UU89euZiWn29w4y9LS8fA+JC00xYDu2y4CvklkaIm5Q4jmUJ90nmuIWs3Qd/YPIptrj/W3VwUbTUniYGveVT4VvOlE5b6dXDRSy0ZcHqMFnqcnjXcB0s9KG3HbiSGVmsvtpP0fFS2Ok8gCBfYEuRO/dR9eePcG786DFtQIDAQAB";
    
    /**
     * @param args
     * @throws UnsupportedEncodingException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        String signdata = "Q%2Fl2dpya1lZhidBgL%2FWisOLCsWExYFaFpOsQUpJoO1tONC6xJgwwP3PvxjPKoLjMbCaeldFnVBWCKnLQ2hgJInu0g01n8Y5jF%2F8ououq8JowL6ShoF2Kfwmr7zEdnldsC515hok1q4MJt9Ehd7tJXrbGp%2Bpd%2F4FtQgrj9c3X%2Fw6hXFX1PY4fbOeD8nCcJ6t2dKrjDOheUCp6ecCO2hqsnw5n9Vh2g%2F4MS7wYI%2FFnhA0RxXnURwyPFxPp3W%2Bq2zmmcKsRXTS%2F3ArYUrIwF8cj2jDosxrxaj6c7IdBTf8ryfo%2F%2FgQQefyvI%2Bal9L%2Fy6P9UVe5wKSvgpNm%2BqQBUy59e5A%3D%3D";
        String signdatadecode = URLDecoder.decode(signdata, "UTF-8");
        
        String encryptdata = "m1V9%2Bn%2FHAU3OVwWy3wflQYd7BHbej%2FuFaihKhtyZYveqQBOzCN8F7l0wawsp%2FwqNoc9brpwRUXjMrqAzdRx6ciUk4LyKhLQx9lJu8zrGsuHXYg0DmhEMYISbfZx8WKms1nuPeuBl3Z4NpOV2V531InQZVLADlSX4Uxj6oc8BsposJ26AjCQd%2FMPpT%2F7lIzh47xKp6afMo5lCPeqsd%2FQKEbDp8zWSDlyukKR5NwgtZ6jlwwOlI8%2BG5yKr%2FpsPgx3of1EMSaGEpm4athm6gR%2FtfdOX%2BToQxvrGUaZ30bnI%2FRab574qDUPYJYBfRdoNQ8Ewz5O%2Bh89DLzz%2F7hbobVvgMw%3D%3D";
        String encryptdatadecode = URLDecoder.decode(encryptdata, "UTF-8");
        
        
        // 解密
        String data = EncryptDecryptUtil.buildRSADecryptByPrivateKey(encryptdatadecode, KEYI_PRIVATEKEY);

        System.out.println("解密后" + data);

       boolean isVerify = EncryptDecryptUtil.buildRSAverifyByPublicKey(data, GOLDWALLET_PUBLICKEY, signdatadecode);
    
       System.out.println("验签：" + isVerify);
        
        
    }
    
    
//    public static void main(String[] args) {
//      String json = "{\"orderId\":\"201711181601389442147483647\"}";
//      
//      String encrypt = EncryptDecryptUtil.buildRSAEncryptByPrivateKey(json, KEYI_PRIVATEKEY);
//      
//      System.out.println("密文" + encrypt);
//      
//      String data = EncryptDecryptUtil.buildRSADecryptByPublicKey(encrypt, KEYI_PUBLICKEY);
//      
//      System.out.println("解密" + data);
//  }


}
