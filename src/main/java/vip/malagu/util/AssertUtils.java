package vip.malagu.util;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vip.malagu.custom.exception.CustomException;
import vip.malagu.custom.exception.ICustomException;
import vip.malagu.enums.SystemErrorEnum;

/**
 * 断言工具类
 */
public final class AssertUtils {

	private AssertUtils() {}

	private static final Pattern MOBILE_PATTERN = Pattern.compile("^[1][0-9]{10}$");

	/**
	 * 对象不为Null，为Null时抛异常 
	 * @param obj 对象
	 * @param customException 错误异常
	 */
	public static void isNotNull(Object obj, ICustomException customException) {
		if (obj == null) {
			throw new CustomException(customException);
		}
	}

	/**
	 * 对象为Null，不为Null时抛异常 
	 * @param obj 对象
	 * @param customException 错误异常
	 */
	public static void isNull(Object obj, ICustomException customException) {
		if (obj != null) {
			throw new CustomException(customException);
		}
	}
	
	/**
	 * 对象参数不为Null，为Null时抛异常 
	 * @param obj 对象参数
	 * @param paramName 提示名称
	 */
	public static void isNotNullParam(Object obj, String paramName) {
		if (obj == null) {
			throw new CustomException(paramName + "不能为空", SystemErrorEnum.PARAMETER_ANOMALY.getStatus());
		}
	}
	
	/**
	 * 对象参数为Null，不为Null时抛异常 
	 * @param obj 对象参数
	 * @param paramName 提示名称
	 */
	public static void isNullParam(Object obj, String paramName) {
		if (obj != null) {
			throw new CustomException(paramName + "需为空", SystemErrorEnum.PARAMETER_ANOMALY.getStatus());
		}
	}
	
	/**
	 * Map不为空，为空时抛异常 
	 * @param map 集合
	 * @param customException 错误异常
	 */
	public static void isNotEmpty(Map<?, ?> map, ICustomException customException) {
		if (map == null || map.isEmpty()) {
			throw new CustomException(customException);
		}
	}
	
	/**
	 * Map为空，不为空时抛异常 
	 * @param map 集合
	 * @param customException 错误异常
	 */
	public static void isEmpty(Map<?, ?> map, ICustomException customException) {
		if (map != null && !map.isEmpty()) {
			throw new CustomException(customException);
		}
	}
	
	/**
	 * Map参数不为空，为空时抛异常 
	 * @param map 集合参数
	 * @param paramName 参数名
	 */
	public static void isNotEmptyParam(Map<?, ?> map, String paramName) {
		if (map == null || map.isEmpty()) {
			throw new CustomException(paramName + "不能为空", SystemErrorEnum.PARAMETER_ANOMALY.getStatus());
		}
	}
	
	/**
	 * Map参数为空，不为空时抛异常 
	 * @param map 集合参数
	 * @param paramName 提示名称
	 */
	public static void isEmptyParam(Map<?, ?> map, String paramName) {
		if (map != null && !map.isEmpty()) {
			throw new CustomException(paramName + "需为空", SystemErrorEnum.PARAMETER_ANOMALY.getStatus());
		}
	}
	
	/**
	 * 集合不为空，为空时抛异常 
	 * @param collection 集合
	 * @param customException 错误异常
	 */
	public static void isNotEmpty(Collection<?> collection, ICustomException customException) {
		if (collection == null || collection.isEmpty()) {
			throw new CustomException(customException);
		}
	}
	
	/**
	 * 集合为空，不为空时抛异常 
	 * @param collection 集合
	 * @param customException 错误异常
	 */
	public static void isEmpty(Collection<?> collection, ICustomException customException) {
		if (collection != null && !collection.isEmpty()) {
			throw new CustomException(customException);
		}
	}
	
	/**
	 * 集合参数不为空，为空时抛参数异常 
	 * @param collection 集合参数
	 * @param paramName 提示名称
	 */
	public static void isNotEmptyParam(Collection<?> collection, String paramName) {
		if (collection == null || collection.isEmpty()) {
			throw new CustomException(paramName + "不能为空", SystemErrorEnum.PARAMETER_ANOMALY.getStatus());
		}
	}
	
	/**
	 * 集合参数为空，不为空时抛异常 
	 * @param collection 集合参数
	 * @param paramName 提示名称
	 */
	public static void isEmptyParam(Collection<?> collection, String paramName) {
		if (collection != null && !collection.isEmpty()) {
			throw new CustomException(paramName + "需为空", SystemErrorEnum.PARAMETER_ANOMALY.getStatus());
		}
	}
	
	/**
	 * 字符串不为空，为空时抛异常 
	 * @param obj 字符串
	 * @param customException 错误异常
	 */
	public static void isNotEmpty(String obj, ICustomException customException) {
		if (obj == null || "".equals(obj)) {
			throw new CustomException(customException);
		}
	}

	/**
	 * 字符串为空，不为空时抛异常 
	 * @param obj 字符串
	 * @param customException 错误异常
	 */
	public static void isEmpty(String obj, ICustomException customException) {
		if (obj != null && !"".equals(obj)) {
			throw new CustomException(customException);
		}
	}
	
	/**
	 * 字符串参数不为空，为空时抛参数异常 
	 * @param obj 字符串参数
	 * @param paramName 提示名称
	 */
	public static void isNotEmptyParam(String obj, String paramName) {
		if (obj == null || "".equals(obj)) {
			throw new CustomException(paramName + "不能为空", SystemErrorEnum.PARAMETER_ANOMALY.getStatus());
		}
	}
	
	/**
	 * 字符串参数为空，不为空时抛异常 
	 * @param obj 字符串参数
	 * @param paramName 提示名称
	 */
	public static void isEmptyParam(String obj, String paramName) {
		if (obj != null && !"".equals(obj)) {
			throw new CustomException(paramName + "需为空", SystemErrorEnum.PARAMETER_ANOMALY.getStatus());
		}
	}
	
	/**
	 * 对象为TRUE，FALSE时抛异常 
	 * @param obj 对象
	 * @param customException 错误码
	 */
	public static void isTrue(Boolean obj, ICustomException customException) {
		if (obj == null || Boolean.FALSE.equals(obj)) {
			throw new CustomException(customException);
		}
	}
	
	/**
	 * 对象为FALSE，TRUE时抛异常 
	 * @param obj 对象
	 * @param customException 错误码
	 */
	public static void isFalse(Boolean obj, ICustomException customException) {
		if (obj == null || Boolean.TRUE.equals(obj)) {
			throw new CustomException(customException);
		}
	}

	/**
	 * 手机号 
	 * @param obj 手机号字符串
	 * @return
	 */
	public static void isMobile(String obj, ICustomException customException) {
		isNotEmpty(obj, customException); 
		Matcher m = MOBILE_PATTERN.matcher(obj);
		if (!m.matches()) {
			throw new CustomException(customException);
		}
	}
	
}
