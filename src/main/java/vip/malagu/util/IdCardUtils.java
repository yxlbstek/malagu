package vip.malagu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证号相关
 * @author Lynn -- 2020年5月22日 上午10:22:56
 */
public class IdCardUtils {

	private static final String regexPassport = "^(P\\d{7}|G\\d{8}|S\\d{7,8}|D\\d+|1[4,5]\\d{7})$";
	
	private static Pattern pPassport = Pattern.compile(regexPassport);

	/**
	 * 身份证验证
	 * @param IDStr 身份证号
	 * @return
	 * @throws ParseException
	 */
	public static String validateIdCard(String IDStr) throws ParseException {
		String[] valCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
		String cardNo = null;
		
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			return "身份证号码长度应该为15位或18位";
		}
		
		if (IDStr.length() == 18) {
			cardNo = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			cardNo = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumber(cardNo) == false) {
			return "身份证15位号码都应为数字、18位号码除最后一位外、都应为数字";
		}

		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String year = cardNo.substring(6, 10);
		String month = cardNo.substring(10, 12);
		String day = cardNo.substring(12, 14);
		if (isDate(year + "-" + month + "-" + day) == false
				|| (gc.get(Calendar.YEAR) - Integer.parseInt(year)) > 150
				|| (gc.getTime().getTime() - dateFormat.parse(year + "-" + month + "-" + day).getTime()) < 0) {
			return "身份证出生日期无效";
		}
		if (Integer.parseInt(month) > 12 || Integer.parseInt(month) == 0) {
			return "身份证月份无效";
		}
		if (Integer.parseInt(day) > 31 || Integer.parseInt(day) == 0) {
			return "身份证日期无效";
		}

		Hashtable<String, String> codes = getAreaCode();
		if (codes.get(cardNo.substring(0, 2)) == null) {
			return "身份证地区编码错误";
		}

		//身份证最后一位
		int totalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			totalmulAiWi = totalmulAiWi + Integer.parseInt(String.valueOf(cardNo.charAt(i))) * Integer.parseInt(wi[i]);
		}
		int modValue = totalmulAiWi % 11;
		String lastOneCode = valCodeArr[modValue];
		cardNo = cardNo + lastOneCode;

		if (IDStr.length() == 18 && !cardNo.equals(IDStr)) {
			return "身份证无效，不是合法的身份证号码";
		}
		return "ok";
	}

	/**
	 * 地区编码
	 * @return
	 */
	public static Hashtable<String, String> getAreaCode() {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为日期格式
	 * @param strDate
	 * @return
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 护照校验
	 * @param passportNo 护照
	 * @return
	 */
	public static boolean PassportValidate(String passportNo) {
		Matcher m = pPassport.matcher(passportNo);
		if (m.matches()) {
			return true;
		}
		return false;
	}

}
