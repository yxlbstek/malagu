package vip.malagu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;

/**
 * 身份证号相关
 * @author Lynn -- 2020年5月22日 上午10:22:56
 */
public class IdCardUtils {
	
	private IdCardUtils() {}

	private static final String REGEX_PASSPORT = "^(P\\d{7}|G\\d{8}|S\\d{7,8}|D\\d+|1[4,5]\\d{7})$";
	
	private static Pattern pPassport = Pattern.compile(REGEX_PASSPORT);

	/**
	 * 身份证验证
	 * @param IDStr 身份证号
	 * @return
	 * @throws ParseException
	 */
	public static boolean validateIdCard(String idStr) throws ParseException {
		String[] valCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
		String cardNo = null;
		if (idStr.length() != 15 && idStr.length() != 18) {
			throw new CustomException("身份证号码长度应该为15位或18位", SystemErrorEnum.FAIL.getStatus());
		}
		cardNo = buildCardNo(idStr, cardNo);
		if (!isNumber(cardNo)) {
			throw new CustomException("身份证15位号码都应为数字、18位号码除最后一位外、都应为数字", SystemErrorEnum.FAIL.getStatus());
		}
		validateIdCardBirthday(cardNo);
		Map<String, String> codes = getAreaCode();
		if (codes.get(cardNo.substring(0, 2)) == null) {
			throw new CustomException("身份证地区编码错误", SystemErrorEnum.FAIL.getStatus());
		}
		cardNo = validateIdCardLastCode(valCodeArr, wi, cardNo);
		if (idStr.length() == 18 && !cardNo.equals(idStr)) {
			throw new CustomException("身份证无效，不是合法的身份证号码", SystemErrorEnum.FAIL.getStatus());
		}
		return true;
	}

	private static String validateIdCardLastCode(String[] valCodeArr, String[] wi, String cardNo) {
		//身份证最后一位
		int totalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			totalmulAiWi = totalmulAiWi + Integer.parseInt(String.valueOf(cardNo.charAt(i))) * Integer.parseInt(wi[i]);
		}
		int modValue = totalmulAiWi % 11;
		String lastOneCode = valCodeArr[modValue];
		cardNo = cardNo + lastOneCode;
		return cardNo;
	}

	private static boolean validateIdCardBirthday(String cardNo) throws ParseException {
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String year = cardNo.substring(6, 10);
		String month = cardNo.substring(10, 12);
		String day = cardNo.substring(12, 14);
		if (!isDate(year + "-" + month + "-" + day)
				|| (gc.get(Calendar.YEAR) - Integer.parseInt(year)) > 150
				|| (gc.getTime().getTime() - dateFormat.parse(year + "-" + month + "-" + day).getTime()) < 0) {
			throw new CustomException("身份证出生日期无效", SystemErrorEnum.FAIL.getStatus());
		}
		if (Integer.parseInt(month) > 12 || Integer.parseInt(month) == 0) {
			throw new CustomException("身份证月份无效", SystemErrorEnum.FAIL.getStatus());
		}
		if (Integer.parseInt(day) > 31 || Integer.parseInt(day) == 0) {
			throw new CustomException("身份证日期无效", SystemErrorEnum.FAIL.getStatus());
		}
		return true;
	}

	private static String buildCardNo(String idStr, String cardNo) {
		if (idStr.length() == 18) {
			cardNo = idStr.substring(0, 17);
		} else if (idStr.length() == 15) {
			cardNo = idStr.substring(0, 6) + "19" + idStr.substring(6, 15);
		}
		return cardNo;
	}

	/**
	 * 地区编码
	 * @return
	 */
	public static Map<String, String> getAreaCode() {
		Map<String, String> result = new HashMap<>();
		result.put("11", "北京");
		result.put("12", "天津");
		result.put("13", "河北");
		result.put("14", "山西");
		result.put("15", "内蒙古");
		result.put("21", "辽宁");
		result.put("22", "吉林");
		result.put("23", "黑龙江");
		result.put("31", "上海");
		result.put("32", "江苏");
		result.put("33", "浙江");
		result.put("34", "安徽");
		result.put("35", "福建");
		result.put("36", "江西");
		result.put("37", "山东");
		result.put("41", "河南");
		result.put("42", "湖北");
		result.put("43", "湖南");
		result.put("44", "广东");
		result.put("45", "广西");
		result.put("46", "海南");
		result.put("50", "重庆");
		result.put("51", "四川");
		result.put("52", "贵州");
		result.put("53", "云南");
		result.put("54", "西藏");
		result.put("61", "陕西");
		result.put("62", "甘肃");
		result.put("63", "青海");
		result.put("64", "宁夏");
		result.put("65", "新疆");
		result.put("71", "台湾");
		result.put("81", "香港");
		result.put("82", "澳门");
		result.put("91", "国外");
		return result;
	}

	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		return isNum.matches();
	}

	/**
	 * 判断字符串是否为日期格式
	 * @param strDate
	 * @return
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		return m.matches();
	}

	/**
	 * 护照校验
	 * @param passportNo 护照
	 * @return
	 */
	public static boolean passportValidate(String passportNo) {
		Matcher m = pPassport.matcher(passportNo);
		return m.matches();
	}

}
