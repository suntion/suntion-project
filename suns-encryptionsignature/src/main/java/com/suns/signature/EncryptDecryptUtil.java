package com.suns.signature;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

/**
 * 加解密工具类
 */
public final class EncryptDecryptUtil {
	public static final String CHARSET = "UTF-8";
	// 密钥算法
	public static final String ALGORITHM_RSA = "RSA";
	public static final String ALGORITHM_RSA_SIGN = "SHA256WithRSA";
	public static final int ALGORITHM_RSA_PRIVATE_KEY_LENGTH = 2048;

	private EncryptDecryptUtil() {
	}

	/**
	 * 初始化RSA算法密钥对
	 *
	 * @param keysize
	 *            RSA1024已经不安全了,建议2048
	 * @return 经过Base64编码后的公私钥Map, 键名分别为publicKey和privateKey
	 */
	public static Map<String, String> initRSAKey(int keysize) throws UnsupportedEncodingException {

		if (keysize != ALGORITHM_RSA_PRIVATE_KEY_LENGTH) {
			throw new IllegalArgumentException("RSA1024已经不安全了,请使用" + ALGORITHM_RSA_PRIVATE_KEY_LENGTH + "初始化RSA密钥对");
		}
		// 为RSA算法创建一个KeyPairGenerator对象
		KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance(ALGORITHM_RSA);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("No such algorithm-->[" + ALGORITHM_RSA + "]");
		}
		// 初始化KeyPairGenerator对象,不要被initialize()源码表面上欺骗,其实这里声明的size是生效的
		kpg.initialize(ALGORITHM_RSA_PRIVATE_KEY_LENGTH);
		// 生成密匙对
		KeyPair keyPair = kpg.generateKeyPair();
		// 得到公钥
		Key publicKey = keyPair.getPublic();
		String publicKeyStr = new String(Base64.encodeBase64(publicKey.getEncoded()), CHARSET);
		// 得到私钥
		Key privateKey = keyPair.getPrivate();
		String privateKeyStr = new String(Base64.encodeBase64(privateKey.getEncoded()), CHARSET);

