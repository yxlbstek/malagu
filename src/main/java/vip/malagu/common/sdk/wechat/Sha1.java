package vip.malagu.common.sdk.wechat;

import java.io.UnsupportedEncodingException;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Sha1 {

	private static final String SALT = "d2VpeXVfd2VpcWlhbmRhaV8x";
	
	private static final String SALTH5 = "dvbnwerdfdfaerthdbmyuihaV8x";

	/**
	 * SHA1 安全加密算法
	 *
	 * @param maps 参数key-value map集合
	 * @return
	 * @throws DigestException
	 */
	public static String SHA1(Map<String, Object> maps) throws DigestException {
		// 获取信息摘要 - 参数字典排序后字符串
		String decrypt = getOrderByLexicographic(maps) + SALT;
		try {
			// 指定sha1算法
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			try {
				digest.update(decrypt.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 获取字节数组
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString().toLowerCase();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new DigestException("签名错误！");
		}
	}

	/**
	 * SHA1 安全加密算法
	 *
	 * @param maps 参数key-value map集合
	 * @return
	 * @throws DigestException
	 */
	public static String SHA1NoSalt(Map<String, String> maps) throws DigestException {
		// 获取信息摘要 - 参数字典排序后字符串
		Set<String> keySet = maps.keySet();
		String[] keyArray = keySet.toArray(new String[keySet.size()]);
		Arrays.sort(keyArray);
		StringBuilder sb = new StringBuilder();
		for (String k : keyArray) {
			if (maps.get(k).trim().length() > 0) // 参数值为空，则不参与签名
				sb.append(k).append("=").append(maps.get(k).trim()).append("&");
		}
		String decrypt = sb.toString();
		decrypt = decrypt.substring(0, decrypt.length() - 1);
		try {
			// 指定sha1算法
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			try {
				digest.update(decrypt.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 获取字节数组
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString().toLowerCase();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new DigestException("签名错误！");
		}
	}

	/**
	 * SHA1 安全加密算法
	 *
	 * @param maps 参数key-value map集合
	 * @return
	 * @throws DigestException
	 */
	public static String SHA1H5(Map<String, Object> maps) throws DigestException {
		// 获取信息摘要 - 参数字典排序后字符串
		String decrypt = getOrderByLexicographic(maps) + SALTH5;
		try {
			// 指定sha1算法
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(decrypt.getBytes("utf-8"));
			// 获取字节数组
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString().toLowerCase();

		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new DigestException("签名错误！");
		}
	}

	/**
	 * 获取参数的字典排序
	 *
	 * @param maps
	 *            参数key-value map集合
	 * @return String 排序后的字符串
	 */
	public static String getOrderByLexicographic(Map<String, Object> maps) {
		return splitParams(lexicographicOrder(getParamsName(maps)), maps);
	}

	/**
	 * 获取参数名称 key
	 *
	 * @param maps 参数key-value map集合
	 * @return
	 */
	public static List<String> getParamsName(Map<String, Object> maps) {
		List<String> paramNames = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : maps.entrySet()) {
			paramNames.add(entry.getKey());
		}
		return paramNames;
	}

	/**
	 * 参数名称按字典排序
	 *
	 * @param paramNames 参数名称List集合
	 * @return 排序后的参数名称List集合
	 */
	public static List<String> lexicographicOrder(List<String> paramNames) {
		Collections.sort(paramNames);
		return paramNames;
	}

	/**
	 * 拼接排序好的参数名称和参数值
	 *
	 * @param paramNames 排序后的参数名称集合
	 * @param maps 参数key-value map集合
	 * @return String 拼接后的字符串
	 */
	private static String splitParams(List<String> paramNames, Map<String, Object> maps) {
		StringBuilder paramStr = new StringBuilder();
		for (String paramName : paramNames) {
			paramStr.append(paramName);
			for (Map.Entry<String, Object> entry : maps.entrySet()) {
				if (paramName.equals(entry.getKey())) {
					paramStr.append(String.valueOf(entry.getValue()));
				}
			}
		}
		return paramStr.toString();
	}

	public static String SHA256(String str) throws DigestException {
		// 获取信息摘要 - 参数字典排序后字符串
		String decrypt = str;
		try {
			// 指定sha1算法
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(decrypt.getBytes("utf-8"));
			// 获取字节数组
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new DigestException("签名错误！");
		}
	}

}