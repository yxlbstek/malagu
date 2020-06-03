package vip.malagu.util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;

/**
 * 加密工具类
 * @author Lynn -- 2020年5月21日 下午5:11:45
 */
public final class EncryptUtils {
	
	private static final char digits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
	private static final String COMMON_KEY = "_weiyuwangluoweiqiandai_";
	
	private static final String IV_STR = "18735624";
	
	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

	/**
	 * MD5加密
	 * @param password
	 * @return
	 */
	public static String MD5Encode(String password) {
		try {
			if (password != null && !"".equals(password)) {
				MessageDigest mdigest = MessageDigest.getInstance("MD5");
				byte[] bytes = password.getBytes();
				mdigest.update(bytes);
				byte[] md = mdigest.digest();
				int j = md.length;
				char str[] = new char[j * 2];
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
	 * DES算法，加密
	 * @param data 待加密字符串
	 * @param key 加密私钥，长度不能够小于8位
	 * @return 加密后的字节数组，一般结合Base64编码使用
	 * @throws Exception
	 */
	public static String DESEncode(String data) {
		if (data == null)
			return null;
		try {
			DESKeySpec dks = new DESKeySpec(COMMON_KEY.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec(IV_STR.getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
			byte[] bytes = cipher.doFinal(data.getBytes());
			return byte2hex(bytes);
		} catch (Exception e) {
			throw new CustomException("DES加密失败", SystemErrorEnum.FAIL.getStatus());
		}
	}

	/**
	 * DES算法，解密 
	 * @param data 待解密字符串
	 * @param key 解密私钥，长度不能够小于8位
	 * @return 解密后的字节数组
	 * @throws Exception 异常
	 */
	public static String DESDecode(String data) {
		if (data == null || data.length() < 8)
			return null;
		try {
			DESKeySpec dks = new DESKeySpec(COMMON_KEY.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec(IV_STR.substring(0, 8).getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			return new String(cipher.doFinal(hex2byte(data.getBytes())));
		} catch (Exception e) {
			throw new CustomException("DES解密失败", SystemErrorEnum.FAIL.getStatus());
		}
	}

	/**
	 * 二行制转字符串 
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp;
		for (int n = 0; b != null && n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1)
				hs.append('0');
			hs.append(stmp);
		}
		return hs.toString().toLowerCase();
	}

	private static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException();
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}
	
}