		Map<String, String> keyPairMap = new HashMap<String, String>();
		keyPairMap.put("publicKey", publicKeyStr);
		keyPairMap.put("privateKey", privateKeyStr);
		System.out.println("publickey=" + publicKeyStr);
		System.out.println("privatekey=" + privateKeyStr);
		return keyPairMap;
	}

	/**
	 * RSA算法公钥加密数据
	 *
	 * @param data
	 *            待加密的明文字符串
	 * @param key
	 *            RSA公钥字符串
	 * @return RSA公钥加密后的经过Base64编码的密文字符串
	 */
	public static String buildRSAEncryptByPublicKey(String data, String key) {
		try {
			// 通过X509编码的Key指令获得公钥对象
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(key.getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			Key publicKey = keyFactory.generatePublic(x509KeySpec);
			// encrypt
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return new String(Base64.encodeBase64(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET))));
		} catch (Exception e) {
			throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
		}
	}

	/**
	 * RSA算法公钥解密数据
	 *
	 * @param data
	 *            待解密的经过Base64编码的密文字符串
	 * @param key
	 *            RSA公钥字符串
	 * @return RSA公钥解密后的明文字符串
	 */
	public static String buildRSADecryptByPublicKey(String data, String key) {
		try {
			// 通过X509编码的Key指令获得公钥对象
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(key.getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			Key publicKey = keyFactory.generatePublic(x509KeySpec);
			// decrypt
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data.getBytes())), CHARSET);
		} catch (Exception e) {
			throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
		}
	}

	/**
	 * RSA算法私钥加密数据
	 *
	 * @param data
	 *            待加密的明文字符串
	 * @param key
	 *            RSA私钥字符串
	 * @return RSA私钥加密后的经过Base64编码的密文字符串
	 */
	public static String buildRSAEncryptByPrivateKey(String data, String key) {
		try {
			// 通过PKCS#8编码的Key指令获得私钥对象
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key.getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
			// encrypt
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			return new String(Base64.encodeBase64(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET))));
		} catch (Exception e) {
			throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
		}
	}

	/**
	 * RSA算法私钥解密数据
	 * 
	 * @param data
	 *            待解密的经过Base64编码的密文字符串
	 * @param key
	 *            RSA私钥字符串
	 * @return RSA私钥解密后的明文字符串
	 */
	public static String buildRSADecryptByPrivateKey(String data, String key) {
		try {
			// 通过PKCS#8编码的Key指令获得私钥对象
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key.getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
			// decrypt
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data.getBytes())), CHARSET);
		} catch (Exception e) {
			throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
		}
	}

	/**
	 * RSA算法使用私钥对数据生成数字签名
	 *
	 * @param data
	 *            待签名的明文字符串
	 * @param key
	 *            RSA私钥字符串
	 * @return RSA私钥签名后的经过Base64编码的字符串
	 */
	public static String buildRSASignByPrivateKey(String data, String key) {
		try {
			// 通过PKCS#8编码的Key指令获得私钥对象
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key.getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
			// sign
			Signature signature = Signature.getInstance(ALGORITHM_RSA_SIGN);
			signature.initSign(privateKey);
			signature.update(data.getBytes(CHARSET));
			return new String(Base64.encodeBase64(signature.sign()));
		} catch (Exception e) {
			throw new RuntimeException("签名字符串[" + data + "]时遇到异常", e);
		}
	}

	/**
	 * RSA算法使用公钥校验数字签名
	 *
	 * @param data
	 *            参与签名的明文字符串
	 * @param key
	 *            RSA公钥字符串
	 * @param sign
	 *            RSA签名得到的经过Base64编码的字符串
	 * @return true--验签通过,false--验签未通过
	 */
	public static boolean buildRSAverifyByPublicKey(String data, String key, String sign) {
		try {
			// 通过X509编码的Key指令获得公钥对象
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(key.getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
			// verify
			Signature signature = Signature.getInstance(ALGORITHM_RSA_SIGN);
			signature.initVerify(publicKey);
			signature.update(data.getBytes(CHARSET));
			return signature.verify(Base64.decodeBase64(sign.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException("验签失败", e);
		}
	}

	/**
	 * RSA算法分段加解密数据
	 *
	 * @param cipher
	 *            初始化了加解密工作模式后的javax.crypto.Cipher对象
	 * @param opmode
	 *            加解密模式,值为javax.crypto.Cipher.ENCRYPT_MODE/DECRYPT_MODE
	 * @return 加密或解密后得到的数据的字节数组
	 */
	private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas) {
		int maxBlock = 0;
		if (opmode == Cipher.DECRYPT_MODE) {
			maxBlock = ALGORITHM_RSA_PRIVATE_KEY_LENGTH / 8;
		} else {
			maxBlock = ALGORITHM_RSA_PRIVATE_KEY_LENGTH / 8 - 11;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] buff;
		int i = 0;
		try {
			while (datas.length > offSet) {
				if (datas.length - offSet > maxBlock) {
					buff = cipher.doFinal(datas, offSet, maxBlock);
				} else {
					buff = cipher.doFinal(datas, offSet, datas.length - offSet);
				}
				out.write(buff, 0, buff.length);
				i++;
				offSet = i * maxBlock;
			}
		} catch (Exception e) {
			throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
		}
		byte[] resultDatas = out.toByteArray();
		IOUtils.closeQuietly(out);
		return resultDatas;
	}

	public static void main(String[] args) {
		String publicKeyYuhai = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnSTsJINtPe2aXUatUz5wU9EHgxm+W+ZCPN3BdcbhlNKzvIIIyjIWhMnPeWwVefJQKAJHnNtikrwq5AB8u1UxeBi0ZFXr4SfgFKpP9o2+EA"
				+ "wytC7BPX+cXUs2AQnDw2Qkwu4dqGDEp9qyjS78jdBZsin9mrSGeO9m6x4YpMoKjqSHwZPAreDc0yLKl5jXgiDdAJOSAAtaoRROYzEhmmXUGAP+m8TgXQuNXd1D0orLY/CS9QUrqefKto01+BlODFUjEkVJ/nuw1E"
				+ "KqzVNMU4RkDAsQKRWKJdepAyV/rKLYn3Ejg4E7oV8n3GQ9MjQ6w0iIGAs3swgmjK6/+4Tc9f5fwQIDAQAB";
		String privateKeyYuhai = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCdJOwkg2097ZpdRq1TPnBT0QeDGb5b5kI83cF1xuGU0rO8ggjKMhaEyc95bBV58lAoAkec22KSvCrkAHy7VTF4GLRkVevhJ+AUq"
				+ "k/2jb4QDDK0LsE9f5xdSzYBCcPDZCTC7h2oYMSn2rKNLvyN0FmyKf2atIZ472brHhikygqOpIfBk8Ct4NzTIsqXmNeCIN0Ak5IAC1qhFE5jMSGaZdQYA/6bxOBdC41d3UPSistj8JL1BSup58q2jTX4GU4MVSMSR"
				+ "Un+e7DUQqrNU0xThGQMCxApFYol16kDJX+sotifcSODgTuhXyfcZD0yNDrDSIgYCzezCCaMrr/7hNz1/l/BAgMBAAECggEAXGf73LTUJlh0L2mCPrM2xnLiofsfb1cFmFkmjCV5PVRxkOY+WPf/OfKaaGJgdqdA7"
				+ "2X3yotPec3gwtZugpJkVqTgXygPXwVdxYkpIGjVXQZvQnlD9O/3ePzjMtUVIu7elyoCkqLkwXffCxqYNKRSOmxqzvBdKvxMXvIBt9V/Q8Ua1wXhFZ005mRbl3r8b4IDhFumbPKP1Y7oN2cFl085DPJx38N9Yqsya"
				+ "loUTmG5p4jF3Fnm/C0HUYQAqQX+aEiH9xDo0V9XysMfF/aLfYOn3QMpeNHdgAlNL655Rh897Fw1zor4QBJHRc8d9kJI7yj5+PxEe3HCUMVmiuo2mYLHKQKBgQDib/TiQqoxRa1CjD8BTmseZVKmROTc0LiPxMjyu"
				+ "hEeG+avzi0ZeKNmNlbrcF1qloM/juNu8NxrCnATkWjsay0NK7I6COBtkLTpH45bGwbuLZTco5JhgjC8ik886rJCHsaqA6qjcz/HnI1KCQv66zT2uIvqdWpbLH74czyjxlSSzwKBgQCxqQj0GrALOYn6YZV2h3j7l"
				+ "bgS9Z0ZmZO0etVN6MMIMeXckn1RU7Wqyn7tcdqin8nIwaGWvSLkf331ZWH113mhfrp+py47xSYJdCAjX5T65KVgCqo3XD/9/Trn8lX05ju89WWzmD6MXsd5QaCs4e9YBPe5z/traRA0Qm9MrY/IbwKBgQDcEApiS"
				+ "n+02w6DemFRF7ErHTWxuYriv0ZfEtRocURpipwrh6h09LV4hJ78uXtQN27eujN2gkb1EHPT0KG0AoUKI8lWdYIHeXcYiT6EdEPL/iiKNb3xXeGXmqNfhIgxkk9os5+Gsl7t08vMg/ZMqYITtU7lUa9HZHsRYo0wb"
				+ "XOMKQKBgFGFNs6L0GvWIzH4xRR2ZVVMBgLtIQE2L0iJdIcjXxrLiqYDse03dAzCjit2KtSkbXrpHrSEZRZkSe/obWLZVL594CVtoRFcDXAZEQYqPbpCYIAJZMGyWsq4/+aelEKjwpWaJOib21edxfol2wx5P9Irt"
				+ "WBVxxAqTX0tXtTYbzmRAoGBAJpviikUwzb+TawG9ClD1xEfq9pNt7uDZwtPad/w/gL45QMLAa72RLhmAZTIBGgSHpsOPRGxpqWcjkgg9QrjAKnjzb4YipsVZBlQgCLZVAPTNerADobl5eP5UAV0YahJsygBioiwd"
				+ "+K4lzgpdJx7o4QqAPqipeFDUBAyrLCr9fpM";

		String publicKeyKeyi = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq4U/TGZ9AOtwSFs3sl/hcSXj6vemprfYgDVsH5y4GvpBSuFaOWHVWXWywd+x09hrY6F4kWFKxPnQIHHxNXuycFkqOdbGAMXMyy+kwWMkBXJl4kzU3qUTnUFFGl4kkeUuQlXhq8ioV72RFl0BtN8BRVdlIeml+1iQidCTreWmvRsgVkMe9UU89euZiWn29w4y9LS8fA+JC00xYDu2y4CvklkaIm5Q4jmUJ90nmuIWs3Qd/YPIptrj/W3VwUbTUniYGveVT4VvOlE5b6dXDRSy0ZcHqMFnqcnjXcB0s9KG3HbiSGVmsvtpP0fFS2Ok8gCBfYEuRO/dR9eePcG786DFtQIDAQAB";
		String privateKeyKeyi = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCrhT9MZn0A63BIWzeyX+FxJePq96amt9iANWwfnLga+kFK4Vo5YdVZdbLB37HT2GtjoXiRYUrE+dAgcfE1e7JwWSo51sYAxczLL6TBYyQFcmXiTNTepROdQUUaXiSR5S5CVeGryKhXvZEWXQG03wFFV2Uh6aX7WJCJ0JOt5aa9GyBWQx71RTz165mJafb3DjL0tLx8D4kLTTFgO7bLgK+SWRoiblDiOZQn3Sea4hazdB39g8im2uP9bdXBRtNSeJga95VPhW86UTlvp1cNFLLRlweowWepyeNdwHSz0obcduJIZWay+2k/R8VLY6TyAIF9gS5E791H1549wbvzoMW1AgMBAAECggEAQxtB9blPhRGYf6a4tuBOiv9hGehE2oupeBfusruVSHDLJOnga/z8BSFIjBPiNllBQtzFGFQxdbmrlVYfImbAHLa2D6UZGOpKwuBkHImWaU8lz5Fx455Ae1EzISreMfqxy6BgR96dBp8TaeV/qm1pyOB1n/wTRbCT9GeXr/ze7LNPfMfiwUdg21CnTKKn5Ar07YRek5ATro89MGckhYf4WJw4l8dAvOEgW1fIrRlYENrgs8xtArWv5oVdqc+y0tjYBqgBeQC5xh8Hjv40GmxeIExbVYNAuyj0ccJvZx8jMqS2BIhRnrZESap/lnagK26D1MrIhZqVKQ8QJt5MXdSkLQKBgQDzKo2MoHAW8jNyYxXRghD9G/sQx/jKKK3DKg9kjzjqNZaFgdL2mAX/nK+5mS4KovEMHSmK2oGnHJaYpnXkqrteIjMswUwJafxwHyXJhQX4DPrYy3eITw7GuR0MhXU7ZnBmxj1slNHagDSK0TBd2lT4ajzUQ9bbLGLz4jhk/7JyBwKBgQC0kq487S/xFRYgU+f5a6fTzxFZb5eOkPbfe9klK8B9JGeYIbtJUNJ+Tflc57wVJeDao1lTJIfG9H8jUHTkr4zLl/ElPke/ON2hY2DTBh71RIU5OBjmAnclCuLA/kk5Rbk9U1kG5aTcMWoEUw+UG8lgAgnXlttW4ATIVIsN9+yrYwKBgQDb+QSv0HtV5qhvs/8K1hS0Q9mtZplDl+UJBkWCfKXKCcwu3jCP7xqOD5pP1ah26swQX1kzSVO8bvo1AXDo+SIil+GffQViBsTcUPEIChRKPsO11uo9HveJAfCrYReaMqM/9dmfHklw1kWxxmdwn4/6YXDBvNTi1JZ3dY/6ne05hQKBgQCbGxu7jemOZhR5dFlgb87bK5S1hBtlBLRqR1HAkhJn7CtoDCF52ojUoVm/KfbvG3hHleSWfFx802uaA/REs153S26CFTs/0DLf/CMhYLKa716YRc1tVVTOZslcCZEQNwnk2YExYXT5ByEP0eEh0bRqWZXmr9h8iTTSd3xyZTHQDQKBgB1HitsOQ8yeFgYwn2I5lRkymZcPaV9y871B64EV6mq6FaiFL2rnUOz3u4ausMy7LQW1o91CZcrOoIy2xVgcr/E8Nw/UuiUsbutr6IqmY5pP1KDPPVGVl4mceo5uemLa7AQQU4erlW20ieFhAfz7ZguXTYSUg9NSwiaf8vSgEOj+";

		// Map<String, String> keyPairMapKeyi = initRSAKey(2048);
		// Map<String, String> keyPairMapYuhai = initRSAKey(2048);
		Map<String, String> keyPairMapMine = new HashMap<String, String>();
		keyPairMapMine.put("publicKey", publicKeyKeyi);
		keyPairMapMine.put("privateKey", privateKeyKeyi);
		Map<String, String> keyPairMapThirdParty = new HashMap<String, String>();
		keyPairMapThirdParty.put("publicKey", publicKeyYuhai);
		keyPairMapThirdParty.put("privateKey", privateKeyYuhai);

		String data = "{\"orderId\":\"201711181601389442147483647\"}";

		// ======第三方向我方发送数据=====//
		// 用我方私钥签名数据
		String sign = buildRSASignByPrivateKey(data, keyPairMapMine.get("privateKey"));
		// 用第三方公钥加密数据
		String encryptData = buildRSAEncryptByPublicKey(data, keyPairMapThirdParty.get("publicKey"));
		// System.out.println("sign:" + sign + "\ndata:" + data + "\nencryptData:" + encryptData);
		// 发送给第三方加密数据及签名

		encryptData = "ix/8y4zWppJv/4dhs3Af6hIIebKa2Q7IRS2dEbV1xMFXWTd3yIKM9a1y32Z6w6BL17RDrd4/88BKcQ6laFtKivtZqLERECk0llVHCi+IEc/P81oy+iTepSkfypp/yeERyJfBnIV+AgIHbCuldkIRGe0A5RNeMXYCkSTHyEzUzpv0owJT5RFz5sIX0NKzQ1ukxGJhe/Ou1XubHuI6PcOU0NnUZSb1roXXQUC1NlSBri4eTuT7eAqIHOiVxZ0cdylXPb4i+rXbFNPsuPtP6vvWuKQiBEJks1dKyoqtlxJ3wDj2/0Lr7icsH01Bkkdju6aQ1tK+WYOl/c77uwER9SWhdIVSYc8lxU+rBQ0Rha8MTDDxgm0SUqgEP5fSr3BSxQvc0/FaDcywxnOuhrsTAwZoLL24QVDSCuzse/Kp14RXfsVZeFYjX8bv3++vib0D/RHcfhOpwjyJBoAe/kH77VhzcALbx8+Pvy06YGD26IG4Apow01QaHaBBpgxHi+YChkf14NosYCXkC9HbNfN552VTGj1aiSB+Zx9e1EfY8omxFvHoL6+Fk3NtqPfggkQ8utgHCwa3NBgAbXMZvbUCzegOrbM0fy+e8SXVryubK8mQ8u4gYMGPK16DWYKC8MVXHTZvMOyfw0/tVJrYWercRgDg+cY90mTk2SOl6KrinQvOjHULgZT9uOy2ht1ouyc0akecUA9OVmjKI/8/UPo8tZkeJegmNW81HdWI6YL89iPgDLtYMfx9Ni9m6JnFLD0NO+EVAsVgjJPXKvxrenWzaRq4I6z7wu4ZY7wwHxrvpU8Mo6tC6pgZ4/TZVwpXPKpKZoli2ln0y99kFpTsZcYHJBm+xkNxhp5fI0xUd6y0YLN8sKt+RHZn5oqDudD4Ntbvxo6Dz4AOjzBNlOCpKatPHiAxAklQhjXyG1G+w88rrwpHiK9e/3IplxtHCUqFh2JTqJ5tCxjtOfMV8lxbeFFmjM7pTHrJE0x5NCC4F+vboxvL3d5geUwEA+zYZdetCvguNfXrYzqD9a8p99fJHvhiI68J3z42POV4IbFaeM5WbWzE2GGvPlfMYCDyNy5Osz/FvA91w7sg3RuBoZXudwsDJquJcY8wUmQpPiFjyYTMKlo4qDMELtR6YZQPfB7VPUHcGb5tMiEX2ddZtotyYK1mQvugTbBGSp/lEDW6+gvmaqwNwIpD6sbj5CaE4ji1xvU+enx6mW1ntsFYNpWjivmZ+w3eoHg8DAosIBF74rG9TIi+aeqrpy7ttoMgtXivjdvjdTOQFdGHS95hn4qCu6m5OtVQ5ddjXK/QKS8KdpyxVWwFNIIyubeTR+5PaF+8wgQZe5EvoknLXyJ4r1dDxMWOE009yCjVdwprlpQqfU/i95923dFt77QBRZBWKPQYOeLrWqSg+mn9qdjzRo8SqnCCTi+eNbpheBEcQyrKen/PJvrbP+EevwsBO1N48gNvXIMdsVv5WqDJNIwIszyOxSJkM/2iKGHe6gVYgxgvocbK2SoQCgKM6XsQjKVFmKxJmaXHgll8SQGTA4cpLXWpd5AOWJhm4zyA4ZqZJduDc3x7oOk+x3ViNyg8D1phKKcoWFEWGHFkOs8fu2XuTXpL6AceXidf16IKJX63mwDB5lru+RhH/6fr/HJ8EHmwnOqUH1tjq5lwVQ04cW6uvxe1njbDFrpkOwfXmeBN8Yn9EctRrV8bTb0BC90dIWRptqa2UdMVnabN4rq8J0l2TQqC7KgwbEUyp/SV/HLnTGNNBVqhEPPK4cAVKnRULmvLNIxUM2kkmVHkknThmfy7KHwZRMlQ5Kqff/tAR4oPBNwgTewqKK9KbP+qP1+A0ycixxejmz4l+q0mGg4s5gVEV1u38dSgOuT21Gy2M5S7aP1EL1XXZ0EpGfSrlT2KMM6nnMG8Ty4oQ5/DU2vwv4eJP0QlTWOfxpNIee1nf2c2TPVO+Z+3h79ftll0zR05I42yoFosl5OtUD5bdlJrp5CLDm10wYFPbrETLJgFRhASJHxEvldrNdjQCAeF5TkC5F985LuPt4nBhbx2BG8RuHrpvc8yJY1J2OS8HPI60z9QGIFrWnNwCDeTCoafBf1LIfQ/lozJI9bRGbaoHyyinbs4uTFmOOgzWxIbUT/pDXmtwN9gvTuk8Gw6G+lG5iE+WB8rx95KwH7XV5W/y8pRBABJp1jCEJ9Ezr48WLsfnpEPvpc8+1GARlLTSq4ILQR0g2AihegHeeEBkGbeWnJ2cuqbsrzdHPeoLMbsy3J0hFyvRhti23LRI8+g5m9VRWUYOEgQreFwQ6S4BU5ZhRIhbeiJAUmYPFjLoOxDbJ4y35kddq+6YZh1zFRdABij5ztTKzKS768TyNq99e1JY2BcQX9A/L0mevOHPns2LSL/hW5oCYsDY7QkzOpHtls7hAoZXQhUhY45zQlOhMN5FniK9fh6HgOOYeACyfR3GHn7sHTfnOISYYHDUZEcCyxESgENmHnDDhrEKuZ+n9PHJqOBsHKBxxyE+cfPG0+uiqyakQZJQ97Ir3w+0qVlnWVZoGbeW6rIiEGBHvlV9nQwECmxSi5VoFj6iwAaUMSqCsluBMjEw4lScK1Kah9el40ozn7su2wumA18N0gL3XXfs0aOSiIRj/6OxFiIsi5MhTloi79lyFM00rZTlhkR3KoLaBRRDyiGi7A5NXfunYebm/67lA6eXh0PdRcBWb7j9kxZjGclbHbVP/eopS5TQmghd4/7n74OQtFjSyG6/5yIWCuHbNiCB3zff97987KBV3D/wPQZFZbUUjJtS8a6ptMyi1Tf1pIvHarbh7CBQ76bgADz3imunYVGomXYWvMNtLvOAsjn5liKiHd0YHiJZCzootILsPyiVTp3k6j4i88MiFuBQBGRE7l2mlr/rM/xQTPKSPUVQenU2c5gd0lwsy6DmCQ7aC4qp2p0kGs1fa3DJ1L6A1SjzhZ1Fr3BNYof4YS6ePXKM8v87Fb8yrNLYM6ZDEM6xGLhIRm0u490ZFiSnUJGaENh2T9c6eWTZtq72Yo6di341v0n7ar/O5kF88SeNMrOseM/0/n+0+p8rblfKZjSYj7mMue9h+ba/Pvv/Bpf3GuLdJ0wED2SnbkrpQGMJq7ISkzrrygr/82iokEBdyqx9bUSEEm0Ty5XsIFKCLXfDeCXGs1Y0QMA5zlsl5Wsb1++j1WUsjPk5JkS6VTZeXNxCsiac1kxo+TzCqFa9ZFYAnXWwB7LW4lahQ0n9ZKWS7P43LTaExZwzo6JOvUpwGrBwyT5/dDVsxOCUs1aF3Fik0inAgizn+8iup7h/oAmVzEqkhwAWo5+W5s9SGGJp0pH8+YLIK641K9uM4hl0AsKlGue1/M3dEmqtYo60JAACKcuA3GfRPKNrwRpzmxXzIZR0IfkjYVEQWb/MGOSOXWjJFFleErag9NqTP/5tifhYGc8NqvApp1fdFWO4jO9RMxW+jXwu/1KKitOCcQgU/ZFlLWlBQf6NKMEIh4zUSVDfeeA4r/UFiI801Orz5dq5kZ6vrtDRgz2cf+Qe7WJ1dr5d9S7E+BkKJwziujU85TDIr6KjCQomP5M624q+Ru4iRnnYDsJGGNbFa/d2Vnj4vEn0fj2KI7DxmtcFXEpv8J3TQzJPLusi0UcXVCb3DTc4eJBirK+bNXHoaAArzSbi7Vu9BpC8Wodfh72JmJRftp47/RcpOemUoUZUtzAiYAMbPuv+70CgZkUIzPsPFi1lU4Vvaj8EMJz1qZVUkpLFjYkuuLDBU6mfRzXvzgYGr5TH8B/ASigrnVEZlhtWo9i49MZurY4xKXbDWxyQvP56B+x4VMoVXunCdnzZ5Io7KZr/3YRkGIoWu4Jax+9c38dzaFB9MrY5pIiCGmDX7O2gxt/TwpM5L3zGITRmMMoKzdOvJQJ0P3Yd9+KEQebYn+kOs+PsMmA8BDWOH7dP7JfQZpa/yehMVqEumDLduJKGv3y22GTrYaBVUugsZ4qQK9G+R3GDXIGSE5BsQTvUbWkzRiyQtqWhP7bBIOpBpgxj7oVvAxtXo0Lz7dNrFz42qLvbjOyo7u5Tj+C3/83A2byauljtJAncK7PckgoBbJ/8QFqSfra9At22jFzeWquyp5g2YIvCrTQIWAWG8n1Pkf0fzp34pPm77jmrQTV9938p3y3iy6zTeFsW1rjke24ksLJ8e4jaOeVDuSNWNzdbRlHBKTBhawTK4L7RsiMNR4zJAvxdcrdPJak+w3DlXkOyPmfTF+jQSVPIap2q4dCAn+gAiUiy8ywDPtC4ece80n6BKmdpvBKNmkDGPAurQ09r+jM1NPHea7QciJlYZ4T6ZSfAPleK50fMYi0nIfkPpsPlPbTrOAbgLuAP14UohTp2xIH2OnT/HcImMQHa4w5wSeTuNZRw6Pig15184jJNxdzAoWp0mrWvKEoI6feHvxuniwD4DhbPvEJQUZkJnJfuL6/d6zdSUnoX87bnG1PwUFoxArpbGiOs/WZlI4fmmO1e2NWgYsyb/ASKzA0YGVS4ilqTZj7ElJvxl1s9Kl/7vU/OXOTL/uA8JrqnZoJPckUk+SpE4VEoftPUI0Ha4d3PxQaZmKMhHsT6fIqPmW3gxUc9HzZyz/quYhOw2xBEMA/3MNanfKj+4OcWXTi/3b3+OCs/Kv/SwZMPnJaeQX8Qs9ErhgyWGWJnvNI/8NEZSgaLDM0gW22OC61gD+Wvj8/hM7DfrtjODKXR+TpAYjtnPYcUAvzMhY19oNU709QDH6Fn6W3KQVniknLSatc7fjQBroy2iUmTA8K0haBHnX8wKiesC6W6lfV8rXUcQgoZtkkludaSHuN6oESYHHi0WUBGL+ndRrZuShjhsMVlSr3yzgrUrtvFxjq2AEmjP1Bt+IGCl8M8lXSZVmqeNyapHZKasZLAyll8lVq7/4Qjy3DcfuX7fNkIfBVI5SyxJqsd3fSXK+savZlqKjB33BX80H0cGvQFyeH3siTb2FRDkLSesR2S0DGRSPD9CciPMUoS5UnzYWZnt7ojAdCYV6DxjW8/xu+d9qh21wSDvUmXqpo6lytt9PB/3fOBoLcp/I7Dkv+XGZAsYrtqDCOw6pSHrPeEJSJ8SVhSAsZKee4MhGcu0qi4OGmYx/gJGezZA2wvqBL/r+GB/iAN/J6Me/5RrYCj0sEmiV+gkXbmY+cjhDFNhQHdMNamD5TTH8a1Mi/wXNp9/u1oxFiDd3xGzqmqTDq0kYhTR+Ar/9HLghUyGhd3SfEg+yB9Gr1MwGuRRMPs62MjDb/WmnBXrPg965Yuenabk703lKz9nq8zaYkGQlPpoqt51YgetwtxnHx74pInxNpAZociblH1b7E/rKNOqGxgIzz7KUJnu3egIPE6NKaYmALoSk+h3ksoAz8GwLQxHtqTEQvqMxDaUtP0mML1cB1YnqB7nVhN9pmVGMiZQDKeAk8iHQtP13i3JRK+zqJQpUH/E+Xp4E7pRXmwBjyopSb4+wjEJ9dLG95CiNa74h5xw14WcTh7/uOL1ylScRF3P8yP43EY4RTMcY6Xf1XzL9YDbHSkiwZYGWAWjmEdptbvYZDDz9ryxIP+mLrGYMiGMeg7XI236Z0FQ+4/WtAmN/4MpYQN9ZUbI5NrtWnPycSvG9kwwvlXOaZGIfQ8lompleENTk3G8Dd08OJr/itXwi+L4xVp5BzMh1KOfQsiZ1jk9Fsz5QQKcx0KHEqRgB13kfWHZomRhpynQTALiVKWoGYdmqXlKhNl55QmcnRLHRBJC5HH/3B1al/ksTmhIvqwFrnwQW9zhuD3tOlkc8mVUqZj1CDdO4vRZJnIK0EWJfxjUzUoFNRCCYsDTzyvulTHdvSx2XJSaYaYy5A64LMzwm3bjt1nIwIkjR4zX4q+GgNLRl6Nq/VTLlqwe5YH7YUjrIBDQ0kEU8PUq0c/4VISh+LF6k7B7YuqkPciNXrcUgsu0Qofaof6mPmaRyTe+DmdlsLYYWwLn3fNmgfNHOxJ3/H23Wt0LsPALCNGk533X58rL7wx8FX22gO/OxUPBkpIK+m62hz5FOf1QUfjcFm3ywDyKbd77V8FcPLo0SyFSUE81SxRMNQfiVrJW0qthQmT1BqmQvhX0qcxMOTHo0ppwEub/vBYweCjOyj25GRkvm6n6DycwQSVpcdwJUFcpLXQW8UdH1CxcMg9QOKLlGc44jq55JQ+H2BlTKpJkByro5mUTqgxfECuhIq2hSpA7Vsxhl7QZFO8t9j/lkACthGTEXDEhBAnTY1yq53U9y7okM9vACpvxcpd5hnMB6Ney+QTLmWks1+qC571fRjcJW9B9eyaECSghCpICrLxnADlEts80z8M4g83C7iIM0yQGZy4XMN6ieUMpJf0qyOxvDrLUhpjOoRep5oLz5oi1CKZ1V1SXu4lzr4Trw5oqqmB5DQ94gMt49e7xz6Q/0URCCFdZa+ftEgp2HQ7AnU7JFmfdvt4jLklOyxLc2mS/Yg4rx+cgHldYRrqXiDG1M3HgOZoWqu5NXAACi8q/DjRuaf1bmf0HBnRSlJV9tWNWZet6Tatl9w3fuWPFrqPvO8sxqb73O+2KTq5kCwWJ4omtLSKvaqbm8NV4KZAh169H1jkxWxjXDAB10cZ+lCWEmAE4U5PVIm7miYnI+6zeuYO/MmsnfVyEBz+vCeBFSbatNeUU7ZEKFP16Rt9aj9skM0KjUnV6XqPmwJInm33HbxxFPkyN8ZBx5fPzYnQsqydQQ5If0QAmAP+VJ6zJXsmlon8d7SS5pMnxC6ke2Bwk6kLlkZH1mKAKoItm0TZsfWhV+4Npg+eVqwLGcmfEgEVFNQtodJZuMx5tOHV4g=";
		sign = "BShAKekwCJMVc3hbfaaKp6JN0V3i6SU96Cps4EBh5PlkAZnzPHBCAJGugFPxQ5jrN7F65J062x/hJzrsS7RRaZkOuIzYEHqVO/RNM7/tlzWV7Vst3jfvdR+oKhnWEQrGa1pcO6pHDnTHHZ1D6tEvohdItITBG6sJB73Joeiud/tU04tBvJRjx0InIDPqo+HassdRfQaW1XvrP38AbmbwMYsiVGe6Nf3wkNx0pFNwQjervB2LKMBS1ZIWUhsKhHQKzCQfueFI7+xHjTOH7xmf4Bj/Oy3HLNTUGV0C81SYb4bYFHVwVG5FqQ2BCz9ApjZr7pmLSH/imj2/Fy7OorTl4A==";
		// 第三方用私钥解密数据
		String decryptData = buildRSADecryptByPrivateKey(encryptData, keyPairMapThirdParty.get("privateKey"));
		// 用我方公钥验签
		boolean signValid = buildRSAverifyByPublicKey(decryptData, keyPairMapMine.get("publicKey"), sign);
		System.out.println("signValid:" + signValid + "\ndecryptData:" + decryptData);

		// ======第三方向我方发送数据=====//
		// 第三方用私钥签名数据
		data = "TUlJS2JBWUpLb1pJaHZjTkFRY0NvSUlLWFRDQ0Nsa0NBUUV4RHpBTkJnbGdoa2dCWlFNRUFnRUZBRENDQktFR0NTcUdTSWIzRFFFSEFhQ0NCSklFZ2dTT2V5SjFjMlZ5Ym1GdFpTSTZJakU0TlRJ"
				+ "ME9UazJPRFExSWl3aWJtRnRaU0k2SXVXeXMrVzRoU0lzSW1sa1kyRnlaRzV2SWpvaU1qRXhNVEF6TVRrNU56RXdNRGN3T1RFMUlpd2lZMjl1ZEhKaFkzUnBaQ0k2SW1aa1ltRmpaamN5TVdSbFpUUmhOek5oWWpV"
				+ "MFpUQXhPVFF4TWpBMFl6Rm1JaXdpWVcxdmRXNTBJam9pTXpJME9TNDVNQ0lzSW5CbGNtbHZaQ0k2SWpFeUlpd2lZMjl1ZEhKaFkzUk9kVzBpT2lJelF6RTNNVEV3TVRrNE55SXNJbVJoZEdVaU9pSXlNREUzTFRF"
				+ "eExUTXdJREV3T2pReE9qTTFJaXdpY0hKdlpIVmpkQ0k2SWpFd01ERXdSakV3TURBd01EQXdNREpFVUVOSElpd2lZV05qZEc1aGJXVWlPaUxsc3JQbHVJVWlMQ0poWTJOMGJtOGlPaUkyTWpFeU1qWXdOekUwTURB"
				+ "eE9UZ3lORGd6SWl3aVltRnVhMDVoYldVaU9pTGt1SzNsbTczbHQ2WGxsWWJwazdib29Zd2lMQ0p0YjJKcGJHVWlPaUl4T0RVeU5EazVOamcwTlNJc0ltRnRiM1Z1ZEY5amFHbHVaWE5sSWpvaTVZK0I1THVmNkxT"
				+ "dzVMMnc2SUtHNW91KzU0Nlc1WVdENTQ2VzZLZVNJaXdpWVhCd2JIbEJaR1J5WlhOeklqb2k2TDY5NWE2QjU1eUI1NXVZNlpTbTViaUM1WVcwNlpxRzVZK3c1WXk2Nkw2OTVyS3o1TDJ6Nkl1Uk1UamxqN2ZtcGJ3"
				+ "ejVZMlY1WVdETkRBeElpd2ljbVZ3WVhsdFpXNTBJam9pTVRBd01UQkdNVEF3TURBd01EQXdNa1JWUVZnaUxDSnphV2R1WkdGMFlTSTZJakl3TVRjdE1URXRNak1nTVRnNk1EYzZOVGdpTENKemRHRnlkRjlrWVhS"
				+ "bElqb2lNakF4TnkweE1TMHlNeUF4T0Rvd09Eb3lOU0lzSW1WdVpGOWtZWFJsSWpvaU1qQXhPQzB4TVMweU1pQXhPRG93T0RveU5TSXNJbU52Ym5SaFkzUk9ZVzFsTUNJNkl1V3lzK1M2a2VXL29DSXNJbU52Ym5S"
				+ "aFkzUk5iMkpwYkdVd0lqb2lNVE01TkRJM09USXdNellpTENKamIyNTBZV04wVG1GdFpURWlPaUxva292cG02Z2lMQ0pqYjI1MFlXTjBUVzlpYVd4bE1TSTZJakV6TURJNE1qSXpNemMzSWl3aVkyOXVkR0ZqZEU1"
				+ "aGJXVXlJam9pNWEyWjU0Nko2WnllSWl3aVkyOXVkR0ZqZEUxdlltbHNaVElpT2lJeE16azVPRGM0TkRZNE5DSXNJbU52Ym5SaFkzUk9ZVzFsTXlJNkl1ZTlsK1dHaVNJc0ltTnZiblJoWTNSTmIySnBiR1V6SWpv"
				+ "aU1UVXlOREkzTnpFeU5ETWlMQ0pqYUdGeVoyVjNZWGxmWm1seWMzUmhZMk55ZFdGc0lqb2lMeUlzSW1Ob1lYSm5aWGRoZVY5aVpXWnZjbVZzYjJGdUlqb2lMeUlzSW1Ob1lYSm5aWGRoZVY5bWFYSnpkR0ZqWTNK"
				+ "MVlXeGZaWEYxWVd4ZlkzVnljbVZ1ZEY5cGJuUmxjbVZ6ZENJNklpOGlMQ0pqYUdGeVoyVjNZWGxmWm1seWMzUmhZMk55ZFdGc1gzQnZjblJtYjJ4cGIxOXlaWEJoZVcxbGJuUWlPaUl2SWl3aVkyaGhjbWRsZDJG"
				+ "NVgyWnBjbk4wWVdOamNuVmhiRjlzWVhOMGNISnBibU5wY0dGc0lqb2lMeUlzSW1Ob1lYSm5aWGRoZVY5bWFYSnpkR0ZqWTNKMVlXeGZaWEYxWVd4ZmNISnBibU5wY0dGc1gybHVkR1Z5WlhOMElqb2lMeUlzSW1O"
				+ "b1lYSm5aWGRoZVY5bWFYSnpkR0ZqWTNKMVlXeGZiM0owWm05c2FXOWZjbVZ3WVhsdFpXNTBYMlZ4ZFdGc1gyTjFjbkpsYm5SZmFXNTBaWEpsYzNRaU9pSXZJbjJnZ2dRNU1JSUVOVENDQXgyZ0F3SUJBZ0lGTUFJ"
				+ "d2xSRXdEUVlKS29aSWh2Y05BUUVGQlFBd0t6RUxNQWtHQTFVRUJoTUNRMDR4SERBYUJnTlZCQW9NRTBOR1EwRWdVbE5CSUZSRlUxUWdUME5CTWpFd0hoY05NVGN4TVRNd01ESTBNVE0yV2hjTk1qQXdOVE13TURJ"
				+ "ME1UTTJXakIyTVFzd0NRWURWUVFHRXdKRFRqRVJNQThHQTFVRUNnd0lUME5CTWpGU1UwRXhEekFOQmdOVkJBc01Ca05HUTBGRFJERVZNQk1HQTFVRUN3d01TVzVrYVhacFpIVmhiQzB4TVN3d0tnWURWUVFERENO"
				+ "RFJrTkJRMFJBNWJLejViaUZRREV5TVRFeE1ETXhPVGszTVRBd056QTVNVFZBTWpDQ0FTSXdEUVlKS29aSWh2Y05BUUVCQlFBRGdnRVBBRENDQVFvQ2dnRUJBSVV2NmVoMVBlcFhQY0Z5UW1zenU2aHNNR240b284"
				+ "L0ZYU3FvM0tKMEYxZkFMeHl2MVNmeXEyQVNpQmxlamtmWlVZWU1ab204dFNPRGR6Wi9HUVh6TVBra2hxaHE0K3NqNDh1V3A2bW5qMCtHd1VwLzZtNUxyMW5Ob1ZEV2hyWnlsMk1KZ3Z0YnVKZHc3K1o3MDZjN2dj"
				+ "Z0plb3NnZzd6S0NxUDRuMWRlU1pFTlVlaEF5M09ON1JpTUpFcmJRYTRFVGhtNDFrVk1Samc4SHlzNlJlN2k1Z0JzZEcyUjBGUUROdHpvU082WnBubGVxb1ZHdk1RVDk1anFVUk5nZ2F5N0dGS3dCblpNY24zNnFW"
				+ "UjRqVStsb3M5dlBOakpMOVZPdllZSXVWS2lHSmszZEZqdUhWYmVHYm5rUXh0Nk93aUNBQzltQkRhbHp4RDFEMjFpN3B6NEZrQ0F3RUFBYU9DQVJNd2dnRVBNQjhHQTFVZEl3UVlNQmFBRk0vZm1mdUdJaFlUT1N3"
				+ "SFhvNDlkeXU1YWUrT01DUUdDU3FCSElhaElBUUJBUVFYREJWYk1WMHlNVEV4TURNeE9UazNNVEF3TnpBNU1UVXdFQVlIWUlFY2h1OHFDQVFGREFNMUxqRXdhUVlEVlIwZkJHSXdZREF1b0N5Z0tvWW9hSFIwY0Rv"
				+ "dkx6SXhNQzQzTkM0ME1pNHpMMDlEUVRJeEwxSlRRUzlqY213eU56UXpMbU55YkRBdW9DeWdLb1lvYUhSMGNEb3ZMekl4TUM0M05DNDBNaTR6TDA5RFFUSXhMMUpUUVM5amNtd3lOelF6TG1OeWJEQUxCZ05WSFE4"
				+ "RUJBTUNBK2d3SFFZRFZSME9CQllFRkhpckRSb3JBYmNEeU10THlhQ2pyZXIwM1B4TU1CMEdBMVVkSlFRV01CUUdDQ3NHQVFVRkJ3TUNCZ2dyQmdFRkJRY0RCREFOQmdrcWhraUc5dzBCQVFVRkFBT0NBUUVBZGJJ"
				+ "K1NPdk0zMXozYS9KcnY4SExRZVV1a1g0R0lHaGxjVW1OL1BaT2FJbERIRVowQjR1anZDZHhxZzUyRUh2WTRLMGhIR2RWMUlEQU5iRDJMUG5mbWpnVG1OWjIwdHNpUFNVdkhLSmx2SjNIK2dmTkJWVzdlQy9oaExk"
				+ "ajdvQzdIbnpoLzFweENzeXlXemt1MFZRVTUzbEIyUFFKeCtJQUE5OXJobWRTcnhLellCb2RRaXUzMEVISEtVbUxBL3Y4TFZNZkE1bHJMMkFoSHZveXpiMlBoODBIaEJSeHNOZkRyYWYvenEyeERpbW15OHR2K0pW"
				+ "M2txd1hpTDNNTEFrVHo4VFVka0FrUWRnYmw2YzBtZGhuWEZ3U2gzbFVHWEI4endjQzY1eDFtMUxqUG1Ib2lBSHNtVmJyMmdFWm9PdWo0T09qUVdLaGVPVjR1TUtvMHFEV2d6R0NBVjh3Z2dGYkFnRUJNRFF3S3pF"
				+ "TE1Ba0dBMVVFQmhNQ1EwNHhIREFhQmdOVkJBb01FME5HUTBFZ1VsTkJJRlJGVTFRZ1QwTkJNakVDQlRBQ01KVVJNQTBHQ1dDR1NBRmxBd1FDQVFVQU1BMEdDU3FHU0liM0RRRUJBUVVBQklJQkFFYjZrNmVPcm1a"
				+ "ckdoVHBvVGxkOHJVeUFBUWUrc3JWYXBHdUJERVRmTHpHc0YzMmZpeFhHV2NzTGYwSE1SR0VTcWsxQURTZ1V1UFNkTDBIR0kvdFpZZjBuZSt2SWNnSVQ1MGZjRjVkUXJtUm5VTExVRjdvSGhBbHBGOC9pVCtxdU82"
				+ "WmFxcEV5V082bEhsc05JTFU3d0ZQbCtVMG8zVitKTkdxczJBNG00RHRud1J1Zmh5SThBdktvV3FudkhuOVZ5eFRpcC8wWGtvOTgvUkFWZ3hic2hPUFJxR1U4eVFhaXZiWUxpTFg5UUs5MmlxY0FCNUxVMVdxZUc3"
				+ "bWU3RHI0Q2hWTkN6aFl1c1pjRzg1QzdCQ1BOcmk4RGpEU2k0RmRwY3lzQVBFS0hLQmxMSnpBUXl3WUNEdmo4eWxJRFZEanRFQy9zMkl5UzZ0b0xLR3l5V2NQQUU9";
		String thirdPartySign = buildRSASignByPrivateKey(data, keyPairMapThirdParty.get("privateKey"));
		// 第三方用我方公钥加密数据
		String thirdPartyEncryptedData = buildRSAEncryptByPublicKey(data, keyPairMapMine.get("publicKey"));
		System.out.println("sign:" + thirdPartySign + "\ndata:" + data + "\nencryptData:" + thirdPartyEncryptedData);
		// 发送给我方数据及签名
		// 用我方私钥解密数据
		String mineDecryptData = buildRSADecryptByPrivateKey(thirdPartyEncryptedData, keyPairMapMine.get("privateKey"));
		// 我方用第三方公钥验签
		boolean mineVerifySignValid = buildRSAverifyByPublicKey(decryptData, keyPairMapThirdParty.get("publicKey"), thirdPartySign);
		// System.out.println("signValid:" + mineVerifySignValid + "\ndecryptData:" + mineDecryptData);

	}
}
