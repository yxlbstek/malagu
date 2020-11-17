package vip.malagu.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 常用正则相关工具类
 */
public final class RegexUtils {
	
	private RegexUtils() {}
	
	/**
	 * 匹配空格、制表符、换页符等等
	 */
	public static final String NOT_BLANK = "\\s*";
	
	/**
	 * 匹配全数字
	 */
	public static final String ALL_NUM = "\\d*";
	
	/**
	 * 匹配是否存在特殊字符
	 */
	public static final String NOT_SPECIAL_CHAR = "\\w*-*\\s*";
	
	
	/**
	 * 替换字符串中的所有的空格、制表符、换页符等等
	 * @param str
	 * @return
	 */
	public static String replaceBlack(String str) {
		if (StringUtils.isNotBlank(str)) {
			return str.replaceAll(NOT_BLANK, "");
		}
		return str;
	}
	
	/**
	 * 是否是全数字
	 * @param str
	 * @return
	 */
	public static Boolean isAllNum(String str) {
		if (StringUtils.isNotBlank(str)) {
			return Pattern.matches(ALL_NUM, str);
		}
		return null;
	}
	
	/**
	 * 是否存在特殊字符
	 * @param str
	 * @return
	 */
	public static Boolean existSpecialChar(String str) {
		if (StringUtils.isNotBlank(str)) {
			return StringUtils.isBlank(str.replaceAll(NOT_SPECIAL_CHAR, "")) ? false : true;
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		String s = " s4324a-c %sv432-d ";
		String e = "321431432e";
		System.out.println(s);
		System.out.println(isAllNum(e));
	}

	
}