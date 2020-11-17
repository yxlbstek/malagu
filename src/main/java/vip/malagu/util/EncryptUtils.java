package vip.malagu.util;

import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;

import sun.misc.BASE64Decoder;

/**
 * 加密工具类
 * @author Lynn -- 2020年5月21日 下午5:11:45
 */
@SuppressWarnings("restriction")
public final class EncryptUtils {
	
	private EncryptUtils() {}
	
	private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
	private static final String DES_KEY = "hangzhoushunaier";
	
	private static final String IV_STR = "18735624";
	
	private static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
	
	private static final String AES_KEY = "hangzhoushunaier";  
    
	private static final String ALGORITHM_AES = "AES/ECB/PKCS5Padding";

	/**
	 * MD5加密
	 * @param content
	 * @return
	 */
	public static String encodeByMD5(String content) {
		try {
			if (StringUtils.isNotBlank(content)) {
				MessageDigest mdigest = MessageDigest.getInstance("MD5");
				byte[] bytes = content.getBytes();
				mdigest.update(bytes);
				byte[] md = mdigest.digest();
				int j = md.length;
				char[] str = new char[j * 2];
				int k = 0;
				for (int i = 0; i < j; i++) {
					byte byte0 = md[i];
					str[k++] = digits[byte0 >>> 4 & 0xf];
					str[k++] = digits[byte0 & 0xf];
				}
				return new String(str);
			}
		} catch (Exception e) {
			throw new CustomException("MD5加密失败", SystemErrorEnum.FAIL.getStatus());
		}
		return null;
	}
	
	/**
	 * 验证内容是否一致
	 * @param md5Content 原加密内容
	 * @param content 新内容
	 * @return
	 */
	public static boolean verifyMD5(String md5Content, String content){
		if (StringUtils.isNotBlank(md5Content) && StringUtils.isNotBlank(content)) {
			return md5Content.equals(encodeByMD5(content));
		}
		return false;
    }
	
	/**
	 * DES算法，加密
	 * @param content 待加密字符串
	 * @param key 加密私钥，长度不能够小于8位
	 * @return 加密后的字节数组，一般结合Base64编码使用
	 * @throws Exception
	 */
	public static String encodeByDES(String content) {
		if (StringUtils.isNotBlank(content)) {
			return null;
		}
		try {
			DESKeySpec dks = new DESKeySpec(DES_KEY.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec(IV_STR.getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
			byte[] bytes = cipher.doFinal(content.getBytes());
			return byte2hex(bytes);
		} catch (Exception e) {
			throw new CustomException("DES加密失败", SystemErrorEnum.FAIL.getStatus());
		}
	}

	/**
	 * DES算法，解密 
	 * @param content 待解密字符串
	 * @param key 解密私钥，长度不能够小于8位
	 * @return 解密后的字节数组
	 * @throws Exception 异常
	 */
	public static String decodeByDES(String content) {
		if (StringUtils.isBlank(content) || content.length() < 8) {
			return null;
		}
		try {
			DESKeySpec dks = new DESKeySpec(DES_KEY.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec(IV_STR.substring(0, 8).getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			return new String(cipher.doFinal(hex2byte(content.getBytes())));
		} catch (Exception e) {
			throw new CustomException("DES解密失败", SystemErrorEnum.FAIL.getStatus());
		}
	}
	
	/** 
     * AES加密
     * @param content 待加密的内容 
     * @return 加密后的base 64 code 
     * @throws Exception 
     */  
    public static String encodeByAES(String content) throws Exception {  
        return encodeByBase64(aesEncryptToBytes(content));  
    } 
    
    /** 
     * AES解密 
     * @param encryptStr 待解密的base 64 code 
     * @return 解密后的string 
     * @throws Exception 
     */  
    public static String decodeByAES(String encryptStr) throws Exception {  
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptToBytes(decodeByBase64(encryptStr));  
    } 
    
    /**
     * AES解密
     * 填充模式AES/CBC/PKCS7Padding
     * 解密模式128
     * @param content 目标密文
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] content, byte[] aesKey, byte[] ivByte) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            Key sKeySpec = new SecretKeySpec(aesKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(iv));
        return params;
    }
    
    /** 
     * base64 加密
     * @param bytes 待编码的byte[] 
     * @return 编码后的base 64 code 
     */  
    public static String encodeByBase64(byte[] bytes){  
        return Base64.encodeBase64String(bytes);  
    }
    
    /** 
     * base64 解密
     * @param base64Code 待解码的base 64 code 
     * @return 解码后的byte[] 
     * @throws Exception 
     */  
    public static byte[] decodeByBase64(String base64Code) throws Exception{  
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);  
    }
    
    /** 
     * AES初始加密 
     * @param content 待加密的内容 
     * @return 加密后的byte[] 
     * @throws Exception 
     */  
    private static byte[] aesEncryptToBytes(String content) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128);  
        Cipher cipher = Cipher.getInstance(ALGORITHM_AES);  
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(AES_KEY.getBytes(), "AES"));  
        return cipher.doFinal(content.getBytes("utf-8"));  
    }
    
    /** 
     * AES初始解密 
     * @param encryptBytes 待解密的byte[] 
     * @return 解密后的String 
     * @throws Exception 
     */  
    private static String aesDecryptToBytes(byte[] encryptBytes) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHM_AES);  
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(AES_KEY.getBytes(), "AES"));  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);  
        return new String(decryptBytes);  
    }

	private static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp;
		for (int n = 0; b != null && n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				hs.append('0');
			}
			hs.append(stmp);
		}
		return hs.toString().toLowerCase();
	}

	private static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0) {
			throw new IllegalArgumentException();
		}
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}
	
}
